package com.example.anna.fr.random;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.anna.fr.R;
import com.example.anna.fr.utils.BottomNavigationViewHelper;

/**
 * Created by anna on 13/4/18.
 */

public class RandomActivity extends AppCompatActivity{
    private static final String TAG = "RandomActivity";
    private static final int ACTIVITY_NUM = 2;

    private Context mContext = RandomActivity.this;

    private TextView textRating;
    private  RatingBar ratingBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);
        Log.d(TAG,"onCreate: started.");



        setupBottomNavigationView();




        ratingBar = (RatingBar) findViewById(R.id.ratingBar1);
        textRating = (TextView) findViewById(R.id.ratingText1);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                textRating.setText("Rating:" + rating);
            }
        });
    }

    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }
}


