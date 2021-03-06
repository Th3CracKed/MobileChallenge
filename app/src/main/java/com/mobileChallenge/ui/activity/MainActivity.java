package com.mobileChallenge.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.mobileChallenge.R;
import com.mobileChallenge.databinding.ActivityMainBinding;
import com.mobileChallenge.ui.adapter.ViewPagerAdapter;
import com.mobileChallenge.ui.fragment.RecyclerViewFragment;
import com.mobileChallenge.ui.fragment.SettingsFragment;

import java.util.Objects;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

/**
 * Main Activity that manage viewPager with tabLayout
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.app_name));

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setupViewPager(binding);

        setupTabIcons(binding.tabLayout);
    }

    /**
     * Setup fragments with viewPager
     * Setup tabLayout with viewPager
     * @param binding to access Activity's layout component
     */
    private void setupViewPager(ActivityMainBinding binding) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RecyclerViewFragment(), "Trending");
        adapter.addFragment(new SettingsFragment(), "Settings");
        binding.viewPager.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        TextView textView = new TextView(this);
        textView.setTextSize(20);
        textView.setTextColor(Color.BLACK);
        textView.setText(title);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(textView);
    }

    /**
     *
     * @param tabLayout change TabLayout's icon
     */
    private void setupTabIcons(TabLayout tabLayout) {
        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.mipmap.star);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(R.mipmap.settings);
    }

}

