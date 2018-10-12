package com.example.jordan.smiletribematerial;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    EditText userEmail;
    EditText userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        userEmail = (EditText) findViewById(R.id.emailID);
        userPassword = (EditText) findViewById(R.id.passwordID);

        if (mAuth.getCurrentUser() != null) {
            // already signed in
        } else {
            // not signed in
        }
    }

    public void login(View view) {

        mAuth.signInWithEmailAndPassword(userEmail.getText().toString(), userPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("EmailPassword", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("EmailPassword", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }
                    }
                });
    }

    public void register(View view) {
        mAuth.createUserWithEmailAndPassword(userEmail.getText().toString(), userPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // TODO
                            DatabaseReference firebaseRef = FirebaseDatabase.getInstance().getReference();
                            DatabaseReference currentUserRef = firebaseRef.child("users2").child(task.getResult().getUser().getUid());
                            DatabaseReference userInfoRef = currentUserRef.child("userInfo");
                            userInfoRef.child("email").setValue(userEmail.getText().toString());
                            DatabaseReference statsRef = currentUserRef.child("stats");
                            DatabaseReference streaksRef = statsRef.child("streaks");
                            streaksRef.child("currentStreak").setValue(0);
                            streaksRef.child("longestStreak").setValue(0);

                            DatabaseReference packsRef = currentUserRef.child("packs");
                            DatabaseReference basics1Ref = packsRef.child("basics1");
                            basics1Ref.child("packName").setValue("Basics 1");
                            basics1Ref.child("packNumberOfWorkouts").setValue(3);

                            DatabaseReference basics2Ref = packsRef.child("basics2");
                            basics2Ref.child("packName").setValue("Basics 2");
                            basics2Ref.child("packNumberOfWorkouts").setValue(3);

                            DatabaseReference basics3Ref = packsRef.child("basics3");
                            basics3Ref.child("packName").setValue("Basics 3");
                            basics3Ref.child("packNumberOfWorkouts").setValue(3);

                            // Sign in success, update UI with the signed-in user's information
                            Log.d("EmailPassword", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("EmailPassword", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public void updateUI(FirebaseUser user) {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);

        startActivity(intent);
    }
}

