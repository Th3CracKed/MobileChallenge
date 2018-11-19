package com.mobileChallenge.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobileChallenge.R;
import com.mobileChallenge.databinding.FragmentRecyclerviewBinding;
import com.mobileChallenge.model.Item;
import com.mobileChallenge.viewModel.RViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Fragment responsible to manage RecyclerView
 *
 */
public class RecyclerViewFragment extends Fragment {

    private static final String TAG = "RecyclerViewFragment";

    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RViewModel rViewModel = ViewModelProviders.of(this).get(RViewModel.class);
        FragmentRecyclerviewBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_recyclerview,container,false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(rViewModel);
        binding.recyclerView.setTag(TAG);
        mRecyclerView = binding.recyclerView;

        setupListUpdate(rViewModel);
        setRecyclerViewLayoutManager();//TODO check if we can do this through layout

        return binding.getRoot();
    }


    private void setupListUpdate(final RViewModel rViewModel) {
        rViewModel.getMutableLiveData().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                if(items.size() == 0){
                    rViewModel.getShowLoading().set(View.GONE);
                    rViewModel.getShowEmptyTextView().set(View.VISIBLE);
                }else{
                    rViewModel.setListInAdapter(items);
                }
            }
        });
    }

    /**
     * Set RecyclerView's LayoutManager and scroll to saved position
     */
    private void setRecyclerViewLayoutManager() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        mRecyclerView.scrollToPosition(scrollPosition);
    }

}