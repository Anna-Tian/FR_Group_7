package com.example.anna.fr.utils;



import android.content.Context;

import android.content.Intent;

import android.os.Bundle;

import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;

import android.util.Log;

import android.view.View;

import android.widget.ImageView;

import android.widget.RatingBar;

import android.widget.TextView;

import android.widget.Toast;



import com.example.anna.fr.R;

import com.example.anna.fr.home.HomeActivity;

import com.example.anna.fr.models.RestaurantDetails;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DataSnapshot;

import com.google.firebase.database.DatabaseError;

import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;



import java.util.ArrayList;



public class ViewUserCommentActivity extends AppCompatActivity {



    private static final String TAG = "ViewUserCommentActivity";



    private String r_id;



    private Context mContext;



    private ArrayList<String> userId = new ArrayList<>();



    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseMethods firebaseMethods;

    private FirebaseDatabase mFirebaseDatabase;

    private DatabaseReference myRef;

    DatabaseReference databaseReference;



    private RecyclerView mResList;



    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_view_comments);

        mContext = ViewUserCommentActivity.this;



        r_id = getIntent().getStringExtra("res_id");

        firebaseMethods = new FirebaseMethods(mContext);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        Log.d(TAG, "onCreate: start");



        myRef = FirebaseDatabase.getInstance().getReference().child("restaurant_comments").child(r_id);

        myRef.keepSynced(true);





        mResList = (RecyclerView) findViewById(R.id.recyclerView);

        mResList.setHasFixedSize(true);

        mResList.setLayoutManager(new LinearLayoutManager(this));



        setUpList();





        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);

        backArrow.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                Log.d(TAG, "onClick: naviagating back to 'HomeActivity'");

                finish();

            }

        });



    }





    private void setUpList(){

        Log.d(TAG, "setUpList: setting up +++++++++"+r_id);

        databaseReference.child("restaurant_comments").child(r_id).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren()){

                    userId.add(ds.child("uID").getValue(String.class));

                    Log.d(TAG, "onDataChange: added "+ds.child("uID").getValue(String.class));

                }

                initRecyclerView();

            }



            @Override

            public void onCancelled(DatabaseError databaseError) {



            }

        });





    }



    private void initRecyclerView(){

        Log.d(TAG, "initRecyclerView: started +++++++");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        CommentAdapter adapter = new CommentAdapter(this,userId,r_id);

        recyclerView.setAdapter(adapter);

    }





}