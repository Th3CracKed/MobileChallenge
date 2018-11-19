package com.mobileChallenge.ui.activity;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobileChallenge.R;
import com.mobileChallenge.databinding.ActivityMainBinding;
import com.mobileChallenge.ui.adapter.ViewPagerAdapter;
import com.mobileChallenge.ui.fragment.FragmentOne;
import com.mobileChallenge.ui.fragment.FragmentTwo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentOne(), "Trending");
        adapter.addFragment(new FragmentTwo(), "Settings");
        binding.viewPager.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        setupTabIcons(binding.tabLayout);

        setTitle(getString(R.string.app_name));
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        TextView textView = new TextView(this);
        textView.setTextSize(20);
        textView.setText(title);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(textView);
    }

    /**
     *
     * @param tabLayout change TabLayout's icon
     */
    private void setupTabIcons(TabLayout tabLayout) {
        tabLayout.getTabAt(0).setIcon(R.mipmap.star);
        tabLayout.getTabAt(1).setIcon(R.mipmap.settings);
    }

}

