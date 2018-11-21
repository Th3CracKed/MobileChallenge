package com.mobileChallenge.ui.observer;

import com.mobileChallenge.databinding.FragmentRecyclerviewBinding;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class RFragmentObserver implements LifecycleObserver {

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
            if(items == null || items.size() == 0){
                binding.getViewModel().showEmptyText();
            }else{
                binding.getViewModel().setListInAdapter(items);
                binding.swipeToRefresh.setRefreshing(false);
            }
        });
    }

    /**
     * Listen to user pull to request to fetch new data
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private void setOnRefreshListner(){
        SwipeRefreshLayout swipeToRefresh = binding.swipeToRefresh;
        swipeToRefresh.setOnRefreshListener(() -> binding.getViewModel().requestData());
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
        binding.getViewModel().onInternetAvailabilityChange();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void onPause(){
        binding.getViewModel().safelyDispose();
    }
}
