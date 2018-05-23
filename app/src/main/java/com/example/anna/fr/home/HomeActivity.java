package com.example.anna.fr.home;



import android.content.Context;

import android.content.Intent;

import android.support.design.widget.BottomNavigationView;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.support.v7.widget.CardView;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;

import android.util.Log;

import android.view.Menu;

import android.view.MenuItem;

import android.view.View;

import android.widget.ImageView;

import android.widget.RatingBar;

import android.widget.TextView;

import android.widget.Toast;



import com.example.anna.fr.R;

import com.example.anna.fr.models.RestaurantDetails;

import com.example.anna.fr.models.RestaurantIntro;

import com.example.anna.fr.utils.BottomNavigationViewHelper;

import com.example.anna.fr.utils.FilterActivity;

import com.example.anna.fr.utils.RestaurantActivity;

import com.example.anna.fr.utils.SearchAdapter;
import com.example.anna.fr.utils.UniversalImageLoader;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

import com.google.firebase.database.DatabaseError;

import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import com.nostra13.universalimageloader.core.ImageLoader;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {



    private static final String TAG = "HomeActivity";

    private static final int ACTIVITY_NUM = 0;



    private Context mContext = HomeActivity.this;



    private RecyclerView mResList;

    private DatabaseReference mRef;

    private DatabaseReference myRef;



    DatabaseReference databaseReference;

    DatabaseReference databaseReference2;

    RecyclerView recyclerView;
    FirebaseUser firebaseUser;
    ArrayList<String> nameList;
    ArrayList<String> addressList;
    ArrayList<String> profile_photoList;
    SearchAdapter searchAdapter;






    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        Log.d(TAG, "onCreate: starting.");



        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference2 = FirebaseDatabase.getInstance().getReference();



        myRef = FirebaseDatabase.getInstance().getReference().child(mContext.getString(R.string.dbname_restaurant_details));

        myRef.keepSynced(true);




        mResList = (RecyclerView) findViewById(R.id.recyclerView);

        mResList.setHasFixedSize(true);

        mResList.setLayoutManager(new LinearLayoutManager(this));



        initImageLoader();

        setupToolbar();

        setupBottomNavigationView();



        recyclerView = (RecyclerView) findViewById( R.id.recyclerView );

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        recyclerView.addItemDecoration( new DividerItemDecoration( this, LinearLayoutManager.VERTICAL ) );

        nameList = new ArrayList<>();
        addressList = new ArrayList<>();
        profile_photoList = new ArrayList<>();

        setAdapter();



    }




        private void setAdapter() {

            databaseReference.child("restaurant_details").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    nameList.clear();
                    addressList.clear();
                    profile_photoList.clear();
                    recyclerView.removeAllViews();


                    for(int counter=5 ;counter>0;counter-- ){
                        for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String uid = snapshot.getKey();
                            String name = snapshot.child("name").getValue(String.class);
                            String address = snapshot.child("address").getValue(String.class);
                            String profile_photo = snapshot.child("profile_photo").getValue(String.class);
                            float rating = snapshot.child("rating").getValue(float.class);

//                            databaseReference2.child("restaurant_comments");
//                            databaseReference2.child(snapshot.child("res_id").getValue(String.class));
//                            databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
//
//                                @Override
//
//                                public void onDataChange(DataSnapshot dataSnapshot) {
//
//                                    int sum = 0;
//
//                                    int count = 0;
//
//                                    for (DataSnapshot ds2 : dataSnapshot.getChildren()) {
//
//                                        sum += ds2.child("rating").getValue(float.class).intValue();
//
//                                        count++;
//
//                                    }
//
//                                    Log.d(TAG, "onDataChange: ++++" + sum + " / " + count + " = " + sum / count);
//
//                                    databaseReference.child("restaurant_details").child(snapshot.child("res_id").getValue(String.class)).child("rating").setValue(sum / count);
//


                            if (rating == counter) {
                                nameList.add(name);
                                addressList.add(address);
                                profile_photoList.add(profile_photo);

                            }

                        }
                    }
                    searchAdapter = new SearchAdapter(com.example.anna.fr.home.HomeActivity.this, nameList, addressList, profile_photoList);
                    recyclerView.setAdapter(searchAdapter);

                }


                           @Override
                           public void onCancelled(DatabaseError databaseError) {

                                                       }
                       });
                                    Log.d(TAG, "onDataChange: rating bar ++++++");
                                       }






        private void initImageLoader(){
            UniversalImageLoader universalImageLoader = new UniversalImageLoader(mContext);
            ImageLoader.getInstance().init(universalImageLoader.getConfig());
        }
        private void setupToolbar(){
            Toolbar toolbar = (Toolbar) findViewById(R.id.homeBar);
            setSupportActionBar(toolbar);

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


        // BottomNavigationView setup
        private void setupBottomNavigationView() {
            Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
            BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavViewBar);
            BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationView);
            Menu menu = bottomNavigationView.getMenu();
            MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
            menuItem.setChecked(true);

        }



//
//
//    @Override
//
//    protected void onStart() {
//
//        super.onStart();
//
//        FirebaseRecyclerAdapter<RestaurantDetails, ResViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RestaurantDetails, ResViewHolder>
//
//                (RestaurantDetails.class, R.layout.snippet_center_restaurant_introduction, ResViewHolder.class, mRef) {
//
//            @Override
//
//            protected void populateViewHolder(ResViewHolder viewHolder, final RestaurantDetails model, int position) {
//
//                viewHolder.setName(model.getName());
//
//                viewHolder.setProfile_photo(getApplicationContext(),model.getProfile_photo());
//
//                viewHolder.setAddress(model.getAddress());
//
//                databaseReference.child("restaurant_details").addListenerForSingleValueEvent(new ValueEventListener() {
//
//                    @Override
//
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//
//                        for (final DataSnapshot ds: dataSnapshot.getChildren()){
//
//                            String name = ds.child("name").getValue(String.class);
//
//                            String address = ds.child("address").getValue(String.class);
//
//                            if(areSame(model.getName(),name)&&areSame(model.getAddress(),address)){
//
//                                databaseReference2.child("restaurant_comments").child(ds.child("res_id").getValue(String.class)).addListenerForSingleValueEvent(new ValueEventListener() {
//
//                                    @Override
//
//                                    public void onDataChange(DataSnapshot dataSnapshot) {
//
//                                        int sum = 0;
//
//                                        int count = 0;
//
//                                        for (DataSnapshot ds2 : dataSnapshot.getChildren()){
//
//                                            sum += ds2.child("rating").getValue(float.class).intValue();
//
//                                            count++;
//
//                                        }
//
//                                        Log.d(TAG, "onDataChange: ++++"+sum+" / "+count+" = "+ sum/count);
//
//                                        databaseReference.child("restaurant_details").child(ds.child("res_id").getValue(String.class)).child("rating").setValue(sum/count);
//
//                                    }
//
//
//
//                                    @Override
//
//                                    public void onCancelled(DatabaseError databaseError) {
//
//
//
//                                    }
//
//                                });
//
//                                Log.d(TAG, "onDataChange: rating bar ++++++");
//
//                            }
//
//                        }
//
//                    }
//
//
//
//                    @Override
//
//                    public void onCancelled(DatabaseError databaseError) {
//
//
//
//                    }
//
//                });
//
//


    private boolean areSame(String string1, String string2){

        return string1.equals(string2);

    }



}