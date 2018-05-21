package com.example.anna.fr.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.anna.fr.R;
import com.example.anna.fr.login.LoginActivity;
import com.example.anna.fr.models.User;
import com.example.anna.fr.models.UserAccountSettings;
import com.example.anna.fr.models.UserSettings;
import com.example.anna.fr.utils.BottomNavigationViewHelper;
import com.example.anna.fr.utils.FirebaseMethods;
import com.example.anna.fr.utils.UniversalImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class MeFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "MeFragment";
    private static final int ACTIVITY_NUM = 3;
    private Context mContext;


    private TextView mDisplayname;
    private ProgressBar mProgressBar;
    private CircleImageView mProfilePhoto;
    private Toolbar toolbar;
    private ImageView profileMenu;
    private Button btnFavourite, btnHistory, btnReview, btnHelp;
    private BottomNavigationView bottomNavigationView;
    private IMeActivity mIMeActivity;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mDisplayname = (TextView) view.findViewById(R.id.display_name);
        mProfilePhoto = (CircleImageView) view.findViewById(R.id.profile_photo);
        mProgressBar = (ProgressBar) view.findViewById(R.id.profileProgressBar);
        toolbar = (Toolbar) view.findViewById(R.id.profileToolBar);
        profileMenu = (ImageView) view.findViewById(R.id.profileMenu);
        bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.bottomNavViewBar);
        mContext = getActivity();
        mFirebaseMethods = new FirebaseMethods(getActivity());
        mProgressBar.setVisibility(View.GONE);
        btnFavourite = (Button) view.findViewById(R.id.buttonFavourite);
        btnHistory = (Button) view.findViewById(R.id.buttonHistory);
        btnReview = (Button) view.findViewById(R.id.buttonReview);
        btnHelp = (Button) view.findViewById(R.id.buttonHelp);

        Log.d(TAG, "onCreateView: started");

        setupFirebaseAuth();
        setupBottomNavigationView();
        setupToolbar();

        TextView editProfile = (TextView) view.findViewById(R.id.textEditProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to "+ mContext.getString(R.string.edit_profile_fragment));
                Intent intent = new Intent(getActivity(), AccountSettingsActivity.class);
                intent.putExtra(getString(R.string.calling_activity), getString(R.string.me_activity));
                startActivity(intent);
            }
        });

        btnFavourite.setOnClickListener(this);
        btnHistory.setOnClickListener(this);
        btnReview.setOnClickListener(this);
        btnHelp.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonFavourite:{
                break;
            }
            case R.id.buttonHistory:{
                break;
            }
            case R.id.buttonReview:{
                break;
            }
            case R.id.buttonHelp:{
                mIMeActivity.inflateFragment(getString(R.string.help_fragment));
                break;
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mIMeActivity = (IMeActivity) getActivity();
    }

    private void setProfileWidgets(UserSettings userSettings){
        Log.d(TAG, "setProfileWidgets: "+ userSettings.toString());
//        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: "+ userSettings.getSettings().getDisplay_name());

        //User user = userSettings.getUser();
        UserAccountSettings settings = userSettings.getSettings();

        UniversalImageLoader.setImage(settings.getProfile_photo(),mProfilePhoto, null, "");
        mDisplayname.setText(settings.getDisplay_name());

    }

    //transfer to AccountSettingActivity page
    private void setupToolbar(){
        ((MeActivity)getActivity()).setSupportActionBar(toolbar);
        profileMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to account settings.");
                Intent intent = new Intent(mContext, AccountSettingsActivity.class);
                startActivity(intent);

            }
        });

    }

    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }

    /*
    -----------Firebase-----------------
     */

    //set up the firebase auth object
    private void setupFirebaseAuth(){

        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                // check if the user is logged in
                //checkCurrentUser(user);

                if (user != null){
                    // user is signed in
                    Log.d(TAG, "onAuthStateChanged: signed_in: " + user.getUid());

                } else {
                    // user is signed out
                    Log.d(TAG, "onAuthStateChanged: signed_out");

                    Intent intent = new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                }
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //retrieve user information from the database
                setProfileWidgets(mFirebaseMethods.getUserSettings(dataSnapshot));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthListener);
    }

    public void onStop(){
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    /*
    -----------Firebase-----------------
     */

}
