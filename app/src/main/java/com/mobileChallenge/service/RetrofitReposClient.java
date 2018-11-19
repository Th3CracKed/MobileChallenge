package com.mobileChallenge.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Retrofit Repos Fetcher
 * hide all configuration to retrieve data
 */
public class RetrofitReposClient {
    private static RetrofitReposClient ourInstance;
    private static API api;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private RetrofitReposClient(){}

    /**
     * @return RetrofitReposClient that encapsulate the fetch
     */
    public static synchronized RetrofitReposClient getInstance(){
        if(ourInstance == null){
            ourInstance = new RetrofitReposClient();
            api = RetrofitHelper.getInstance().create(API.class);
        }
        return ourInstance;
    }

    /**
     *  fetch Requested data
     * @param requestedPage the page that user requested
     * @param retrofitAction An abstract class that define the behaviour on Success and on fail of Retrofit fetch
     */
    public void fetchData(int requestedPage,RetrofitAction retrofitAction){
        compositeDisposable.add(api.getRepositories(getParams(requestedPage))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(retrofitAction::onSuccess, throwable -> retrofitAction.onFailure()));
    }

    /**
     * Calculate today - 30 days and parse it to the correct date format
     * @param requestedPage the page that user requested
     * @return HTTP GET params
     */
    private Map<String,String> getParams(int requestedPage) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-30);//reduce 30 days of today's date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
        String formattedDate = dateFormat.format(calendar.getTime());//format date to string with specific format
        Map<String,String> params = new HashMap<>();
        params.put("q","created:>"  + formattedDate);
        params.put("sort","stars");
        params.put("order","desc");
        params.put("page",String.valueOf(requestedPage));
        return params;
    }
}
