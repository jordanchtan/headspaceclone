package com.example.jordan.smiletribematerial;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionSlideFragment extends Fragment {
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String mFileName = null;
    private MediaRecorder mRecorder = null;


    // Views
    ViewGroup rootView;
    TextView questionLabelView;
    TextView questionTextView;
    TextView countdownTimerView;
    private ImageButton recordStopButton;

    // Display
    private int positionArg;
    private String questionNumber;
    private String questionText;

    // Audio
    private MediaRecorder mediaRecorder;
    private String recordingName = UUID.randomUUID().toString() + ".3gp";

    // Firebase
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private String currentUserUID = currentUser.getUid();
    private String questionID;
    private String packID;
    private String workoutID;
    private String date;
    private String replyURL;

    private int NOT_RECORDING = 0;
    private int RECORDING = 1;

    private CountDownTimer timer;




    private void findRequiredViews() {
        questionLabelView = (TextView) rootView.findViewById(R.id.questionLabelID);
        questionTextView = (TextView) rootView.findViewById(R.id.questionTextID);
        countdownTimerView = rootView.findViewById(R.id.countdownTimerID);
        recordStopButton = (ImageButton) rootView.findViewById(R.id.tempNextButtonID);
    }


    private void initializeDisplay() {
        questionLabelView.setText(questionNumber);

    }

    public ViewGroup onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_question_slide, container, false);
        findRequiredViews();

// Record to the external cache directory for visibility
        mFileName = getActivity().getExternalCacheDir().getAbsolutePath();
        mFileName += "/audiorecordtest.3gp";

        ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_RECORD_AUDIO_PERMISSION);


        positionArg = getArguments().getInt("position", 0);
        questionNumber  = Integer.toString(positionArg + 1);
        packID = getArguments().getString("packID");
        workoutID = getArguments().getString("workoutID");



        // Get question text (required for setting display) and ID (required for submission)
        FirebaseDatabase.getInstance().getReference().child("packs")
                .child(packID).child("workouts").child(workoutID).child("questions").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot question : dataSnapshot.getChildren()) {
                    String databaseQuestionNumber = question.child("questionPosition").getValue().toString();
                    if (databaseQuestionNumber.equals(questionNumber)) {
                        questionText = question.child("questionText").getValue().toString();
                        Log.i("ques text", questionText);
                        questionTextView.setText(questionText);

                        questionID = question.getValue().toString();
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        initializeDisplay();

        return rootView;
    }

//    public void onSubmit(View view) {
//        uploadRecording();
//        FirebaseDatabase.getInstance().getReference().child("users").child(currentUserUID).child("responses")
//                .child(questionID).child("data").push().setValue(new QuestionSlideFragment.Response(date, replyURL));
//    }

//    public void uploadRecording() {
//        // Create a storage reference from our app
//
//
//        Uri file = Uri.fromFile(new File(filePathName));
//
//        final StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("voces").child(file.getLastPathSegment());
//        UploadTask uploadTask = storageRef.putFile(file);
//
//        // Register observers to listen for when the download is done or if it fails
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                Toast.makeText(getActivity(), "Upload failed", Toast.LENGTH_LONG).show();
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Task<Uri> downloadURL = storageRef.getDownloadUrl();
//                replyURL = downloadURL.toString();
//            }
//        });
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recordStopButton.setTag(NOT_RECORDING);
        recordStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((int) recordStopButton.getTag() == NOT_RECORDING) {
                    recordStopButton.setTag(RECORDING);

                    setViewAsRecording();
                    startRecording();
                } else {
                    recordStopButton.setTag(NOT_RECORDING);
                    recordStopButton.setImageResource(R.drawable.ic_done_black_24dp);
//                recordStopButton.setColorFilter(getResources().getColor(R.color.lightgreen500), PorterDuff.Mode.MULTIPLY);
                    mOnButtonClickListener.onButtonClicked(v);
                    stopRecording();
                }
            }
        });
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            timer = new CountDownTimer(3000, 1000) {

                public void onTick(long millisUntilFinished) {
                    int currentTime = (int) (millisUntilFinished / 1000) + 1;
                        countdownTimerView.setText(Integer.toString(currentTime));
                }

                public void onFinish() {
                    setViewAsRecording();
                    startRecording();
                }
            };
            timer.start();
        }
    }

    private void setViewAsRecording() {
        timer.cancel();
        countdownTimerView.setText("Recording...");
        recordStopButton.setImageResource(R.drawable.ic_stop_black_24dp);
    }



    public static class Response {
        String date;
        String reply;
        public Response(String date, String reply) {
            this.date = date;
            this.reply = reply;
        }
    }



    private OnButtonClickListener mOnButtonClickListener;

    interface OnButtonClickListener{
        void onButtonClicked(View view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnButtonClickListener = (OnButtonClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(((Activity) context).getLocalClassName()
                    + " must implement OnButtonClickListener");
        }

    }
    //no internet?
// Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) getActivity().finish();

    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e("prepe", "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

    }

}
