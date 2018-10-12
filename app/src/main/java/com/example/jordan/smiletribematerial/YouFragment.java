package com.example.jordan.smiletribematerial;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class YouFragment extends Fragment {
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    String currentUserUID = currentUser.getUid();
    long currentStreakNumber = -1;
    long longestStreakNumber = -1;
    String email;
    TextView currentStreakNumView;
    TextView longestStreakNumView;
    TextView userEmailView;

    public static YouFragment newInstance() {
        return new YouFragment();
    }

    public YouFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_you, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MaterialButton logoutButton = (MaterialButton) view.findViewById(R.id.logoutButtonID);
        currentStreakNumView = (TextView) view.findViewById(R.id.currStreakNumID);
        longestStreakNumView = (TextView) view.findViewById(R.id.longStreakNumID);
        userEmailView = (TextView) view.findViewById(R.id.userEmailID);

        DatabaseReference firebaseRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference currentUserRef = firebaseRef.child("users2").child(currentUserUID);
        DatabaseReference statsRef = currentUserRef.child("stats");
        DatabaseReference userInfoRef = currentUserRef.child("userInfo");
        DatabaseReference streaksRef = statsRef.child("streaks");

        streaksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentStreakNumber = (long) dataSnapshot.child("currentStreak").getValue();
                longestStreakNumber = (long) dataSnapshot.child("longestStreak").getValue();
                Log.i("curr", Long.toString(currentStreakNumber));
                Log.i("long", Long.toString(longestStreakNumber));
                currentStreakNumView.setText(Long.toString(currentStreakNumber));
                longestStreakNumView.setText(Long.toString(longestStreakNumber));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        userInfoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                email = (String) dataSnapshot.child("email").getValue();
                userEmailView.setText(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    public void logout() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Intent intent = new Intent(getContext(), MainActivity.class);

        startActivity(intent);
        getActivity().finish();
    }

}
