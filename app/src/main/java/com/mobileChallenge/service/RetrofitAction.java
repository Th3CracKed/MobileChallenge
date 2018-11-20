package com.mobileChallenge.service;

import android.util.Log;

import com.mobileChallenge.model.Item;
import com.mobileChallenge.model.RepositoriesModel;

import java.util.List;

/**
 * Define action when using Retrofit
 */
public abstract class RetrofitAction {

    private int requestedPage = 1;//counter

    public int getRequestedPage() {
        return requestedPage;
    }

    /**
     * Log the result, will be override to implement interface specific behaviour and logic
     * @param repositoriesModel Repositories structure contains list of repos
     */
    public void onSuccess(RepositoriesModel repositoriesModel){
        logList(repositoriesModel.getItems());
        requestedPage += 1;//increment
    }

    /**
     * Log the error
     * we used to queue an other request and signal the error to user
     */
    public void onFailure(){
        Log.d("RetrofitAction","Retrofit onFailure()");
    }


    private void logList(List<Item> items){
        for(Item item : items){
            Log.d("RetrofitAction"," Name : "+item.getName()+" Description : "+item.getDescription()
                    +" \nLogin : "+item.getOwner().getLogin()+" Watchers : "+item.getWatchers()+" Image url : "+item.getOwner().getAvatar_url()+" Repo Url : "+item.getHtml_url()

            );
        }
    }
}
