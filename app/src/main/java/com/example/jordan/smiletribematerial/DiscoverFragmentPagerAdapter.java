package com.example.jordan.smiletribematerial;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class DiscoverFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private Context context;
    private String tabTitles[] = new String[] { "SERIES", "SINGLES"};

    public DiscoverFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Log.i("getItem", Integer.toString(position));
        if (position == 0) {
            return DiscoverSeriesFragment.newInstance();
        } else {
            return DiscoverSinglesFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
