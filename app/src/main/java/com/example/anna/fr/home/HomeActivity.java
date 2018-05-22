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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anna.fr.R;
import com.example.anna.fr.models.RestaurantDetails;
import com.example.anna.fr.models.RestaurantIntro;
import com.example.anna.fr.search.SearchActivity;
import com.example.anna.fr.utils.BottomNavigationViewHelper;
import com.example.anna.fr.utils.FilterActivity;
import com.example.anna.fr.utils.RestaurantActivity;
import com.example.anna.fr.utils.SearchAdapter;
import com.example.anna.fr.utils.UniversalImageLoader;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private static final int ACTIVITY_NUM = 0;

    private Context mContext = HomeActivity.this;

    private RecyclerView mResList;
    private DatabaseReference mRef;


    RecyclerView recyclerView;
    FirebaseUser firebaseUser;
    ArrayList<String> nameList;
    ArrayList<String> addressList;
    ArrayList<String> profile_photoList;
    SearchAdapter searchAdapter;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(TAG, "onCreate: starting.");

        mRef = FirebaseDatabase.getInstance().getReference().child(mContext.getString(R.string.dbname_restaurant_details));
        mRef.keepSynced(true);

        mResList = (RecyclerView) findViewById(R.id.recyclerView);
        mResList.setHasFixedSize(true);
        mResList.setLayoutManager(new LinearLayoutManager(this));

        initImageLoader();
        setupToolbar();
        setupBottomNavigationView();

////shiyan
//        Query query =  mRef.orderByChild("rating");
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot ds : dataSnapshot.getChildren()) {
//                    String g = ds.child("g").getValue(String.class);
//                    Log.d("TAG", g);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {}
//        };
//        query.addListenerForSingleValueEvent(valueEventListener);
////.shiyan
//

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
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String uid = snapshot.getKey();
                        String name = snapshot.child("name").getValue(String.class);
                        String address = snapshot.child("address").getValue(String.class);
                        String profile_photo = snapshot.child("profile_photo").getValue(String.class);
                        float rating = snapshot.child("rating").getValue(float.class);

                        if (rating == counter) {
                            nameList.add(name);
                            addressList.add(address);
                            profile_photoList.add(profile_photo);

                        }

                    }
                }
                    searchAdapter = new SearchAdapter(HomeActivity.this, nameList, addressList, profile_photoList);
                    recyclerView.setAdapter(searchAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseRecyclerAdapter<RestaurantDetails, ResViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RestaurantDetails, ResViewHolder>
//                (RestaurantDetails.class, R.layout.snippet_center_restaurant_introduction, ResViewHolder.class, mRef) {
//            @Override
//            protected void populateViewHolder(ResViewHolder viewHolder, final RestaurantDetails model, int position) {
//                viewHolder.setName(model.getName());
//                viewHolder.setProfile_photo(getApplicationContext(),model.getProfile_photo());
//                viewHolder.setAddress(model.getAddress());
//                viewHolder.setRating((int) model.getRating());
//
//                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(mContext, model.getName(), Toast.LENGTH_SHORT).show();
//
//                        Intent intent = new Intent(mContext,RestaurantActivity.class);
//                        intent.putExtra("name",model.getName());
//                        intent.putExtra("profilePhoto",model.getProfile_photo());
//                        intent.putExtra("address",model.getAddress());
//                        intent.putExtra("phone",model.getPhone());
//                        intent.putExtra("rating",model.getRating());
//
//                        startActivity(intent);
//                    }
//                });
//            }
//        };
//        mResList.setAdapter(firebaseRecyclerAdapter);
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
//
//        public void setRating(int rating){
//                       TextView ratingBarT = (TextView) mView.findViewById(R.id.ratingBarText);
//                       ratingBarT.setText(rating+"");
//                      RatingBar ratingBar = (RatingBar) mView.findViewById(R.id.ratingBar);
//                      ratingBar.setRating(rating);
//                   }
//    }

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

}
