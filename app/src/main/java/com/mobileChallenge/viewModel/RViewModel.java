package com.mobileChallenge.viewModel;

import android.view.View;

import com.mobileChallenge.model.Item;
import com.mobileChallenge.model.Owner;
import com.mobileChallenge.ui.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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

}
