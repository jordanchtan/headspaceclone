package com.example.jordan.smiletribematerial;

import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PackInfoActivity extends AppCompatActivity {
    ImageButton exitButton;
    TextView packNameView;
    TextView packNumberOfWorkoutsView;
    TextView packDescriptionView;
    MaterialButton addRemovePackButton;
    String packID;
    String packName;
    String packNumberOfWorkouts;
    String packDescription;
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private String currentUserUID = currentUser.getUid();
    DatabaseReference firebaseRef = FirebaseDatabase.getInstance().getReference();

    DatabaseReference currentUserRef = firebaseRef.child("users2").child(currentUserUID);
    DatabaseReference userPacksRef = currentUserRef.child("packs");

    int USER_HAS_PACK = 1;
    int USER_NO_PACK = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pack_info);
        exitButton = findViewById(R.id.exitButtonID);
        packNameView = findViewById(R.id.packNameID);
        packNumberOfWorkoutsView = findViewById(R.id.packNumberOfWorkoutsID);
        packDescriptionView = findViewById(R.id.packDescriptionID);
        addRemovePackButton = findViewById(R.id.addRemovePackButtonID);

        setUserHasPackText();


        packID = getIntent().getStringExtra("packID");
        Log.i("packID", packID);

        firebaseRef.child("packs")
                .child(packID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                packName = (String) dataSnapshot.child("packName").getValue();
                packDescription = (String) dataSnapshot.child("packDescription").getValue();
                packNumberOfWorkouts =  Long.toString((Long) dataSnapshot.child("packNumberOfWorkouts").getValue());
                packNameView.setText(packName);
                packDescriptionView.setText(packDescription);
                packNumberOfWorkoutsView.setText(packNumberOfWorkouts);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });




    }

    private void addPack() {
        DatabaseReference newPackRef = userPacksRef.child(packID);
        newPackRef.child("packName").setValue(packName);
        newPackRef.child("packNumberOfWorkouts").setValue(Long.parseLong(packNumberOfWorkouts));
    }

    private void removePack() {
        DatabaseReference packToDelete = userPacksRef.child(packID);
        packToDelete.removeValue();
    }

    private void setUserHasPackText() {
        userPacksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(packID)) {
                    addRemovePackButton.setTag(USER_HAS_PACK);
                    addRemovePackButton.setText("Remove Pack");
                } else {
                    addRemovePackButton.setTag(USER_NO_PACK);
                    addRemovePackButton.setText("Add Pack");
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public void clickAddRemoveButton(View v) {
        if ((int) v.getTag() == USER_HAS_PACK) {
            removePack();
        } else {
            addPack();
        }
    }

    public void exitInfo(View view) {
        finish();
    }


}
