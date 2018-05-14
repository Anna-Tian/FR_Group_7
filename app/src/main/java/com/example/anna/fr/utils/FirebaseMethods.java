package com.example.anna.fr.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.anna.fr.R;
import com.example.anna.fr.models.RestaurantDetails;
import com.example.anna.fr.models.User;
import com.example.anna.fr.models.UserAccountSettings;
import com.example.anna.fr.models.UserSettings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class FirebaseMethods {
    private static final String TAG = "FirebaseMethods";


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private Context mContext;
    private String userID;
    private String resID;

    public FirebaseMethods (Context context){
        mContext = context;
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        if (mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
        }

    }

    public void updateUserSettings(String displayName, String address, String description, long phone ){
        Log.d(TAG, "updateUserSettings: updating user settings");

        if (displayName != null){
            myRef.child(mContext.getString(R.string.dbname_user_account_settings))
                    .child(userID)
                    .child(mContext.getString(R.string.field_display_name))
                    .setValue(displayName);
        }
        if (address != null){
            myRef.child(mContext.getString(R.string.dbname_users))
                    .child(userID)
                    .child(mContext.getString(R.string.field_address))
                    .setValue(address);
        }
        if (description != null){
            myRef.child(mContext.getString(R.string.dbname_users))
                    .child(userID)
                    .child(mContext.getString(R.string.field_description))
                    .setValue(description);
        }
        if (phone != 0){
            myRef.child(mContext.getString(R.string.dbname_users))
                    .child(userID)
                    .child(mContext.getString(R.string.field_phone))
                    .setValue(phone);
        }
    }

    public void updateUsername(String username){
        Log.d(TAG, "updateUsername: updating username to: " + username);

        myRef.child(mContext.getString(R.string.dbname_users))
                .child(userID)
                .child(mContext.getString(R.string.field_username))
                .setValue(username);

        myRef.child(mContext.getString(R.string.dbname_user_account_settings))
                .child(userID)
                .child(mContext.getString(R.string.field_username))
                .setValue(username);

    }

    public void updateEmail(String email){
        Log.d(TAG, "updateEmail: updating email to: " + email);

        myRef.child(mContext.getString(R.string.dbname_users))
                .child(userID)
                .child(mContext.getString(R.string.field_email))
                .setValue(email);
    }


    /**
     * register a new email and password to firebase authentication
     * @param email
     * @param password
     * @param username
     */
    public void registerNewEmail (final String email, final String username, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            userID = mAuth.getCurrentUser().getUid();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(mContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void addNewUser (String email, String username,
                            String description, String profile_photo, String address){
        User user = new User(
                userID,
                1,
                email,
                StringManipulation.condenseUsername(username),
                description,
                address);

        myRef.child(mContext.getString(R.string.dbname_users))
                .child(userID)
                .setValue(user);

        UserAccountSettings settings = new UserAccountSettings(
                username,
                profile_photo,
                StringManipulation.condenseUsername(username));

        myRef.child(mContext.getString(R.string.dbname_user_account_settings))
                .child(userID)
                .setValue(settings);
    }

    /**
     * retrieves the account settings for the user currently logged in
     * database: user_account_settings node
     * database: user node
     * @param dataSnapshot
     * @return
     */
    public UserSettings getUserSettings(DataSnapshot dataSnapshot){
        Log.d(TAG, "getUserAccountSettings: retrieving user account setting from firebase.");

        UserAccountSettings settings = new UserAccountSettings();
        User user = new User();

        for (DataSnapshot ds: dataSnapshot.getChildren()){
            //user_account_settings node
            Log.d(TAG, "getUserSettings: snapshot key: " + ds.getKey());
            if (ds.getKey().equals(mContext.getString(R.string.dbname_user_account_settings))){
                //Log.d(TAG, "getUserAccountSettings: datasnapshot: "+ ds);

                try {
                    settings.setDisplay_name(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getDisplay_name()
                    );
                    settings.setProfile_photo(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getProfile_photo()
                    );
                    settings.setUsername(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getUsername()
                    );
                    Log.d(TAG, "getUserAccountSettings: retrieved user_account_settings information: " + settings.toString());
                }catch (NullPointerException e){
                    Log.d(TAG, "getUserSettings: NullPointerException: " + e.getMessage());
                }
            }

            //user node
            Log.d(TAG, "getUserSettings: snapshot key: " + ds.getKey());
            if (ds.getKey().equals(mContext.getString(R.string.dbname_users))) {
                //Log.d(TAG, "getUserAccountSettings: datasnapshot: " + ds);

                try {
                    user.setUser_id(
                            ds.child(userID)
                                    .getValue(User.class)
                                    .getUser_id()
                    );
                    user.setPhone(
                            ds.child(userID)
                                    .getValue(User.class)
                                    .getPhone()
                    );
                    user.setEmail(
                            ds.child(userID)
                                    .getValue(User.class)
                                    .getEmail()
                    );
                    user.setUsername(
                            ds.child(userID)
                                    .getValue(User.class)
                                    .getUsername()
                    );
                    user.setDescription(
                            ds.child(userID)
                                    .getValue(User.class)
                                    .getDescription()
                    );
                    user.setAddress(
                            ds.child(userID)
                                    .getValue(User.class)
                                    .getAddress()
                    );
                    Log.d(TAG, "getUserAccountSettings: retrieved users information: " + user.toString());
                }catch (NullPointerException e){
                    Log.d(TAG, "getUserSettings: NullPointerException2: " + e.getMessage());
                }
            }
        }
        return new UserSettings(user,settings);
    }



    public void viewRestaurantDetails (){
        Log.d(TAG, "viewRestaurantDetails: view current restaurant details: " + myRef.child(mContext.getString(R.string.dbname_restaurant_details)).getKey());

        String RestaurantKey = myRef.child(mContext.getString(R.string.dbname_restaurant_details)).getKey();
        RestaurantDetails restaurantDetails = new RestaurantDetails();
        restaurantDetails.getName();
        restaurantDetails.getAddress();
        restaurantDetails.getProfile_photo();
        restaurantDetails.getRes_rating();
        restaurantDetails.getPhone();
        restaurantDetails.getRes_id();
        myRef.child(mContext.getString(R.string.dbname_restaurant_details)).child(RestaurantKey).getDatabase();

    }
}
