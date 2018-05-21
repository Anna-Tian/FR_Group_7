package com.example.anna.fr.home;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anna.fr.R;
import com.example.anna.fr.models.RestaurantDetails;
import com.example.anna.fr.models.RestaurantIntro;
import com.example.anna.fr.utils.BottomNavigationViewHelper;
import com.example.anna.fr.utils.FilterActivity;
import com.example.anna.fr.utils.RestaurantActivity;
import com.example.anna.fr.utils.UniversalImageLoader;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private static final int ACTIVITY_NUM = 0;

    private Context mContext = HomeActivity.this;

    private RecyclerView mResList;
    private DatabaseReference mRef;


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


//        Collections.sort(mRestaurantIntro, RestaurantIntro.ComparatorBy);
//        Collections.sort(mRestaurantDetails, RestaurantDetails.ComparatorBy);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<RestaurantDetails, ResViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RestaurantDetails, ResViewHolder>
                (RestaurantDetails.class, R.layout.snippet_center_restaurant_introduction, ResViewHolder.class, mRef) {
            @Override
            protected void populateViewHolder(ResViewHolder viewHolder, final RestaurantDetails model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setProfile_photo(getApplicationContext(),model.getProfile_photo());
                viewHolder.setAddress(model.getAddress());
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext, model.getName(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(mContext,RestaurantActivity.class);
                        intent.putExtra("name",model.getName());
                        intent.putExtra("profilePhoto",model.getProfile_photo());
                        intent.putExtra("address",model.getAddress());
                        intent.putExtra("phone",model.getPhone());

                        startActivity(intent);
                    }
                });
            }
        };
        mResList.setAdapter(firebaseRecyclerAdapter);
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
