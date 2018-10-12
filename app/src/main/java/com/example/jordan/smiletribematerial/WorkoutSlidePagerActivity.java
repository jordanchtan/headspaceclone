package com.example.jordan.smiletribematerial;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class WorkoutSlidePagerActivity extends FragmentActivity {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    int num_workouts = 0;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    String packID;
    String packName;
    long packNumberOfWorkouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_slide_pager);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        packID = getIntent().getStringExtra("packID");
        packName = getIntent().getStringExtra("packName");
        packNumberOfWorkouts = getIntent().getLongExtra("packNumberOfWorkouts", 0);
        num_workouts = (int) packNumberOfWorkouts;
        mPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            WorkoutSlideFragment fragment = new WorkoutSlideFragment();
            Bundle args = new Bundle();
            args.putInt("position", position);
            args.putString("packID", packID);
            args.putString("packName", packName);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return num_workouts;
        }
    }


}