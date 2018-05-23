package com.example.anna.fr.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anna.fr.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddUserCommentActivity extends AppCompatActivity {

    private static final String TAG = "AddUserCommentActivity";

    private Context mContext;
    private ImageView backarrow,saveComment;
    private TextView userName;
    private EditText userComment;
    private RatingBar userRating;
    private String userID;
    private String currentUserName;
    private String res_id;
    private int cRating;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_comment);

        backarrow = (ImageView) findViewById(R.id.backArrow);
        saveComment = (ImageView) findViewById(R.id.saveComment);
        userName = (TextView) findViewById(R.id.display_name) ;
        userComment = (EditText) findViewById(R.id.writeReview) ;
        userRating = (RatingBar) findViewById(R.id.ratingBarWrite) ;
        mContext = AddUserCommentActivity.this;

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference2 = FirebaseDatabase.getInstance().getReference();

        res_id = getIntent().getStringExtra("res_id");

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null){
                    // user is signed in
                    Log.d(TAG, "onAuthStateChanged: signed_in: " + user.getUid());
                    userID = user.getUid();

                } else {
                    // user is signed out
                    Log.d(TAG, "onAuthStateChanged: signed_out");
                    userID = "";

                }
            }
        };

        userName.setText(currentUserName);
        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: Added information to database: \n" + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        myRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    String uID = ds.child("user_id").getValue(String.class);
                    String name = ds.child("username").getValue(String.class);


                    if(areSame(uID,userID)){
                        currentUserName = name;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        userRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                 cRating = (int) rating;
            }
        });

        saveComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Sumbit passed");
                String comment = userComment.getText().toString();

                Log.d(TAG, "onClick: Attempting to submit to database: \n" +
                        "userName: "+currentUserName+"\n"+
                        "comment: "+comment+"\n"+
                        "rating: "+cRating+"\n" +
                        "uID"+userID+"\n"
                );

                if(!currentUserName.equals("")&&!comment.equals("")&&cRating!=0){
                    UserComment uc = new UserComment(currentUserName,comment,cRating,userID);
                    myRef.child("restaurant_comments").child(res_id).child(userID).setValue(uc);

                    userComment.setText("");
                    userRating.setRating(0);


                    databaseReference.child("restaurant_details").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (final DataSnapshot ds: dataSnapshot.getChildren()){
                                String rID = ds.child("res_id").getValue(String.class);
                                if(areSame(res_id,rID)){
                                    databaseReference2.child("restaurant_comments").child(ds.child("res_id").getValue(String.class)).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            int sum = 0;
                                            int count = 0;
                                            for (DataSnapshot ds2 : dataSnapshot.getChildren()){
                                                sum += ds2.child("rating").getValue(float.class).intValue();
                                                count++;
                                            }
                                            Log.d(TAG, "onDataChange: ++++++++"+sum+" / "+count+" = "+ sum/count);
                                            databaseReference.child("restaurant_details").child(ds.child("res_id").getValue(String.class)).child("rating").setValue(sum/count);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                    Log.d(TAG, "onDataChange: rating bar ++++++");
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    finish();
                }else {
                    Toast.makeText(mContext, "Incomplete input, please check that you have rated and commented", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: naviagating back to 'HomeActivity'");

                finish();
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private boolean areSame(String string1, String string2){
        return string1.equals(string2);
    }
}
