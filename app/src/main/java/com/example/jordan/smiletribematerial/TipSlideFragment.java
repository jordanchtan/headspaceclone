package com.example.jordan.smiletribematerial;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class TipSlideFragment extends Fragment {
//what fragment needs: tipname, tip text
    //tipname, text from packid>>workoutID
    ViewGroup rootView;
    TextView tipNameView;
    TextView tipTextView;
    private int tipPosition;
    private String tipNumber;
    String tipName;
    String tipText;
    private String packID;
    private String workoutID;
    int num_tips;
    boolean isLastTip = false;
    MaterialButton startQuestionsButton;
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private String currentUserUID = currentUser.getUid();

    public TipSlideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_tip_slide, container, false);
        tipNameView = rootView.findViewById(R.id.tipNameID);
        tipTextView = rootView.findViewById(R.id.questionTextID);

        tipPosition = getArguments().getInt("position", 0);
        tipNumber = Integer.toString(tipPosition + 1);

        num_tips = getArguments().getInt("num_tips");

        isLastTip = Integer.toString(num_tips).equals(tipNumber);
        startQuestionsButton = rootView.findViewById(R.id.startQuestionsButtonID);

        if (isLastTip) {

            startQuestionsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startQuestions();

                }
            });
            startQuestionsButton.setVisibility(View.VISIBLE);
        } else {
            startQuestionsButton.setVisibility(View.GONE);
        }




        packID = getArguments().getString("packID");
        workoutID = getArguments().getString("workoutID");

        FirebaseDatabase.getInstance().getReference().child("packs").child(packID).child("workouts").child(workoutID)
                .child("tips").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot tip : dataSnapshot.getChildren()) {
                    String databaseTipPosition = tip.child("tipPosition").getValue().toString();
                    if (databaseTipPosition.equals(tipNumber)) {
                        tipName = tip.child("tipName").getValue().toString();
                        tipNameView.setText(tipName);
                        tipText = tip.child("tipText").getValue().toString();
                        tipTextView.setText(tipText);
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
        return rootView;
    }

    public void startQuestions() {
        Intent intent = new Intent(getContext(), QuestionSlidePagerActivity.class);
        intent.putExtra("packID", packID);
        intent.putExtra("workoutID", workoutID);
        intent.putExtra("currentUserUID", currentUserUID);
        startActivity(intent);
    }


}
