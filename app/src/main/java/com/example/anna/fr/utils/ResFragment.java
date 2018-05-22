package com.example.anna.fr.utils;

import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.anna.fr.models.RestaurantDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ResFragment extends ListFragment {
    private RestaurantDetails restaurantDetails;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //this will hold our collection of res
        final List<RestaurantDetails> restaurantsList = new ArrayList<RestaurantDetails>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();
        databaseReference.child("restaurant_details").addValueEventListener(new ValueEventListener() {

            /**
             * this method will be invoked any time the data on the database changes.
             * additionally, it will be invoked as soon as we connect the listener, so that we can get an snapshot of the data on the database.
             * @param dataSnapshot
             */
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //get all of the children at this level
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                //shake hands with each of them
                for(DataSnapshot child: children){
                    RestaurantDetails restaurantDetails = child.getValue(RestaurantDetails.class);
                    restaurantsList.add(restaurantDetails);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //make an arrayAdapter to show our results
        ArrayAdapter<RestaurantDetails> restaurantListAdapter = new ArrayAdapter<RestaurantDetails>(getActivity(), android.support.v4.R.layout.notification_action,restaurantsList);
        //setListAdapter(plantAdapter);
        setListAdapter(restaurantListAdapter);
    }
}
