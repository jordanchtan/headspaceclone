package com.example.jordan.smiletribematerial;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements RecyclerViewClickListener {
    ArrayList<String> packNames = new ArrayList<>();
    ArrayList<String> packIDs = new ArrayList<>();
    ArrayList<Long> packNumberOfWorkoutsList = new ArrayList<>();

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    String currentUserUID = currentUser.getUid();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialButton quickStartButton = (MaterialButton) view.findViewById(R.id.quickStartButtonID);
        quickStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quickStart(view);
            }
        });

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recyclerViewID);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new HomeCardAdapter(getContext(), this, packNames);

        mRecyclerView.setAdapter(mAdapter);


        //Fill coaching frameworks and category keys list
        FirebaseDatabase.getInstance().getReference().child("users2").child(currentUserUID).child("packs").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String packName = null;
                String packID = null;
                long numWorkouts = -1;
                try {
                    packName = dataSnapshot.child("packName").getValue().toString();
                    packID = dataSnapshot.getKey();
                    numWorkouts = (long) dataSnapshot.child("packNumberOfWorkouts").getValue();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                packNames.add(packName);
                packIDs.add(packID);
                packNumberOfWorkoutsList.add(numWorkouts);

                // get down to questions, use on childadded to get ids
//                dataSnapshot.child("questions").addChildEventListener()
//
//                questionKeys.add(dataSnapshot.getKey());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

    }

    public void quickStart(View view) {
        Intent intent = new Intent(getContext(), TipSlidePagerActivity.class);
//TODO figure out how firebase structure going to be
        //TODO avoid hardcoding of workout to enter
        intent.putExtra("packID", packIDs.get(0));
        intent.putExtra("workoutID", "workout1");
        startActivity(intent);
    }

    @Override
    public void recyclerViewListClicked(View v, int position){
        Log.i("clicked","here");
        Intent intent = new Intent(getContext(), WorkoutSlidePagerActivity.class);
        //TODO create objects instead of multiple arrays with corresponding index
        intent.putExtra("packID", packIDs.get(position));
        intent.putExtra("currentUserUID", currentUserUID);
        intent.putExtra("packName", packNames.get(position));
        intent.putExtra("packNumberOfWorkouts", packNumberOfWorkoutsList.get(position));
        startActivity(intent);
    }

    @Override
    public void recyclerViewListClicked(View v, int position, String packID){
    }


}
