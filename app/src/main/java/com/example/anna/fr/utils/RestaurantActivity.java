package com.example.anna.fr.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

public class RestaurantActivity extends AppCompatActivity {
    private static final String TAG = "RestaurantActivity";
    private static final int ACTIVITY_NUM = 0;


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

        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: naviagating back to 'HomeActivity'");
                finish();
            }
        });



        initWidgets();
        initImageLoader();

        getIncomingIntent();
    }

    private void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent: check for incoming intent");

        if(getIntent().hasExtra("name")&&getIntent().hasExtra("profilePhoto")&&getIntent().hasExtra("address")/*&&getIntent().hasExtra("phone")*/){
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            String rName = getIntent().getStringExtra("name");
            String rProfilePhoto = getIntent().getStringExtra("profilePhoto");
            String rAddress = getIntent().getStringExtra("address");
//            String rPhone = getIntent().getStringExtra("phone");

            setRestaurant(rName,rAddress,/*rPhone,*/rProfilePhoto,getApplicationContext());
        }
    }

    private void setRestaurant(String rName,String rAddress,/* String rPhone,*/String rProfilePhoto, Context ctx ){
        Log.d(TAG, "setRestaurant: setting profile photo name and address");

        TextView name = (TextView) findViewById(R.id.restaurantName);
        name.setText(rName);

        TextView address =(TextView) findViewById(R.id.restaurantAddress);
        address.setText(rAddress);

//        TextView phone = findViewById(R.id.restaurantPhone);
//        phone.setText(rPhone);

        ImageView profilePhoto =(ImageView) findViewById(R.id.restaurantImage);
        Picasso.with(ctx).load(rProfilePhoto).into(profilePhoto);
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

    private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(mContext);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }
    private void setupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.homeBar);
        setSupportActionBar(toolbar);

        ImageView filterMenu = (ImageView) findViewById(R.id.filterMenu);
        filterMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to filter selection.");
                Intent intent = new Intent(mContext, FilterActivity.class);
                startActivity(intent);

            }
        });

    }

    // BottomNavigationView setup
    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }

    /*
    -----------Firebase-----------------
     */



    /*
    -----------Firebase-----------------
     */
}
