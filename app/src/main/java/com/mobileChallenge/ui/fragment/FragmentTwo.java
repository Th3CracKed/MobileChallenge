package com.mobileChallenge.ui.fragment;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobileChallenge.R;
import com.mobileChallenge.databinding.FragmentTwoBinding;

public class FragmentTwo extends Fragment {

    public FragmentTwo() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentTwoBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_two,container,false);
        return binding.getRoot();
    }

}