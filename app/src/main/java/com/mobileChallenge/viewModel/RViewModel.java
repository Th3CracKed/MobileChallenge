package com.mobileChallenge.viewModel;

import android.app.Application;
import android.view.View;
import android.widget.Toast;

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.mobileChallenge.model.Item;
import com.mobileChallenge.model.RepositoriesModel;
import com.mobileChallenge.service.RetrofitAction;
import com.mobileChallenge.service.RetrofitReposClient;
import com.mobileChallenge.ui.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * ViewModel for RecyclerView Fragment.
 * Store and manage UI-related data in a lifecycle conscious way.
 * The ViewModel class allows data to survive configuration changes such as screen rotations.
 */
public class RViewModel extends AndroidViewModel {

    private MutableLiveData<List<ItemViewModel>> mutableLiveData;
    private RecyclerViewAdapter adapter;
    private ObservableInt recyclerViewVisibility;
    private ObservableInt loadingVisibility;
    private ObservableInt emptyTextViewVisibility;
    private Disposable internetDisposable;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Boolean isRequested = true;//to queue user request when connection is down
    private Application application;

    public RViewModel(@NonNull Application mApplication) {
        super(mApplication);
        application = mApplication;
        init();
    }


    public LiveData<List<ItemViewModel>> getMutableLiveData() {
        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
        }
        return mutableLiveData;
    }

    public ObservableInt getRecyclerViewVisibility() {
        return recyclerViewVisibility;
    }

    public ObservableInt getLoadingVisibility() {
        return loadingVisibility;
    }

    public ObservableInt getEmptyTextViewVisibility() {
        return emptyTextViewVisibility;
    }

    public RecyclerViewAdapter getAdapter() {
        return adapter;
    }

    /**
     * populate Recycler view with list items, and update ui
     * @param items to populate RecyclerView
     */
    public void setListInAdapter(List <ItemViewModel> items){
        adapter.setItems(items);
        loadingVisibility.set(View.GONE);
        recyclerViewVisibility.set(View.VISIBLE);
        emptyTextViewVisibility.set(View.GONE);
    }

    /**
     * Show message to user when no data is available
     */
    public void showEmptyText() {
        loadingVisibility.set(View.GONE);
        recyclerViewVisibility.set(View.GONE);
        emptyTextViewVisibility.set(View.VISIBLE);
    }

    /*
     * Init binding layout
     */
    private void init(){
        adapter = new RecyclerViewAdapter();
        recyclerViewVisibility = new ObservableInt(View.GONE);
        loadingVisibility = new ObservableInt(View.VISIBLE);
        emptyTextViewVisibility = new ObservableInt(View.GONE);
    }

    /**
     *
     * Listen to internet connectivity every 2 seconds (default), different from listening to network connectivity
     * Customization is possible with InternetObservingSetting
     * @see <a href="https://github.com/pwittchen/ReactiveNetwork#checking-internet-connectivity-once">ReactiveNetwork guide</a>
     * when the device is connected and a request is queued, try to fetch Data using Retrofit
     */
    public void onInternetAvailabilityChange() {
        internetDisposable = ReactiveNetwork
                .observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isConnected ->{
                    if (isConnected && isRequested) {
                        requestData();
                    }else if (!isConnected && (mutableLiveData.getValue()==null || mutableLiveData.getValue().isEmpty() )){//Internet is not available and list is empty
                        showEmptyText();
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
     * onFailure queue request page
     */
    public void requestData() {
        RetrofitReposClient.getInstance().fetchData(new RetrofitAction() {
            @Override
            public void onSuccess(RepositoriesModel repositoriesModel) {
                super.onSuccess(repositoriesModel);
                isRequested = false;//request delivered
                List <ItemViewModel> itemViewModels = new ArrayList<>();
                for(Item item : repositoriesModel.getItems()){
                    ItemViewModel itemViewModel = new ItemViewModel(application);
                    itemViewModel.setItem(item);
                    itemViewModels.add(itemViewModel);
                }
                if(mutableLiveData.getValue() == null) {
                    mutableLiveData.setValue(itemViewModels);
                }else{
                    mutableLiveData.getValue().addAll(itemViewModels);//add new data to list
                    mutableLiveData.setValue(mutableLiveData.getValue());//to trigger observe method
                }
            }

            @Override
            public void onFailure() {
                super.onFailure();
                isRequested = true; //queue a request that will be launched when internet is available again
                if (mutableLiveData.getValue()==null || mutableLiveData.getValue().isEmpty()){//list is empty
                    showEmptyText();
                }else{
                    Toast.makeText(application,"Request failed",Toast.LENGTH_SHORT).show();
                }
            }
        },compositeDisposable);
    }
}