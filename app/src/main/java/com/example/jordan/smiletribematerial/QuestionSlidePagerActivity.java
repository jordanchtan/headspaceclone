package com.example.jordan.smiletribematerial;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuestionSlidePagerActivity extends FragmentActivity implements QuestionSlideFragment.OnButtonClickListener{

    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private int num_questions = -1;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private QuestionViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    String packID;
    String currentUserUID;
    String workoutID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_slide_pager);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (QuestionViewPager) findViewById(R.id.viewPagerID);
        mPager.disableScroll(true);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        packID = getIntent().getStringExtra("packID");
        currentUserUID = getIntent().getStringExtra("currentUserUID");
        workoutID = getIntent().getStringExtra("workoutID");

        Log.i("packID", packID);
        Log.i("workoutID", workoutID);

        //TODO
        FirebaseDatabase.getInstance().getReference().child("packs").child(packID)
                .child("workouts").child(workoutID).child("questions").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // + 1 for after questions frag TODO rename
                //TODO add boolean once loaded
                num_questions = (int) dataSnapshot.getChildrenCount();
                Log.i("numques", Integer.toString(num_questions));
                mPagerAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == num_questions) {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);

            startActivity(intent);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Warning");
            builder.setMessage("Exiting halfway will cause you to lose your progress!");
            builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);

                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

    }

    @Override
    public void onButtonClicked(View view) {
        int currPos = mPager.getCurrentItem();
//        if (currPos == )
        mPager.setCurrentItem(currPos+1);
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
            Fragment fragment;
            Bundle args = new Bundle();
            args.putInt("position", position);
            args.putString("packID", packID);
            args.putString("workoutID", workoutID);
            int last_pos = num_questions - 1;
            if (last_pos + 1 == position) {
                fragment = new AfterQuestionsFragment();
            } else {
                fragment = new QuestionSlideFragment();
            }
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            //+1 because of afterquestions slide
            return num_questions + 1;
        }
    }
}
