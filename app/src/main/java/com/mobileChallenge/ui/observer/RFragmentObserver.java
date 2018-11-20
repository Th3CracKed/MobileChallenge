package com.mobileChallenge.ui.observer;

import android.view.View;

import com.mobileChallenge.databinding.FragmentRecyclerviewBinding;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        binding.getViewModel().onInternetAvailabilityChange();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void onPause(){
        binding.getViewModel().safelyDispose();
    }
}
