package com.example.anna.fr.me;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anna.fr.R;
import com.example.anna.fr.dialogs.ConfirmPasswordDialog;
import com.example.anna.fr.login.LoginActivity;
import com.example.anna.fr.models.User;
import com.example.anna.fr.models.UserAccountSettings;
import com.example.anna.fr.models.UserSettings;
import com.example.anna.fr.utils.FirebaseMethods;
import com.example.anna.fr.utils.UniversalImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by anna on 17/4/18.
 */

public class EditProfileFragment extends Fragment implements
        ConfirmPasswordDialog.OnConfirmPasswordListener{


    @Override
    public void onConfirmPassword(String password) {

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(mAuth.getCurrentUser().getEmail(), password);

        ///////////////// Prompt the user to re-provide their sign-in credentials
        mAuth.getCurrentUser().reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task <Void> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "User re-authenticated.");

                            ////////////////// check to see if the email is not already present in the database
                            mAuth.fetchProvidersForEmail(mEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                                @Override
                                public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                                    if (task.isSuccessful()){
                                        if(task.getResult().getProviders().size() == 1){
                                            Log.d(TAG, "onComplete: that email is already in use");
                                            Toast.makeText(getActivity(),"That email is already in use", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            Log.d(TAG, "onComplete: That email is available.");

                                            /////////////////////////the email is available so update it
                                            mAuth.getCurrentUser().updateEmail(mEmail.getText().toString())
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Log.d(TAG, "User email address updated.");
                                                                Toast.makeText(getActivity(),"email updated", Toast.LENGTH_SHORT).show();
                                                                mFirebaseMethods.updateEmail(mEmail.getText().toString());
                                                            }
                                                        }
                                                    });
                                        }
                                    }else{
                                            Log.e(TAG, "onComplete: re-authentication2 failed.");
                                        }
                                }
                            });
                        }else {
                            Log.e(TAG, "onComplete: re-authentication failed.");
                            Toast.makeText(getActivity(),"Invalid Password", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private static final String TAG = "EditProfileFragment";
    private Context mContext;

    //editprofile fragment widgets
    private EditText mUsername, mDisplayname, mAddress, mDescription, mPhone, mEmail;
    private TextView mChangeProfilePhoto;
    private String userID;
    private CircleImageView mProfilePhoto;
    private ImageView backArrow, checkmark;

    //variables
    private UserSettings mUserSettings;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;

    @Nullable
    @Override


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_editprofile, container, false);
        mProfilePhoto = (CircleImageView) view.findViewById(R.id.profile_photo);
        mUsername = (EditText) view.findViewById(R.id.username);
        mDisplayname = (EditText) view.findViewById(R.id.display_name);
        mAddress = (EditText) view.findViewById(R.id.address);
        mDescription = (EditText) view.findViewById(R.id.description);
        mPhone = (EditText) view.findViewById(R.id.phone);
        mEmail = (EditText) view.findViewById(R.id.email);
        mChangeProfilePhoto = (TextView) view.findViewById(R.id.changeProfilePhoto);
        backArrow = (ImageView) view.findViewById(R.id.backArrow);
        checkmark = (ImageView) view.findViewById(R.id.saveChanges);
        mFirebaseMethods = new FirebaseMethods(getActivity());

        setupFirebaseAuth();
//        setProfileImage();

        //setup the backarraw for navigating back to "ProfileActivity"
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: naviagating back to 'ProfileActivity'");
                getActivity().finish();
            }
        });

        checkmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: attempting to save changes.");
                saveProfileSettings();
            }
        });

        return view;
    }

    /**
     * retrieves the data contained in the widgets and submits it to the database
     * before doing so it checks to make sure the username chosen is unique
     */
    private void saveProfileSettings(){
        final String username = mUsername.getText().toString();
        final String displayName = mDisplayname.getText().toString();
        final String address = mAddress.getText().toString();
        final String description = mDescription.getText().toString();
        final long phone = Long.parseLong(mPhone.getText().toString());
        final String email = mEmail.getText().toString();

        //case 1: if the user made a change to their username
        if (!mUserSettings.getUser().getUsername().equals(username)){
            checkIfUsernameExists(username);
        }
        //case 2: if the user made a change to their email
        else if (!mUserSettings.getUser().getEmail().equals(email)){
            // see the above "onConfirmPassword"

            //step1: reauthenticate
            //      -confirm the password and email
            ConfirmPasswordDialog dialog = new ConfirmPasswordDialog();
            dialog.show(getFragmentManager(), getString(R.string.confirm_password_dialog));
            dialog.setTargetFragment(EditProfileFragment.this, 1);
            //step2: check if the email already is registered
            //      -'fetchProvidersForEmail'(String email) -- check the email that you submitted and takes the email parameter here

            //step3: change the email
            //      -submit the new email to the database and authentication
        }
        else if (!mUserSettings.getSettings().getDisplay_name().equals(displayName)){
            mFirebaseMethods.updateUserSettings(displayName, null, null, 0);
            Toast.makeText(getActivity(), "Changes Saved", Toast.LENGTH_SHORT).show();
        }
        else if (!mUserSettings.getUser().getAddress().equals(address)){
            mFirebaseMethods.updateUserSettings(null, address, null, 0);
            Toast.makeText(getActivity(), "Changes Saved", Toast.LENGTH_SHORT).show();
        }
        else if (!mUserSettings.getUser().getDescription().equals(description)){
            mFirebaseMethods.updateUserSettings(null, null, description, 0);
            Toast.makeText(getActivity(), "Changes Saved", Toast.LENGTH_SHORT).show();
        }
        else if (!mUserSettings.getUser().getDescription().equals(phone)){
            mFirebaseMethods.updateUserSettings(null, null, null, phone);
            Toast.makeText(getActivity(), "Changes Saved", Toast.LENGTH_SHORT).show();

        }
    }

    /**
     * check if @param username already exists in the database
     * @param username
     */
    private void checkIfUsernameExists(final String username) {
        Log.d(TAG, "checkIfUsernameExists: checking if " + username + " already exists");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child(getString(R.string.dbname_users))
                .orderByChild(getString(R.string.field_username))
                .equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    //add the username
                    mFirebaseMethods.updateUsername(username);
                    Toast.makeText(getActivity(), "Changes Saved", Toast.LENGTH_SHORT).show();
                }
                for (DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                    if (singleSnapshot.exists()){
                        Log.d(TAG, "onDataChange: FOUND A MATCH: " + singleSnapshot.getValue(User.class).getUsername());
                        Toast.makeText(getActivity(), "That username already exists.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setProfileWidgets(UserSettings userSettings){
//        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: "+ userSettings.toString());

        User user = userSettings.getUser();
        UserAccountSettings settings = userSettings.getSettings();

        UniversalImageLoader.setImage(settings.getProfile_photo(),mProfilePhoto, null, "");
        mUserSettings = userSettings;
        mUsername.setText(settings.getUsername());
        mDisplayname.setText(settings.getDisplay_name());
        mAddress.setText(user.getAddress());
        mDescription.setText(user.getDescription());
        mPhone.setText(String.valueOf(userSettings.getUser().getPhone()));
        mEmail.setText(userSettings.getUser().getEmail());

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
        userID = mAuth.getCurrentUser().getUid();

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

