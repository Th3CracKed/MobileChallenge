package com.mobileChallenge.viewModel;

import android.util.Log;
import android.view.View;

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.mobileChallenge.model.Item;
import com.mobileChallenge.model.Owner;
import com.mobileChallenge.model.RepositoriesModel;
import com.mobileChallenge.service.RetrofitAction;
import com.mobileChallenge.service.RetrofitReposClient;
import com.mobileChallenge.ui.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * ViewModel for RecyclerView Fragment.
 * Store and manage UI-related data in a lifecycle conscious way.
 * The ViewModel class allows data to survive configuration changes such as screen rotations.
 */
public class RViewModel extends ViewModel {

    private MutableLiveData<List<Item>> mutableLiveData;
    private RecyclerViewAdapter adapter;
    private ObservableInt showRecyclerView;
    private ObservableInt showLoading;
    private ObservableInt showEmptyTextView;
    private Disposable internetDisposable;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Boolean isRequested = true;//to queue user request when connection is down

    public RViewModel() {
        super();
        init();
    }

    public LiveData<List<Item>> getMutableLiveData() {
        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
            loadData();
        }
        return mutableLiveData;
    }

    public ObservableInt getShowRecyclerView() {
        return showRecyclerView;
    }

    public ObservableInt getShowLoading() {
        return showLoading;
    }

    public ObservableInt getShowEmptyTextView() {
        return showEmptyTextView;
    }

    public RecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public void setListInAdapter(List <Item> items){
        adapter.setItems(items);
        showLoading.set(View.GONE);
        showRecyclerView.set(View.VISIBLE);
    }

    /*
    * Init binding layout
     */
    private void init(){
        adapter = new RecyclerViewAdapter();
        showRecyclerView = new ObservableInt(View.GONE);
        showLoading = new ObservableInt(View.VISIBLE);
        showEmptyTextView = new ObservableInt(View.GONE);
    }

    private void loadData() {
        //TODO when Retrofit is implemented replace all bellow lines throw interface
        Owner owner = new Owner("GoogleChromeLabs", "https://avatars1.githubusercontent.com/u/43830688?v=4");
        Item item = new Item("bert", "TensorFlow code and pre-trained models for BERT", "7487",owner, "https://github.com/google-research/bert");
        List<Item> items = new ArrayList<>();
        for(int i = 0; i < 8 ;i++){
                items.add(item);
        }
        mutableLiveData.setValue(items);//add 8 elements with same content
    }

    /**
     *
     * Listen to internet connectivity every 2 seconds (default), different from listening to network connectivity
     * Customization is possible with InternetObservingSetting
     * @see <a href="https://github.com/pwittchen/ReactiveNetwork#checking-internet-connectivity-once">ReactiveNetwork guide</a>
     * when the device is connected and request is queued, try to fetch Data using Retrofit
     */
    public void onInternetAvailabilityChange() {
        internetDisposable = ReactiveNetwork
                .observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isConnected ->{
                    Log.d("RFragmentObserver","onInternetAvailabilityChange = "+isConnected);
                    if (isConnected && isRequested) {
                        requestData();
                    }
                });
    }

    /**
     * Public safelyDispose method that dispose all used composite
     */
    public void safelyDispose(){
        safelyDispose(internetDisposable,compositeDisposable);
    }

    /**
     * Dispose the resources passed on param
     * @param disposables varargs of Disposable object
     */
    private void safelyDispose(Disposable... disposables) {
        for (Disposable subscription : disposables) {
            if (subscription != null && !subscription.isDisposed()) {
                subscription.dispose();
            }
        }
    }

    /**
     * Request data from RetrofitReposClient
     * update UI onSuccess and onFailure
     * onSuccess increment requested page, and dispose request
     * onFailure que request page
     */
    private void requestData() {
        RetrofitReposClient.getInstance().fetchData(new RetrofitAction() {
            @Override
            public void onSuccess(RepositoriesModel repositoriesModel) {
                super.onSuccess(repositoriesModel);
                isRequested = false;//request delivered
                //TODO change UI
            }

            @Override
            public void onFailure() {
                super.onFailure();
                isRequested = true; //queue a request that will be launched when internet is available again
                //TODO change UI
            }
        },compositeDisposable);
    }

}
