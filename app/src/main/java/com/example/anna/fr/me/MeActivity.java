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

public class MeActivity extends AppCompatActivity implements IMeActivity{
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
        doFragmentTransaction(fragment, getString(R.string.me_fragment),false);

    }
    private void doFragmentTransaction(Fragment fragment, String tag, boolean addToBackStack){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.container, fragment, tag);
        if(addToBackStack){
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }

    @Override
    public void inflateFragment(String fragmentTag) {
        if (fragmentTag.equals(getString(R.string.favourite_fragment))){
            FavouriteFragment fragment = new FavouriteFragment();
            doFragmentTransaction(fragment, fragmentTag,true);
        }
        else if (fragmentTag.equals(getString(R.string.history_fragment))){
            HistoryFragment fragment = new HistoryFragment();
            doFragmentTransaction(fragment, fragmentTag,true);
        }
        else if (fragmentTag.equals(getString(R.string.review_fragment))){
            ReviewFragment fragment = new ReviewFragment();
            doFragmentTransaction(fragment, fragmentTag,true);
        }
        else if (fragmentTag.equals(getString(R.string.help_fragment))){
            HelpFragment fragment = new HelpFragment();
            doFragmentTransaction(fragment, fragmentTag,true);

        }
    }
}
