package com.example.jordan.smiletribematerial;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
public class DiscoverSeriesFragment extends Fragment implements RecyclerViewClickListener {
    ArrayList<String> foundationPackNames = new ArrayList<>();
    ArrayList<String> foundationPackIDs = new ArrayList<>();
    RecyclerView.Adapter foundationAdapter;
    RecyclerView mRecyclerView1;

    ArrayList<String> growthPackNames = new ArrayList<>();
    ArrayList<String> growthPackIDs = new ArrayList<>();
    RecyclerView.Adapter growthAdapter;
    RecyclerView mRecyclerView2;

    ArrayList<String> masteryPackNames = new ArrayList<>();
    ArrayList<String> masteryPackIDs = new ArrayList<>();
    RecyclerView.Adapter masteryAdapter;
    RecyclerView mRecyclerView3;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    String currentUserUID = currentUser.getUid();

    public static DiscoverSeriesFragment newInstance() {
        return new DiscoverSeriesFragment();
    }

    public DiscoverSeriesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discover_series, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //TODO refactor these to one func
        initializeSeriesFoundation();
        initializeSeriesGrowth();
        initializeSeriesMastery();
    }

    public void initializeSeriesFoundation() {
        mRecyclerView1 = (RecyclerView) getView().findViewById(R.id.recyclerViewID1);
        RecyclerView.LayoutManager mLayoutManager;

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView1.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView1.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        foundationAdapter = new DiscoverCardAdapter(getContext(), this, foundationPackNames, foundationPackIDs);
        mRecyclerView1.setAdapter(foundationAdapter);


        //Fill coaching frameworks and category keys list
        FirebaseDatabase.getInstance().getReference().child("repository").child("repoCategory").child("series").child("foundation").child("packs").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String coachingFramework = null;
                String packID = null;

                try {
                    coachingFramework = dataSnapshot.child("packName").getValue().toString();
                    packID = dataSnapshot.getKey();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                foundationPackNames.add(coachingFramework);
                foundationPackIDs.add(packID);
                foundationAdapter.notifyDataSetChanged();
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

    public void initializeSeriesGrowth() {
        mRecyclerView2 = (RecyclerView) getView().findViewById(R.id.recyclerViewID2);
        RecyclerView.LayoutManager mLayoutManager;

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView2.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView2.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        growthAdapter = new DiscoverCardAdapter(getContext(), this, growthPackNames, growthPackIDs);
        mRecyclerView2.setAdapter(growthAdapter);


        //Fill coaching frameworks and category keys list
        FirebaseDatabase.getInstance().getReference().child("repository").child("repoCategory").child("series").child("growth").child("packs").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String coachingFramework = null;
                String packID = null;

                try {
                    coachingFramework = dataSnapshot.child("packName").getValue().toString();
                    packID = dataSnapshot.getKey();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                growthPackNames.add(coachingFramework);
                growthPackIDs.add(packID);

                growthAdapter.notifyDataSetChanged();
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

    public void initializeSeriesMastery() {
        mRecyclerView3 = (RecyclerView) getView().findViewById(R.id.recyclerViewID3);
        RecyclerView.LayoutManager mLayoutManager;

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView3.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView3.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        masteryAdapter = new DiscoverCardAdapter(getContext(), this, masteryPackNames, masteryPackIDs);
        mRecyclerView3.setAdapter(masteryAdapter);


        //Fill coaching frameworks and category keys list
        FirebaseDatabase.getInstance().getReference().child("repository").child("repoCategory").child("series").child("mastery").child("packs").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String coachingFramework = null;
                String packID = null;

                try {
                    coachingFramework = dataSnapshot.child("packName").getValue().toString();
                    packID = dataSnapshot.getKey();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                masteryPackNames.add(coachingFramework);
                masteryPackIDs.add(packID);

                masteryAdapter.notifyDataSetChanged();
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

    @Override
    public void recyclerViewListClicked(View v, int position) {

    }

    @Override
    public void recyclerViewListClicked(View v, int position, String packID){
        Intent intent = new Intent(getContext(), PackInfoActivity.class);
        intent.putExtra("packID", packID);
        startActivity(intent);
    }

}
