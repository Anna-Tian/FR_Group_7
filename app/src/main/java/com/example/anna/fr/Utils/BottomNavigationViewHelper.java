package com.example.anna.fr.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.example.anna.fr.Home.HomeActivity;
import com.example.anna.fr.Me.MeActivity;
import com.example.anna.fr.R;
import com.example.anna.fr.Random.RandomActivity;
import com.example.anna.fr.Search.SearchActivity;

/**
 * Created by anna on 13/4/18.
 */

public class BottomNavigationViewHelper {
    private static final String TAG = "BottomNavigationViewHel";

    public static void enableNavigation(final Context context, BottomNavigationView view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        Intent intent1 = new Intent(context, HomeActivity.class);//ACTIVITY_NUM = 0
                        context.startActivity(intent1);
                        break;
                    case R.id.navigation_search:
                        Intent intent2 = new Intent(context, SearchActivity.class);//ACTIVITY_NUM = 1
                        context.startActivity(intent2);
                        break;
                    case R.id.navigation_random:
                        Intent intent3 = new Intent(context, RandomActivity.class);//ACTIVITY_NUM = 2
                        context.startActivity(intent3);
                        break;
                    case R.id.navigation_me:
                        Intent intent4 = new Intent(context, MeActivity.class);//ACTIVITY_NUM = 3
                        context.startActivity(intent4);
                        break;
                }

                return false;
            }
        });
    }
}
