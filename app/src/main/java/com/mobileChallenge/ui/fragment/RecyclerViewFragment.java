package com.mobileChallenge.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobileChallenge.R;
import com.mobileChallenge.databinding.FragmentRecyclerviewBinding;
import com.mobileChallenge.ui.observer.RFragmentObserver;
import com.mobileChallenge.viewModel.RViewModel;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

/**
 * Fragment responsible to manage RecyclerView
 *
 */
public class RecyclerViewFragment extends Fragment {

    private static final String TAG = "RecyclerViewFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RViewModel rViewModel = ViewModelProviders.of(this).get(RViewModel.class);
        FragmentRecyclerviewBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_recyclerview,container,false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(rViewModel);
        binding.recyclerView.setTag(TAG);
        getLifecycle().addObserver(new RFragmentObserver(binding,getViewLifecycleOwner()));
        return binding.getRoot();
    }

}