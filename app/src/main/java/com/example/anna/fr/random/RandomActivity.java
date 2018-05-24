package com.example.anna.fr.random;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anna.fr.R;
import com.example.anna.fr.home.HomeActivity;
import com.example.anna.fr.utils.BottomNavigationViewHelper;
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

public class RandomActivity extends AppCompatActivity{
    private static final String TAG = "RandomActivity";
    private static final int ACTIVITY_NUM = 2;

    private Context mContext = RandomActivity.this;

//    private TextView textRating;
//    private  RatingBar ratingBar;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);
        Log.d(TAG,"onCreate: started.");



        setupBottomNavigationView();


        mRef = FirebaseDatabase.getInstance().getReference().child(mContext.getString(R.string.dbname_restaurant_details));
        mRef.keepSynced(true);

        mResList = (RecyclerView) findViewById(R.id.recyclerView1);
        mResList.setHasFixedSize(true);
        mResList.setLayoutManager(new LinearLayoutManager(this));

        recyclerView = (RecyclerView) findViewById( R.id.recyclerView1 );

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        recyclerView.addItemDecoration( new DividerItemDecoration( this, LinearLayoutManager.VERTICAL ) );

        nameList = new ArrayList<>();
        addressList = new ArrayList<>();
        profile_photoList = new ArrayList<>();

        setAdapter();
//
//        ratingBar = (RatingBar) findViewById(R.id.ratingBar1);
//        textRating = (TextView) findViewById(R.id.ratingText1);
//
//        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                textRating.setText("Rating:" + rating);
//            }
//        });
    }

    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }



    private void setAdapter() {
        Toast.makeText(mContext, "Random restaurant!~~~", Toast.LENGTH_SHORT).show();

        databaseReference.child("restaurant_details").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nameList.clear();
                addressList.clear();
                profile_photoList.clear();
                recyclerView.removeAllViews();

for(int counter=10;counter>0;counter--) {

    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
        String name = snapshot.child("name").getValue(String.class);
        String address = snapshot.child("address").getValue(String.class);
        String profile_photo = snapshot.child("profile_photo").getValue(String.class);
//        float rating = snapshot.child("rating").getValue(float.class);
        int randomIndex = (int)Math.floor(Math.random() * 10);

        if (counter == randomIndex) {
            if(!nameList.contains(name)) {
                nameList.add(name);
                addressList.add(address);
                profile_photoList.add(profile_photo);
            }

        }

    }
}

                searchAdapter = new SearchAdapter(RandomActivity.this, nameList, addressList, profile_photoList);
                recyclerView.setAdapter(searchAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}


