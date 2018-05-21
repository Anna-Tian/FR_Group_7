package com.example.anna.fr.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anna.fr.R;
import com.example.anna.fr.models.RestaurantDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RestaurantActivity extends AppCompatActivity {
    private static final String TAG = "RestaurantActivity";


    private Context mContext;
    private String name, address, profile_photo;
    private long phone;
    private TextView mPhone, mName, mAddress;
    private ImageView mProfilePhoto;
    private RatingBar mRestaurantRating;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethods firebaseMethods;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);
        mContext = RestaurantActivity.this;
        firebaseMethods = new FirebaseMethods(mContext);
        Log.d(TAG, "onCreate: start");

        myRef = FirebaseDatabase.getInstance().getReference().child(mContext.getString(R.string.dbname_restaurant_details));
        myRef.keepSynced(true);




        initWidgets();
    }


    private void initWidgets(){
        Log.d(TAG, "intWidgets: Initializing Widgets.");
        mName = (TextView) findViewById(R.id.restaurantName);
        mPhone = (TextView) findViewById(R.id.restaurantPhone);
        mProfilePhoto = (ImageView) findViewById(R.id.restaurantImage);
        mAddress = (TextView) findViewById(R.id.restaurantAddress);
//        mRestaurantRating = (RatingBar) findViewById(R.id.ratingBar);

        mContext = RestaurantActivity.this;
        RestaurantDetails restaurantDetails = new RestaurantDetails();
        UniversalImageLoader.setImage(restaurantDetails.getProfile_photo(),mProfilePhoto, null, "");
        name = mName.getText().toString();
        address = mAddress.getText().toString();
        phone  = mPhone.getText().length();

    }

    /*
    -----------Firebase-----------------
     */



    /*
    -----------Firebase-----------------
     */
}
