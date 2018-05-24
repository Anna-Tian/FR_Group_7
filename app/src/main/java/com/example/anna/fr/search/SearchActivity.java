package com.example.anna.fr.search;



import android.content.Context;

import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;

import android.support.design.widget.BottomNavigationView;

import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.DividerItemDecoration;

import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;

import android.text.Editable;

import android.text.TextWatcher;

import android.util.Log;

import android.view.Menu;

import android.view.MenuItem;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


import com.example.anna.fr.R;

import com.example.anna.fr.utils.BottomNavigationViewHelper;

import com.example.anna.fr.utils.FilterActivity;
import com.example.anna.fr.utils.SearchAdapter;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;

import com.google.firebase.database.DatabaseError;

import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;



import java.util.ArrayList;



/**

 * Created by anna on 13/4/18.

 */



public class SearchActivity extends AppCompatActivity{

    private static final String TAG = "SearchActivity";

    private static final int ACTIVITY_NUM = 1;



    private Context mContext = SearchActivity.this;



    EditText search_edit_text;

    RecyclerView recyclerView;

    DatabaseReference databaseReference;

    FirebaseUser firebaseUser;

    ArrayList<String> nameList;

    ArrayList<String> addressList;

    ArrayList<String> profile_photoList;

    SearchAdapter searchAdapter;

//    private Button mSearchBtn;



//    private FirebaseMethods firebaseMethods;





//    private EditText mSearchParam;





//    private List<RestaurantIntro> mRestaurantList;

//

//    private RestaurantListAdapter mAdapter;







    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_search );

        Log.d( TAG, "onCreate: started." );



        search_edit_text = (EditText) findViewById( R.id.searchText );

        recyclerView = (RecyclerView) findViewById( R.id.result_list );



        databaseReference = FirebaseDatabase.getInstance().getReference();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();



        recyclerView.setHasFixedSize( true );

        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );

        recyclerView.addItemDecoration( new DividerItemDecoration( this, LinearLayoutManager.VERTICAL ) );



        nameList = new ArrayList<>();

        addressList = new ArrayList<>();

        profile_photoList = new ArrayList<>();









        search_edit_text.addTextChangedListener( new TextWatcher() {

            @Override

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {



            }



            @Override

            public void onTextChanged(CharSequence s, int start, int before, int count) {



            }



            @Override

            public void afterTextChanged(Editable s) {

                if (!s.toString().isEmpty()) {

                    setAdapter( s.toString() );



                } else {



                    nameList.clear();

                    addressList.clear();

                    profile_photoList.clear();

                    recyclerView.removeAllViews();

                }



            }

        } );


        setupBottomNavigationView();


        ImageView filterMenu = (ImageView) findViewById(R.id.filterMenu);

        filterMenu.setOnClickListener(new View.OnClickListener(){

            @Override

            public void onClick(View v) {

                Log.d(TAG, "onClick: navigating to filter selection.");

                Intent intent = new Intent(mContext, FilterActivity.class);

                startActivity(intent);



            }

        });

    }







    private void setAdapter(final String searchedString){



        databaseReference.child("restaurant_details").addListenerForSingleValueEvent( new ValueEventListener() {

            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {

                nameList.clear();

                addressList.clear();

                profile_photoList.clear();

                recyclerView.removeAllViews();

                int counter = 0;



                for (DataSnapshot snapshot: dataSnapshot.getChildren()){

                    String uid = snapshot.getKey();

                    String name = snapshot.child("name").getValue(String.class);

                    String address = snapshot.child("address").getValue(String.class);

                    String profile_photo = snapshot.child("profile_photo").getValue(String.class);



                    if(name.toLowerCase().contains( searchedString.toLowerCase() )){

                        nameList.add(name);

                        addressList.add(address);

                        profile_photoList.add(profile_photo);

                        counter++;

                    } else if (address.toLowerCase().contains( searchedString.toLowerCase() )){

                        nameList.add(name);

                        addressList.add(address);

                        profile_photoList.add(profile_photo);

                        counter++;

                    }



                    if(counter ==15)

                        break;



                }



                searchAdapter = new SearchAdapter( SearchActivity.this, nameList, addressList, profile_photoList );

                recyclerView.setAdapter( searchAdapter) ;

            }



            @Override

            public void onCancelled(DatabaseError databaseError) {



            }

        } );



    }



//        mSearchBtn = (Button) findViewById(R.id.searchBtn);

//

//        mResultList.setHasFixedSize(true);

//        mResultList.setLayoutManager(new LinearLayoutManager(this));



