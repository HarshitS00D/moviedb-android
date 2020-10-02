package com.example.moviestabbasic;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.example.moviestabbasic.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private FragmentFavourite fragmentFavourite;
    private FragmentPopular fragmentPopular;
    private FragmentTopRated fragmentTopRated;
    private FragmentNowPlaying fragmentNowPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tablayout_id);
        viewPager = findViewById(R.id.viewpager_id);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        fragmentPopular = new FragmentPopular();
        fragmentTopRated = new FragmentTopRated();
        fragmentFavourite = new FragmentFavourite();
        fragmentNowPlaying = new FragmentNowPlaying();

        viewPagerAdapter.AddFragment(fragmentPopular,"Popular");
        viewPagerAdapter.AddFragment(fragmentTopRated,"Top Rated");
        viewPagerAdapter.AddFragment(fragmentNowPlaying,"Now Playing");
        viewPagerAdapter.AddFragment(fragmentFavourite,"Favourite");




        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 3) {
                    fragmentFavourite.notifyDataChanged();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }
}
