package com.mobileChallenge.ui.observer;

import android.content.Context;
import android.util.Log;

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.mobileChallenge.model.RepositoriesModel;
import com.mobileChallenge.service.RetrofitAction;
import com.mobileChallenge.service.RetrofitReposClient;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RFragmentObserver implements LifecycleObserver {
    private Disposable internetDisposable;
    private int requestedPage = 1;//counter
    private Boolean isRequested = true;//to queue user request when connection is down
    private Context context;
    public RFragmentObserver(Context mContext) {
        context = mContext;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        onInternetAvailabilityChange();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause(){
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
