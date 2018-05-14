package com.example.anna.fr.me;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.example.anna.fr.login.LoginActivity;
import com.example.anna.fr.R;
import com.example.anna.fr.utils.BottomNavigationViewHelper;
import com.example.anna.fr.utils.UniversalImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by anna on 13/4/18.
 */

public class MeActivity extends AppCompatActivity{
    private static final String TAG = "MeActivity";
    private static final int ACTIVITY_NUM = 3;

    private ProgressBar mProgressBar;
    private ImageView profilePhoto;
    private TextView tvUsername;
    private EditText mUsername;

    private Context mContext = MeActivity.this;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d(TAG,"onCreate: started.");
//        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
//        mProgressBar.setVisibility(View.GONE);

        init();
//        setupBottomNavigationView();
//        setupToolbar();
//        setupActivityWidgets();
//        setProfileImage();
//        setupFirebaseAuth();
    }

    private void init(){
        Log.d(TAG, "init: inflating " + getString(R.string.me_fragment));

        MeFragment fragment = new MeFragment();
        FragmentTransaction transaction = MeActivity.this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(getString(R.string.me_fragment));
        transaction.commit();
    }

//    private void setProfileImage(){
//        Log.d(TAG, "setProfileImage: setting profile photo.");
//        String imgURL = "pbs.twimg.com/profile_images/875443327835025408/ZvmtaSXW_400x400.jpg";
//        UniversalImageLoader.setImage(imgURL, profilePhoto, mProgressBar, "https://");
//    }
//    private void setupActivityWidgets(){
//        mUsername = (EditText) findViewById(R.id.input_username);
//        tvUsername = (TextView) findViewById(R.id.display_name);
//        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
//        mProgressBar.setVisibility(View.GONE);
//        profilePhoto = (ImageView) findViewById(R.id.profile_photo);
//    }
//
//    //transfer to new page
//    private void setupToolbar(){
//        Toolbar toolbar = (Toolbar) findViewById(R.id.profileToolBar);
//        setSupportActionBar(toolbar);
//
//        ImageView profileMenu = (ImageView) findViewById(R.id.profileMenu);
//        profileMenu.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: navigating to account settings.");
//                Intent intent = new Intent(mContext, AccountSettingsActivity.class);
//                startActivity(intent);
//
//            }
//        });
//
//    }
//
//    /*
//    -----------Firebase-----------------
//     */
//
//    //checks to see if the @param 'user' is logged in
//
//    /*
//        private void checkCurrentUser (FirebaseUser user){
//        Log.d(TAG, "checkCurrentUser: checking if user is logged in.");
//
//        if (user == null){
//            Intent intent = new Intent (mContext, LoginActivity.class);
//            startActivity(intent);
//            }
//        }
//    */
//
//    //set up the firebase auth object
//    private void setupFirebaseAuth(){
//        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth");
//
//        mAuth = FirebaseAuth.getInstance();
//
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//
//                // check if the user is logged in
//                //checkCurrentUser(user);
//
//                if (user != null){
//                    // user is signed in
//                    Log.d(TAG, "onAuthStateChanged: signed_in: " + user.getUid());
//                } else {
//                    // user is signed out
//                    Log.d(TAG, "onAuthStateChanged: signed_out");
//
//                    TextView linkLogin = (TextView) findViewById(R.id.display_name);
//                    linkLogin.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Log.d(TAG, "onClick: navigating to login screen");
//                            Intent intent = new Intent (MeActivity.this, LoginActivity.class);
//                            startActivity(intent);
//                        }
//                    });
//                }
//            }
//        };
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        mAuth.addAuthStateListener(mAuthListener);
//        mAuth.getCurrentUser();
//        //checkCurrentUser(mAuth.getCurrentUser());
//    }
//
//    public void onStop(){
//        super.onStop();
//        if(mAuthListener != null){
//            mAuth.removeAuthStateListener(mAuthListener);
//        }
//    }
//
//    /*
//    -----------Firebase-----------------
//     */
//
//    private void setupBottomNavigationView() {
//        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
//        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavViewBar);
//        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationView);
//        Menu menu = bottomNavigationView.getMenu();
//        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
//        menuItem.setChecked(true);
//
//    }
//
//
}
