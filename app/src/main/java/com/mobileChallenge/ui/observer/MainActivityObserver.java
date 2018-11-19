package com.mobileChallenge.ui.observer;

import android.util.Log;

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivityObserver implements LifecycleObserver {
    private Disposable internetDisposable;

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
     * Listen to internet connectivity every 2 seconds (default), different from listening to network connectivity
     * Customization is possible with InternetObservingSetting
     * @see <a href="https://github.com/pwittchen/ReactiveNetwork#checking-internet-connectivity-once">ReactiveNetwork guide</a>
     */
    private void onInternetAvailabilityChange() {
        internetDisposable = ReactiveNetwork
                .observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isConnected ->{
                    Log.d("MainActivityObserver","onInternetAvailabilityChange = "+isConnected);
                });
    }
}
