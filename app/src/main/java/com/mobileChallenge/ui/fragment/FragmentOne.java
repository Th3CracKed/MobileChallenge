package com.mobileChallenge.ui.fragment;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobileChallenge.R;
import com.mobileChallenge.databinding.FragmentOneBinding;

public class FragmentOne extends Fragment {

    public FragmentOne() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentOneBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_one,container,false);
        return binding.getRoot();
    }

}