//        myRef.keepSynced(true);

//        mSearchParam = (EditText) findViewById(R.id.searchText);

//        mResultList = (RecyclerView) findViewById(R.id.result_list);





//        hideSoftKeyboard();

//        initTextListener();



//        setupViewPager();





    //setup the search button

//        mSearchBtn.setOnClickListener(new View.OnClickListener() {

//            @Override

//            public void onClick(View view) {

//

//                String searchText = mSearchField.getText().toString();

//

//                firebaseRestaurantSearch(searchText);

//            }

//        });







//    private void initTextListener(){

//        Log.d(TAG, "initTextListener: initializing");

//

//        mRestaurantList = new ArrayList<>();

//

//        mSearchParam.addTextChangedListener(new TextWatcher() {

//            @Override

//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

//

//            }

//

//            @Override

//            public void onTextChanged(CharSequence s, int start, int before, int count) {

//

//            }

//

//            @Override

//            public void afterTextChanged(Editable s) {

//                String text = mSearchParam.getText().toString().toLowerCase(Locale.getDefault());

//                searchForMatch(text);

//            }

//        });

//    }



//    private void searchForMatch (String keyword){

//        Log.d(TAG, "searchForMatch: searching for a match: " + keyword);

//        mRestaurantList.clear();

//

//        //update the restaurants' list

//        if (keyword.length() == 0){

//

//        }else {

//            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

//            Query query = reference.child(getString(R.string.dbname_restaurant_details))

//                    .orderByChild(getString(R.string.field_restaurant_name)).equalTo(keyword);

//            query.addListenerForSingleValueEvent(new ValueEventListener() {

//                @Override

//                public void onDataChange(DataSnapshot dataSnapshot) {

//                    for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()){

//                        Log.d(TAG, "onDataChange: found restaurant: ");

//

//                        mRestaurantList.add(singleSnapshot.getValue(RestaurantIntro.class));

//

//                        //update restaurant list view

////                        updateRestaurantList();

//                    }

//                }

//

//                @Override

//                public void onCancelled(DatabaseError databaseError) {

//

//                }

//            });

//        }

//    }



//    private void updateRestaurantList(){

//        Log.d(TAG, "updateRestaurantList: updating restaurant list");

//        mAdapter = new RestaurantListAdapter(SearchActivity.this, R.layout.snippet_center_restaurant_introduction, mRestaurantList);

//

//        mResultList.setAdapter(mAdapter);

//

//        mResultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

//            @Override

//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Log.d(TAG, "onItemClick: selected restaurant: " + mRestaurantList.get(position).toString());

//

//                //navigating to restaurant details

//            }

//        });

//    }



//    private void hideSoftKeyboard(){

//        if (getCurrentFocus() != null){

//            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

//            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

//        }

//    }









//

//    protected void firebaseRestaurantSearch(String searchText) {

//        Toast.makeText( SearchActivity.this, "Started Search", Toast.LENGTH_LONG).show();

//        Query firebaseSearchQuery = myRef.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff");

//

//        FirebaseRecyclerAdapter<RestaurantIntro, ResViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RestaurantIntro, ResViewHolder>

//                (RestaurantIntro.class, R.layout.snippet_center_restaurant_introduction, ResViewHolder.class, myRef) {

//            @Override

//            protected void populateViewHolder(ResViewHolder viewHolder, RestaurantIntro model, int position) {

//                viewHolder.setName(model.getName());

//                viewHolder.setProfile_photo(getApplicationContext(),model.getProfile_photo());

//                viewHolder.setAddress(model.getAddress());

//            }

//        };

//        mResultList.setAdapter(firebaseRecyclerAdapter);

//    }





//

//    public static class ResViewHolder extends RecyclerView.ViewHolder{

//        View mView;

//        public ResViewHolder(View itemView){

//            super(itemView);

//            mView = itemView;

//        }

//        public void setName(String name){

//            TextView res_name = (TextView) mView.findViewById(R.id.restaurantName);

//            res_name.setText(name);

//        }

//        public void setProfile_photo(Context ctx,String profile_photo){

//            ImageView res_image = (ImageView) mView.findViewById(R.id.restaurantImage);

//            Picasso.with(ctx).load(profile_photo).into(res_image);

//        }

//        public void setAddress(String address){

//            TextView res_address = (TextView) mView.findViewById(R.id.restaurantAddress);

//            res_address.setText(address);

//        }

//    }

    //Responsible for adding the tabs: Saved, History

//    private void setupViewPager () {

//        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());





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