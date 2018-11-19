package com.mobileChallenge.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobileChallenge.R;
import com.mobileChallenge.databinding.FragmentSettingsBinding;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

/**
 * Not Implemented
 */
public class SettingsFragment extends Fragment {

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentSettingsBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_settings,container,false);
        return binding.getRoot();
    }

}