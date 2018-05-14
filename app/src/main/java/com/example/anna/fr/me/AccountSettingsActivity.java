package com.example.anna.fr.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.anna.fr.R;
import com.example.anna.fr.utils.SectionsStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by anna on 16/4/18.
 */

public class AccountSettingsActivity extends AppCompatActivity {

    private static final String TAG = "AccountSettingsActivity";

    private Context mContext = AccountSettingsActivity.this;

    private SectionsStatePagerAdapter pagerAdapter;
    private ViewPager mViewPager;
    private RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_accountsettings);
        Log.d(TAG, "onCreate: started");

        mViewPager = (ViewPager) findViewById(R.id.view_container);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.relLayout1);

        setupSettingsList();
        setupFragments();
        getIncomingIntent();

        //setup the backarraw for navigating back to "ProfileActivity"
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: naviagating back to 'ProfileActivity'");
                finish();
            }
        });
    }

    private void getIncomingIntent(){
        Intent intent = getIntent();

        if (intent.hasExtra(getString(R.string.calling_activity))){
            Log.d(TAG, "getIncomingIntent: received incoming intent from " + getString(R.string.me_activity));
            setViewPager(pagerAdapter.getFragmentNumber(getString(R.string.edit_profile_fragment)));
        }
    }

    private void setupFragments(){
        pagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new EditProfileFragment(), getString(R.string.edit_profile_fragment)); //fragment 0
        pagerAdapter.addFragment(new PasswordFragment(), getString(R.string.password_fragment)); //fragment 1
        pagerAdapter.addFragment(new SignOutFragment(), getString(R.string.sign_out_fragment)); //fragment2
    }

    private void setViewPager(int fragmentNumber){
        Log.d(TAG, "setViewPager: navigating to fragment #:" +fragmentNumber);
        mRelativeLayout.setVisibility(View.GONE);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(fragmentNumber);}

    private void setupSettingsList() {
        Log.d(TAG, "setupSettingsList: initializing 'Account Settings' list.");
        ListView listView = (ListView) findViewById(R.id.lvAccountSettings);

        ArrayList<String> options = new ArrayList<>();
        options.add(getString(R.string.edit_profile_fragment));
        options.add(getString(R.string.password_fragment));
        options.add(getString(R.string.sign_out_fragment));

        ArrayAdapter adapter = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, options);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: navigating to fragment#: " +position);
                setViewPager(position);
            }
        });
    }
}