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
import com.example.anna.fr.utils.RestaurantListAdapter;
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

    private EditText mSearchParam;
    private ListView mResultList;
    private Button mSearchBtn;

    private List<RestaurantIntro> mRestaurantList;
    private RestaurantListAdapter mAdapter;

    private FirebaseMethods firebaseMethods;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Log.d(TAG,"onCreate: started.");

        mSearchParam = (EditText) findViewById(R.id.searchText);
        mResultList = (ListView) findViewById(R.id.result_list);
//        mResultList.setHasFixedSize(true);
//        mResultList.setLayoutManager(new LinearLayoutManager(this));
        mSearchBtn = (Button) findViewById(R.id.searchBtn);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference("restaurant_intro");

        setupBottomNavigationView();
//        setupViewPager();
        hideSoftKeyboard();
        initTextListener();

        //setup the search button
//        mSearchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                firebaseRestaurantSearch();
//            }
//        });
    }

//    private void firebaseRestaurantSearch() {
//        Log.d(TAG, "firebaseRestaurantSearch: started");
//        FirebaseRecyclerAdapter<RestaurantIntro, RestaurantViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RestaurantIntro, RestaurantViewHolder>(
//                RestaurantIntro.class,
//                R.layout.snippet_center_restaurant_introduction,
//                RestaurantViewHolder.class,
//                myRef
//        ) {
//            @Override
//            protected void populateViewHolder(RestaurantViewHolder viewHolder, RestaurantIntro model, int position) {
//                viewHolder.setDetails(model.getName(), model.getAddress(), model.getProfile_photo());
//            }
//        };
//
//        mResultList.setAdapter(firebaseRecyclerAdapter);
//    }

//    public static class RestaurantViewHolder extends RecyclerView.ViewHolder{
//
//        View mView;
//
//        public RestaurantViewHolder(View itemView) {
//            super(itemView);
//        }
//
//        public void setDetails(String restaurantName, String restaurantAddress, String restaurantImage){
//            TextView restaurant_name = (TextView) mView.findViewById(R.id.restaurantName);
//            TextView restaurant_address = (TextView) mView.findViewById(R.id.restaurantAddress);
//            ImageView restaurant_image = (ImageView) mView.findViewById(R.id.restaurantImage);
//
//            restaurant_name.setText(restaurantName);
//            restaurant_address.setText(restaurantAddress);
//
//            RestaurantDetails restaurantDetails = new RestaurantDetails();
//            UniversalImageLoader.setImage(restaurantImage,restaurant_image, null, "");
//
//        }
//    }

    private void initTextListener(){
        Log.d(TAG, "initTextListener: initializing");

        mRestaurantList = new ArrayList<>();

        mSearchParam.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = mSearchParam.getText().toString().toLowerCase(Locale.getDefault());
                searchForMatch(text);
            }
        });
    }

    private void searchForMatch (String keyword){
        Log.d(TAG, "searchForMatch: searching for a match: " + keyword);
        mRestaurantList.clear();

        //update the restaurants' list
        if (keyword.length() == 0){

        }else {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            Query query = reference.child(getString(R.string.dbname_restaurant_details))
                    .orderByChild(getString(R.string.field_restaurant_name)).equalTo(keyword);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                        Log.d(TAG, "onDataChange: found restaurant: ");

                        mRestaurantList.add(singleSnapshot.getValue(RestaurantIntro.class));

                        //update restaurant list view
                        updateRestaurantList();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void updateRestaurantList(){
        Log.d(TAG, "updateRestaurantList: updating restaurant list");
        mAdapter = new RestaurantListAdapter(SearchActivity.this, R.layout.snippet_center_restaurant_introduction, mRestaurantList);

        mResultList.setAdapter(mAdapter);

        mResultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: selected restaurant: " + mRestaurantList.get(position).toString());

                //navigating to restaurant details
            }
        });
    }

    private void hideSoftKeyboard(){
        if (getCurrentFocus() != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
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
