package com.mobileChallenge.service;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mobileChallenge.service.API.BASE_URL;

/**
 * Give a ready Retrofit instance with GsonConverter and RxJava adapter
 */
public class RetrofitHelper {
    private static Retrofit ourInstance;

    /**
     * @return Retrofit instance
     */
    public static synchronized Retrofit getInstance() {
        if(ourInstance == null)
            ourInstance = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return ourInstance;
    }
    //to prevent instantiation of this class
    private RetrofitHelper() {
    }
}
