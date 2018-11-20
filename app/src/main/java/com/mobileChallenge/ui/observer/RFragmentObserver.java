package com.mobileChallenge.ui.observer;

import android.util.Log;
import android.view.View;

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.mobileChallenge.databinding.FragmentRecyclerviewBinding;
import com.mobileChallenge.model.RepositoriesModel;
import com.mobileChallenge.service.RetrofitAction;
import com.mobileChallenge.service.RetrofitReposClient;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RFragmentObserver implements LifecycleObserver {

    private Disposable internetDisposable;
    private int requestedPage = 1;//counter
    private Boolean isRequested = true;//to queue user request when connection is down
    private FragmentRecyclerviewBinding binding;
    private LifecycleOwner viewLifecycleOwner;

    public RFragmentObserver(FragmentRecyclerviewBinding mBinding, LifecycleOwner mViewLifecycleOwner) {
        binding = mBinding;
        viewLifecycleOwner = mViewLifecycleOwner;
    }

    /**
     * observe LiveData change and update UI according to it
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private void setupListUpdate() {
        binding.getViewModel().getMutableLiveData().observe(viewLifecycleOwner, items -> {
            if(items.size() == 0){
                binding.getViewModel().getShowLoading().set(View.GONE);
                binding.getViewModel().getShowEmptyTextView().set(View.VISIBLE);
            }else{
                binding.getViewModel().setListInAdapter(items);
            }
        });
    }

    /**
     * Set RecyclerView's LayoutManager and scroll to saved position
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private void setRecyclerViewLayoutManager() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(binding.getRoot().getContext());
        binding.recyclerView.setLayoutManager(mLayoutManager);

        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (binding.recyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) binding.recyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        binding.recyclerView.scrollToPosition(scrollPosition);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void onResume() {
        onInternetAvailabilityChange();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void onPause(){
        safelyDispose(internetDisposable);
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
     *
     * Listen to internet connectivity every 2 seconds (default), different from listening to network connectivity
     * Customization is possible with InternetObservingSetting
     * @see <a href="https://github.com/pwittchen/ReactiveNetwork#checking-internet-connectivity-once">ReactiveNetwork guide</a>
     * when the device is connected and request is queued, try to fetch Data using Retrofit
     */
    private void onInternetAvailabilityChange() {
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
     * Request data from RetrofitReposClient
     * update UI onSuccess and onFailure
     * onSuccess increment requested page, and dispose request
     * onFailure que request page
     */
    private void requestData() {
        RetrofitReposClient.getInstance().fetchData(requestedPage, new RetrofitAction() {
            @Override
            public void onSuccess(RepositoriesModel repositoriesModel) {
                super.onSuccess(repositoriesModel);
                isRequested = false;//request delivered
                requestedPage += 1;//increment
                //TODO change UI
            }

            @Override
            public void onFailure() {
                super.onFailure();
                isRequested = true; //queue a request that will be launched when internet is available again
                //TODO change UI
            }
        });
    }
}
