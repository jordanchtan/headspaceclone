package com.example.jordan.smiletribematerial;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
public class DiscoverSinglesFragment extends Fragment {
    ArrayList<String> allTests = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    String currentUserUID = currentUser.getUid();

    public static DiscoverSinglesFragment newInstance() {
        return new DiscoverSinglesFragment();
    }

    public DiscoverSinglesFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discover_singles, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recyclerViewID);
//
//        // use this setting to improve performance if you know that changes
//        // in content do not change the layout size of the RecyclerView
//        mRecyclerView.setHasFixedSize(true);
//
//        // use a linear layout manager
//        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//
//        // specify an adapter (see also next example)
//        mAdapter = new HomeCardAdapter(allTests);
//        mRecyclerView.setAdapter(mAdapter);
//
//
//        //Fill coaching frameworks and category keys list
//        FirebaseDatabase.getInstance().getReference().child("tests").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                String test = null;
//                try {
//                    test = dataSnapshot.child("testName").getValue().toString();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                Log.i("all tests", test);
//                allTests.add(test);
//                mAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {}
//        });

    }
}
