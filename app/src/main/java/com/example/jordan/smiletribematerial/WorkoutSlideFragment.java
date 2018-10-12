package com.example.jordan.smiletribematerial;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class WorkoutSlideFragment extends Fragment {
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private String currentUserUID = currentUser.getUid();
    private String packID;
    private String workoutID;
    private int workoutPosition;
    private String workoutNumber;
    private String packName;
    String workoutName;
    ViewGroup rootView;
    TextView workoutNameView;

    public WorkoutSlideFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_workout_slide, container, false);

        workoutPosition = getArguments().getInt("position", 0);
        workoutNumber = Integer.toString(workoutPosition + 1);
        packID = getArguments().getString("packID");
        packName = getArguments().getString("packName");

        TextView packNameView = rootView.findViewById(R.id.packName);
        workoutNameView = rootView.findViewById(R.id.workoutName);



// Get question text (required for setting display) and ID (required for submission)
        FirebaseDatabase.getInstance().getReference().child("packs").child(packID)
                .child("workouts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot workout : dataSnapshot.getChildren()) {
                    String databaseWorkoutPosition = workout.child("workoutPosition").getValue().toString();
                    if (databaseWorkoutPosition.equals(workoutNumber)) {
                        workoutName = workout.child("workoutName").getValue().toString();
                        workoutID = workout.getKey();
                        workoutNameView.setText(workoutName);
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        packNameView.setText(packName);


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button startItemButton = rootView.findViewById(R.id.startItemID);
        startItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startItem();

            }
        });

    }

    public void startItem() {
        Intent intent = new Intent(getContext(), TipSlidePagerActivity.class);
        intent.putExtra("packID", packID);
        intent.putExtra("workoutID", workoutID);
        intent.putExtra("currentUserUID", currentUserUID);
        startActivity(intent);
    }
}
