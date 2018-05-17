package com.example.anna.fr.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.anna.fr.R;
import com.example.anna.fr.models.RestaurantDetails;
import com.example.anna.fr.models.RestaurantIntro;
import com.example.anna.fr.utils.BottomNavigationViewHelper;
import com.example.anna.fr.utils.FirebaseMethods;
import com.example.anna.fr.utils.SectionsPagerAdapter;
import com.example.anna.fr.utils.UniversalImageLoader;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by anna on 13/4/18.
 */

public class SearchActivity extends AppCompatActivity{
    private static final String TAG = "SearchActivity";
    private static final int ACTIVITY_NUM = 1;

    private Context mContext = SearchActivity.this;

    private EditText mSearchField;
    private RecyclerView mResultList;
    private Button mSearchBtn;

    private FirebaseMethods firebaseMethods;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Log.d(TAG,"onCreate: started.");

        mResultList = (RecyclerView) findViewById(R.id.result_list);
        mSearchField = (EditText) findViewById(R.id.searchText);
        mSearchBtn = (Button) findViewById(R.id.searchBtn);

        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));


        myRef = FirebaseDatabase.getInstance().getReference().child(mContext.getString(R.string.dbname_restaurant_intro));
        myRef.keepSynced(true);

        setupBottomNavigationView();
//        setupViewPager();


        //setup the search button
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = mSearchField.getText().toString();

                firebaseRestaurantSearch(searchText);
            }
        });
    }

    protected void firebaseRestaurantSearch(String searchText) {
        Query firebaseSearchQuery = myRef.orderByChild
                (mContext.getString(R.string.field_restaurant_name)).startAt(searchText).endAt(searchText + "\uf8ff");

        FirebaseRecyclerAdapter<RestaurantIntro, ResViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RestaurantIntro, ResViewHolder>
                (RestaurantIntro.class, R.layout.snippet_center_restaurant_introduction, ResViewHolder.class, myRef) {
            @Override
            protected void populateViewHolder(ResViewHolder viewHolder, RestaurantIntro model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setProfile_photo(getApplicationContext(),model.getProfile_photo());
                viewHolder.setAddress(model.getAddress());
            }
        };
        mResultList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class ResViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public ResViewHolder(View itemView){
            super(itemView);
            mView = itemView;
        }
        public void setName(String name){
            TextView res_name = (TextView) mView.findViewById(R.id.restaurantName);
            res_name.setText(name);
        }
        public void setProfile_photo(Context ctx,String profile_photo){
            ImageView res_image = (ImageView) mView.findViewById(R.id.restaurantImage);
            Picasso.with(ctx).load(profile_photo).into(res_image);
        }
        public void setAddress(String address){
            TextView res_address = (TextView) mView.findViewById(R.id.restaurantAddress);
            res_address.setText(address);
        }
    }
    //Responsible for adding the tabs: Saved, History
//    private void setupViewPager () {
//        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
//        adapter.addFragment(new HistoryFragment()); //index 0
//        adapter.addFragment(new SavedFragment()); //index 1
//        ViewPager viewPager = (ViewPager) findViewById(R.id.search_container);
//        viewPager.setAdapter(adapter);
//
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.searchTabs);
//        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.getTabAt(0).setText("History");
//        tabLayout.getTabAt(1).setText("Saved");
//    }

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
