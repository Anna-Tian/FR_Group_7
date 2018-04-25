package com.example.anna.fr.Search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.anna.fr.R;
import com.example.anna.fr.Utils.BottomNavigationViewHelper;
import com.example.anna.fr.Utils.SectionsPagerAdapter;

/**
 * Created by anna on 13/4/18.
 */

public class SearchActivity extends AppCompatActivity{
    private static final String TAG = "SearchActivity";
    private static final int ACTIVITY_NUM = 1;

    private Context mContext = SearchActivity.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Log.d(TAG,"onCreate: started.");
        setupBottomNavigationView();
        setupViewPager();

        //setup the cancel button for navigating back to "SearchActivity"
        Button backArrow = (Button) findViewById(R.id.cancelSearch);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: naviagating back to 'SearchActivity'");
                finish();
            }
        });
    }

    //Responsible for adding the tabs: Saved, History
    private void setupViewPager () {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HistoryFragment()); //index 0
        adapter.addFragment(new SavedFragment()); //index 1
        ViewPager viewPager = (ViewPager) findViewById(R.id.search_container);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.searchTabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("History");
        tabLayout.getTabAt(1).setText("Saved");
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

}
