package com.example.anna.fr.Random;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.anna.fr.R;
import com.example.anna.fr.Utils.BottomNavigationViewHelper;
import com.example.anna.fr.Utils.RecycleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anna on 13/4/18.
 */

public class RandomActivity extends AppCompatActivity{
    private static final String TAG = "RandomActivity";
    private static final int ACTIVITY_NUM = 2;

    private Context mContext = RandomActivity.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);
        Log.d(TAG,"onCreate: started.");



        setupBottomNavigationView();
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


