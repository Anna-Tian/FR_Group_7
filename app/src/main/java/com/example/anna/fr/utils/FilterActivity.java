package com.example.anna.fr.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.anna.fr.R;
import com.example.anna.fr.search.SearchActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by anna on 24/4/18.
 */

public class FilterActivity extends AppCompatActivity {
    private static final String TAG = "FilterActivity";

    TextView mItemSelected;
    TextView mItemSelected2;
    TextView mItemSelected3;
    String[] listItems1;
    String[] listItems2;
    String[] listItems3;
    ArrayList<String> SelectedString1;
    ArrayList<String> SelectedString2;
    ArrayList<String> SelectedString3;
    boolean[] checkedItems1;
    boolean[] checkedItems2;
    boolean[] checkedItems3;
    ArrayList<Integer> mUserItems1 = new ArrayList<>();
    ArrayList<Integer> mUserItems2 = new ArrayList<>();
    ArrayList<Integer> mUserItems3 = new ArrayList<>();
    Button mFilter1;
    Button mFilter2;
    Button mFilter3;
    AlertDialog mDialog;


    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    ArrayList<String> nameList;
    ArrayList<String> addressList;
    ArrayList<String> profile_photoList;
    SearchAdapter searchAdapter;
    private Context mContext = FilterActivity.this;

    ArrayList<String> selectedItemsforToast = new ArrayList<>();



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }



    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Log.d(TAG, "onCreate: started");


        recyclerView = (RecyclerView) findViewById( R.id.filter_done_recycler );

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        recyclerView.addItemDecoration( new DividerItemDecoration( this, LinearLayoutManager.VERTICAL ) );

        nameList = new ArrayList<>();
        addressList = new ArrayList<>();
        profile_photoList = new ArrayList<>();


//
        //-----Button: Country----------------------------------------------------------------------

        mFilter1 = (Button) findViewById(R.id.filter_country_button);
        mItemSelected = (TextView) findViewById(R.id.filter_itemSelected);
        listItems1 = getResources().getStringArray(R.array.Country);
        checkedItems1 = new boolean[listItems1.length];

        mFilter1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(FilterActivity.this);
                mBuilder.setTitle(R.string.dialog_title);
                mBuilder.setMultiChoiceItems(listItems1, checkedItems1, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if(isChecked){
                            if(! mUserItems1.contains(position)){
                                mUserItems1.add(position);
                            }
                        }else if(mUserItems1.contains(position)){
                            mUserItems1.remove(position);
                        }
                    }
                });
                mBuilder.setCancelable(true);
                mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";
                        for(int i = 0; i < mUserItems1.size(); i++){
                            item = item + listItems1[mUserItems1.get(i)];
//                            SelectedString1.add(item);
                            if (i != mUserItems1.size() -1){
                                item = item + ",";
                            }
                        }
                        mItemSelected.setText(item);
//                        selectedItemsforToast.add(item);
//                        SelectedString1.add(item);
                    }
                });
                mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < checkedItems1.length; i++){
                            checkedItems1[i] = false;
                            mUserItems1.clear();
                            mItemSelected.setText("");

                        }
                    }
                });
                mDialog = mBuilder.create();
               mBuilder.show();
            }
        });

        /**
         * ----Button: Cuisine-----------------------------------------------------------------------------
         */
        mFilter2 = (Button) findViewById(R.id.filer_cuisine_button);
        mItemSelected2 = (TextView) findViewById(R.id.filter_itemSelected_2);
        listItems2 = getResources().getStringArray(R.array.Cuisine);
        checkedItems2 = new boolean[listItems2.length];

        mFilter2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(FilterActivity.this);
                mBuilder.setTitle(R.string.dialog_title);
                mBuilder.setMultiChoiceItems(listItems2, checkedItems2, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if(isChecked){
                            if(! mUserItems2.contains(position)){
                                mUserItems2.add(position);
                            }
                        }else if(mUserItems2.contains(position)){
                            mUserItems2.remove(position);
                        }
                    }
                });
                mBuilder.setCancelable(true);
                mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";
                        for(int i = 0; i < mUserItems2.size(); i++){
                            item = item + listItems2[mUserItems2.get(i)];
                            if (i != mUserItems2.size() -1){
                                item = item + ",";
                            }
                        }
                        mItemSelected2.setText(item);
//                        selectedItemsforToast.add(item);
                    }
                });
                mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < checkedItems2.length; i++){
                            checkedItems2[i] = false;
                            mUserItems2.clear();
                            mItemSelected2.setText("");
                        }
                    }
                });
                 mDialog = mBuilder.create();
                mBuilder.show();
            }
        });
        //---Button: More-----------------------------------------------------------------------------------

        mFilter3 = (Button) findViewById(R.id.filter_more_button);
        mItemSelected3 = (TextView) findViewById(R.id.filter_itemSelected_3);
        listItems3 = getResources().getStringArray(R.array.More);
        checkedItems3 = new boolean[listItems3.length];

        mFilter3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(FilterActivity.this);
                mBuilder.setTitle(R.string.dialog_title);
                mBuilder.setMultiChoiceItems(listItems3, checkedItems3, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if(isChecked){
                            if(! mUserItems3.contains(position)){
                                mUserItems3.add(position);
                            }
                        }else if(mUserItems3.contains(position)){
                            mUserItems3.remove(position);
                        }
                    }
                });
                mBuilder.setCancelable(true);
                mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";
                        for(int i = 0; i < mUserItems3.size(); i++){
                            item = item + listItems3[mUserItems3.get(i)];
                            if (i != mUserItems3.size() -1){
                                item = item + ",";
                            }
                        }
                        mItemSelected3.setText(item);
//                        selectedItemsforToast.add(item);
                    }
                });
                mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < checkedItems3.length; i++){
                            checkedItems3[i] = false;
                            mUserItems3.clear();
                            mItemSelected3.setText("");
                        }
                    }
                });
                 mDialog = mBuilder.create();
                mBuilder.show();
            }
        });


//        getIncomingIntent();

        setUpToolBar();


    }
    //-----------------------------------------------------------------------------------------------



    private void clearList(){
        if ((!mItemSelected.getText().toString().isEmpty())|| (!mItemSelected2.getText().toString().isEmpty()) ||(!mItemSelected3.getText().toString().isEmpty())) {
            setAdapter( mItemSelected.getText().toString(),mItemSelected2.getText().toString(),mItemSelected3.getText().toString());

        } else {
            nameList.clear();
            addressList.clear();
            profile_photoList.clear();
            recyclerView.removeAllViews();
        }
    }

    private void setAdapter(final String searchedString, final String searchedString2,final String searchedString3){

        databaseReference.child("restaurant_details").addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nameList.clear();
                addressList.clear();
                profile_photoList.clear();
                recyclerView.removeAllViews();

                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String uid = snapshot.getKey();
                    String name = snapshot.child("name").getValue(String.class);
                    String rcategory = snapshot.child("category").getValue(String.class);
                    String rcuisine = snapshot.child("cuisine").getValue(String.class);
                    String address = snapshot.child("address").getValue(String.class);
                    String profile_photo = snapshot.child("profile_photo").getValue(String.class);
                    String outdoorSeat = snapshot.child("outdoor_seat").getValue(String.class);
                    String card = snapshot.child("card").getValue(String.class);
                    String byo = snapshot.child("byo").getValue(String.class);
                    String wifi = snapshot.child("wifi").getValue(String.class);
                    if(!searchedString.isEmpty() && searchedString2.isEmpty()&& searchedString3.isEmpty()&&searchedString.toLowerCase().contains(rcategory.toLowerCase())){
                        //only category
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }else if(searchedString.isEmpty() && !searchedString2.isEmpty()&& searchedString3.isEmpty()&&searchedString2.toLowerCase().contains(rcuisine.toLowerCase())){
                        //only cuisine
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }else if(!searchedString.isEmpty() && !searchedString2.isEmpty()&& searchedString3.isEmpty()&&searchedString.toLowerCase().contains(rcategory.toLowerCase())&&(searchedString2.toLowerCase().contains(rcuisine.toLowerCase()))){
                        //category and cuisine
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);






                    }else if(searchedString.isEmpty() && searchedString2.isEmpty()&& !searchedString3.isEmpty()&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.equals("outdoor seat")&&outdoorSeat.equals("yes")
                            ){
                        //only more, and there is only outdoor seat in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }
                    else if(searchedString.isEmpty() && searchedString2.isEmpty()&& !searchedString3.isEmpty()&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.equals("card")&&card.equals("yes")
                            ){
                        //only more, and there is only card in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }else if(searchedString.isEmpty() && searchedString2.isEmpty()&& !searchedString3.isEmpty()&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().equals("byo")&&byo.equals("yes")
                            ){
                        //only more, and there is only byo in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }else if(searchedString.isEmpty() && searchedString2.isEmpty()&& !searchedString3.isEmpty()&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().equals("wifi")&&wifi.equals("yes")
                            ){
                        //only more, and there is only wifi in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);



                    }else if(searchedString.isEmpty() && searchedString2.isEmpty()&& !searchedString3.isEmpty()&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("outdoor seat")&&
                            searchedString3.toLowerCase().contains("card")&&
                            outdoorSeat.equals("yes")&&card.equals("yes")
                            ){
                        //only more, and there are two options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }
                    else if(searchedString.isEmpty() && searchedString2.isEmpty()&& !searchedString3.isEmpty()&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("outdoor seat")&&
                            searchedString3.toLowerCase().contains("byo")&&
                            outdoorSeat.equals("yes")&&byo.equals("yes")
                            ){
                        //only more, and there are two options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }else if(searchedString.isEmpty() && searchedString2.isEmpty()&& !searchedString3.isEmpty()&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("outdoor seat")&&
                            searchedString3.toLowerCase().contains("wifi")&&
                            outdoorSeat.equals("yes")&&wifi.equals("yes")
                            ){
                        //only more, and there are two options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }
                    else if(searchedString.isEmpty() && searchedString2.isEmpty()&& !searchedString3.isEmpty()&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("card")&&
                            searchedString3.toLowerCase().contains("byo")&&
                            byo.equals("yes")&&card.equals("yes")
                            ){
                        //only more, and there are two options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }else if(searchedString.isEmpty() && searchedString2.isEmpty()&& !searchedString3.isEmpty()&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("card")&&
                            searchedString3.toLowerCase().contains("wifi")&&
                            wifi.equals("yes")&&card.equals("yes")
                            ){
                        //only more, and there are two options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }else if(searchedString.isEmpty() && searchedString2.isEmpty()&& !searchedString3.isEmpty()&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("byo")&&
                            searchedString3.toLowerCase().contains("wifi")&&
                            byo.equals("yes")&&wifi.equals("yes")
                            ){
                        //only more, and there are two options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);


                    }else if(searchedString.isEmpty() && searchedString2.isEmpty()&& !searchedString3.isEmpty()&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("outdoor seat")&&
                            searchedString3.toLowerCase().contains("card")&&
                            searchedString3.toLowerCase().contains("byo")&&
                            outdoorSeat.equals("yes")&&card.equals("yes")&&byo.equals("yes")
                            ){
                        //only more, and there are three options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }else if(searchedString.isEmpty() && searchedString2.isEmpty()&& !searchedString3.isEmpty()&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("outdoor seat")&&
                            searchedString3.toLowerCase().contains("card")&&
                            searchedString3.toLowerCase().contains("wifi")&&
                            outdoorSeat.equals("yes")&&card.equals("yes")&&wifi.equals("yes")
                            ){
                        //only more, and there are three options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }else if(searchedString.isEmpty() && searchedString2.isEmpty()&& !searchedString3.isEmpty()&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("wifi")&&
                            searchedString3.toLowerCase().contains("card")&&
                            searchedString3.toLowerCase().contains("byo")&&
                            wifi.equals("yes")&&card.equals("yes")&&byo.equals("yes")
                            ){
                        //only more, and there are three options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);


                    }else if(searchedString.isEmpty() && searchedString2.isEmpty()&& !searchedString3.isEmpty()&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("outdoor seat")&&
                            searchedString3.toLowerCase().contains("card")&&
                            searchedString3.toLowerCase().contains("byo")&&
                            searchedString3.toLowerCase().contains("wifi")&&
                            outdoorSeat.equals("yes")&&card.equals("yes")&&byo.equals("yes")&&wifi.equals("yes")
                            ){
                        //only more, and there are four options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);













                        //category and more-------------------------------------------------------------------------------------------------------------------------------------
                    }else if(!searchedString.isEmpty() && searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString.toLowerCase().contains(rcategory.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.equals("outdoor seat")&&outdoorSeat.equals("yes")
                            ){
                        //only more, and there is only outdoor seat in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }else if(!searchedString.isEmpty() && searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString.toLowerCase().contains(rcategory.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.equals("card")&&card.equals("yes")
                            ){
                        //only more, and there is only card in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }else if(!searchedString.isEmpty() && searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString.toLowerCase().contains(rcategory.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().equals("byo")&&byo.equals("yes")
                            ){
                        //only more, and there is only byo in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }else if(!searchedString.isEmpty() && searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString.toLowerCase().contains(rcategory.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().equals("wifi")&&wifi.equals("yes")
                            ){
                        //only more, and there is only wifi in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);



                    }else if(!searchedString.isEmpty() && searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString.toLowerCase().contains(rcategory.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("outdoor seat")&&
                            searchedString3.toLowerCase().contains("card")&&
                            outdoorSeat.equals("yes")&&card.equals("yes")
                            ){
                        //only more, and there are two options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }else if(!searchedString.isEmpty() && searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString.toLowerCase().contains(rcategory.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("outdoor seat")&&
                            searchedString3.toLowerCase().contains("byo")&&
                            outdoorSeat.equals("yes")&&byo.equals("yes")
                            ){
                        //only more, and there are two options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }else if(!searchedString.isEmpty() && searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString.toLowerCase().contains(rcategory.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("outdoor seat")&&
                            searchedString3.toLowerCase().contains("wifi")&&
                            outdoorSeat.equals("yes")&&wifi.equals("yes")
                            ){
                        //only more, and there are two options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);

                }else if(!searchedString.isEmpty() && searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString.toLowerCase().contains(rcategory.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("card")&&
                            searchedString3.toLowerCase().contains("byo")&&
                            byo.equals("yes")&&card.equals("yes")
                            ){
                        //only more, and there are two options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }else if(!searchedString.isEmpty() && searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString.toLowerCase().contains(rcategory.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("card")&&
                            searchedString3.toLowerCase().contains("wifi")&&
                            wifi.equals("yes")&&card.equals("yes")
                            ){
                        //only more, and there are two options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }else if(!searchedString.isEmpty() && searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString.toLowerCase().contains(rcategory.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("byo")&&
                            searchedString3.toLowerCase().contains("wifi")&&
                            byo.equals("yes")&&wifi.equals("yes")
                            ){
                        //only more, and there are two options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);


                    }else if(!searchedString.isEmpty() && searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString.toLowerCase().contains(rcategory.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("outdoor seat")&&
                            searchedString3.toLowerCase().contains("card")&&
                            searchedString3.toLowerCase().contains("byo")&&
                            outdoorSeat.equals("yes")&&card.equals("yes")&&byo.equals("yes")
                            ){
                        //only more, and there are three options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }else if(!searchedString.isEmpty() && searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString.toLowerCase().contains(rcategory.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("outdoor seat")&&
                            searchedString3.toLowerCase().contains("card")&&
                            searchedString3.toLowerCase().contains("wifi")&&
                            outdoorSeat.equals("yes")&&card.equals("yes")&&wifi.equals("yes")
                            ){
                        //only more, and there are three options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }else if(!searchedString.isEmpty() && searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString.toLowerCase().contains(rcategory.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("wifi")&&
                            searchedString3.toLowerCase().contains("card")&&
                            searchedString3.toLowerCase().contains("byo")&&
                            wifi.equals("yes")&&card.equals("yes")&&byo.equals("yes")
                            ){
                        //only more, and there are three options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);


                    }else if(!searchedString.isEmpty() && searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString.toLowerCase().contains(rcategory.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("outdoor seat")&&
                            searchedString3.toLowerCase().contains("card")&&
                            searchedString3.toLowerCase().contains("byo")&&
                            searchedString3.toLowerCase().contains("wifi")&&
                            outdoorSeat.equals("yes")&&card.equals("yes")&&byo.equals("yes")&&wifi.equals("yes")
                            ){
                        //only more, and there are four options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);






                    //cuisine and more-------------------------------------------------------------------------------------------------------------------------------------
                    }else if(searchedString.isEmpty() && !searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString2.toLowerCase().contains(rcuisine.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.equals("outdoor seat")&&outdoorSeat.equals("yes")
                            ){
                        //only more, and there is only outdoor seat in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }else if(searchedString.isEmpty() && !searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString2.toLowerCase().contains(rcuisine.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.equals("card")&&card.equals("yes")
                            ){
                        //only more, and there is only card in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }else if(searchedString.isEmpty() && !searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString2.toLowerCase().contains(rcuisine.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().equals("byo")&&byo.equals("yes")
                            ){
                        //only more, and there is only byo in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }else if(searchedString.isEmpty() && !searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString2.toLowerCase().contains(rcuisine.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().equals("wifi")&&wifi.equals("yes")
                            ){
                        //only more, and there is only wifi in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);



                    }else if(searchedString.isEmpty() && !searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString2.toLowerCase().contains(rcuisine.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("outdoor seat")&&
                            searchedString3.toLowerCase().contains("card")&&
                            outdoorSeat.equals("yes")&&card.equals("yes")
                            ){
                        //only more, and there are two options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }else if(searchedString.isEmpty() && !searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString2.toLowerCase().contains(rcuisine.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("outdoor seat")&&
                            searchedString3.toLowerCase().contains("byo")&&
                            outdoorSeat.equals("yes")&&byo.equals("yes")
                            ){
                        //only more, and there are two options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }else if(searchedString.isEmpty() && !searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString2.toLowerCase().contains(rcuisine.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("outdoor seat")&&
                            searchedString3.toLowerCase().contains("wifi")&&
                            outdoorSeat.equals("yes")&&wifi.equals("yes")
                            ){
                        //only more, and there are two options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);

                    }else if(searchedString.isEmpty() && !searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString2.toLowerCase().contains(rcuisine.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("card")&&
                            searchedString3.toLowerCase().contains("byo")&&
                            byo.equals("yes")&&card.equals("yes")
                            ){
                        //only more, and there are two options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }else if(searchedString.isEmpty() && !searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString2.toLowerCase().contains(rcuisine.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("card")&&
                            searchedString3.toLowerCase().contains("wifi")&&
                            wifi.equals("yes")&&card.equals("yes")
                            ){
                        //only more, and there are two options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }else if(searchedString.isEmpty() && !searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString2.toLowerCase().contains(rcuisine.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("byo")&&
                            searchedString3.toLowerCase().contains("wifi")&&
                            byo.equals("yes")&&wifi.equals("yes")
                            ){
                        //only more, and there are two options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);


                    }else if(searchedString.isEmpty() && !searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString2.toLowerCase().contains(rcuisine.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("outdoor seat")&&
                            searchedString3.toLowerCase().contains("card")&&
                            searchedString3.toLowerCase().contains("byo")&&
                            outdoorSeat.equals("yes")&&card.equals("yes")&&byo.equals("yes")
                            ){
                        //only more, and there are three options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }else if(searchedString.isEmpty() && !searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString2.toLowerCase().contains(rcuisine.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("outdoor seat")&&
                            searchedString3.toLowerCase().contains("card")&&
                            searchedString3.toLowerCase().contains("wifi")&&
                            outdoorSeat.equals("yes")&&card.equals("yes")&&wifi.equals("yes")
                            ){
                        //only more, and there are three options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }else if(searchedString.isEmpty() && !searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString2.toLowerCase().contains(rcuisine.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("wifi")&&
                            searchedString3.toLowerCase().contains("card")&&
                            searchedString3.toLowerCase().contains("byo")&&
                            wifi.equals("yes")&&card.equals("yes")&&byo.equals("yes")
                            ){
                        //only more, and there are three options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);


                    }else if(searchedString.isEmpty() && !searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString2.toLowerCase().contains(rcuisine.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("outdoor seat")&&
                            searchedString3.toLowerCase().contains("card")&&
                            searchedString3.toLowerCase().contains("byo")&&
                            searchedString3.toLowerCase().contains("wifi")&&
                            outdoorSeat.equals("yes")&&card.equals("yes")&&byo.equals("yes")&&wifi.equals("yes")
                            ) {
                        //only more, and there are four options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }










                    //category, cuisine and more all together filter options
                    else if(!searchedString.isEmpty() && !searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString.toLowerCase().contains(rcategory.toLowerCase())&&searchedString2.toLowerCase().contains(rcuisine.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.equals("outdoor seat")&&outdoorSeat.equals("yes")
                            ) {
                        //only more, and there is only outdoor seat in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }else if(!searchedString.isEmpty() && !searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString.toLowerCase().contains(rcategory.toLowerCase())&&searchedString2.toLowerCase().contains(rcuisine.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.equals("card")&&card.equals("yes")
                            ){
                        //only more, and there is only card in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }else if(!searchedString.isEmpty() && !searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString.toLowerCase().contains(rcategory.toLowerCase())&&searchedString2.toLowerCase().contains(rcuisine.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().equals("byo")&&byo.equals("yes")
                            ){
                        //only more, and there is only byo in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }else if(!searchedString.isEmpty() && !searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString.toLowerCase().contains(rcategory.toLowerCase())&&searchedString2.toLowerCase().contains(rcuisine.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().equals("wifi")&&wifi.equals("yes")
                            ){
                        //only more, and there is only wifi in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);



                    }else if(!searchedString.isEmpty() && !searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString.toLowerCase().contains(rcategory.toLowerCase())&&searchedString2.toLowerCase().contains(rcuisine.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("outdoor seat")&&
                            searchedString3.toLowerCase().contains("card")&&
                            outdoorSeat.equals("yes")&&card.equals("yes")
                            ){
                        //only more, and there are two options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }else if(!searchedString.isEmpty() && !searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString.toLowerCase().contains(rcategory.toLowerCase())&&searchedString2.toLowerCase().contains(rcuisine.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("outdoor seat")&&
                            searchedString3.toLowerCase().contains("byo")&&
                            outdoorSeat.equals("yes")&&byo.equals("yes")
                            ){
                        //only more, and there are two options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }else if(!searchedString.isEmpty() && !searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString.toLowerCase().contains(rcategory.toLowerCase())&&searchedString2.toLowerCase().contains(rcuisine.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("outdoor seat")&&
                            searchedString3.toLowerCase().contains("wifi")&&
                            outdoorSeat.equals("yes")&&wifi.equals("yes")
                            ){
                        //only more, and there are two options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);

                    }else if(!searchedString.isEmpty() && !searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString.toLowerCase().contains(rcategory.toLowerCase())&&searchedString2.toLowerCase().contains(rcuisine.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("card")&&
                            searchedString3.toLowerCase().contains("byo")&&
                            byo.equals("yes")&&card.equals("yes")
                            ){
                        //only more, and there are two options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }else if(!searchedString.isEmpty() && !searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString.toLowerCase().contains(rcategory.toLowerCase())&&searchedString2.toLowerCase().contains(rcuisine.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("card")&&
                            searchedString3.toLowerCase().contains("wifi")&&
                            wifi.equals("yes")&&card.equals("yes")
                            ){
                        //only more, and there are two options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }else if(!searchedString.isEmpty() && !searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString.toLowerCase().contains(rcategory.toLowerCase())&&searchedString2.toLowerCase().contains(rcuisine.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("byo")&&
                            searchedString3.toLowerCase().contains("wifi")&&
                            byo.equals("yes")&&wifi.equals("yes")
                            ){
                        //only more, and there are two options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);


                    }else if(!searchedString.isEmpty() && !searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString.toLowerCase().contains(rcategory.toLowerCase())&&searchedString2.toLowerCase().contains(rcuisine.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("outdoor seat")&&
                            searchedString3.toLowerCase().contains("card")&&
                            searchedString3.toLowerCase().contains("byo")&&
                            outdoorSeat.equals("yes")&&card.equals("yes")&&byo.equals("yes")
                            ){
                        //only more, and there are three options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }else if(!searchedString.isEmpty() && !searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString.toLowerCase().contains(rcategory.toLowerCase())&&searchedString2.toLowerCase().contains(rcuisine.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("outdoor seat")&&
                            searchedString3.toLowerCase().contains("card")&&
                            searchedString3.toLowerCase().contains("wifi")&&
                            outdoorSeat.equals("yes")&&card.equals("yes")&&wifi.equals("yes")
                            ){
                        //only more, and there are three options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }
                    else if(!searchedString.isEmpty() && !searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString.toLowerCase().contains(rcategory.toLowerCase())&&searchedString2.toLowerCase().contains(rcuisine.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("wifi")&&
                            searchedString3.toLowerCase().contains("card")&&
                            searchedString3.toLowerCase().contains("byo")&&
                            wifi.equals("yes")&&card.equals("yes")&&byo.equals("yes")
                            ){
                        //only more, and there are three options in more;
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }

                    else if(!searchedString.isEmpty() && !searchedString2.isEmpty()&& !searchedString3.isEmpty()&&searchedString.toLowerCase().contains(rcategory.toLowerCase())&&searchedString2.toLowerCase().contains(rcuisine.toLowerCase())&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")
                            searchedString3.toLowerCase().contains("wifi")&&
                            searchedString3.toLowerCase().contains("card")&&
                            searchedString3.toLowerCase().contains("byo")&&
                            searchedString3.toLowerCase().contains("outdoor seat")&&
                            wifi.equals("yes")&&card.equals("yes")&&byo.equals("yes")&&outdoorSeat.equals("yes")
                            ){
                        nameList.add(name);
                        addressList.add(address);
                        profile_photoList.add(profile_photo);
                    }
//                    if(searchedString.toLowerCase().contains( rcategory.toLowerCase() ) && (searchedString2.toLowerCase().contains(rcuisine.toLowerCase()))&&
//                            snapshot.child(searchedString3.toLowerCase()).getValue(String.class).equals("yes")){
//                        nameList.add(name);
//                        addressList.add(address);
//                        profile_photoList.add(profile_photo);
//                    }
                }

                searchAdapter = new SearchAdapter( FilterActivity.this, nameList, addressList, profile_photoList );
                recyclerView.setAdapter( searchAdapter) ;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } );

    }




    public void setUpToolBar(){
        //setup the cancel button for navigating back to "HomeActivity"
        Button backArrow = (Button) findViewById(R.id.cancelChanges);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: naviagating back to 'HomeActivity'");
                finish();
            }
        });

//        Button filterDone = (Button) findViewById(R.id.saveChanges);
//        filterDone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: filter selection finished.");
//                clearList();
//            }
//        });

    }

    public void saveChangesButton(View view){

        Button filterDone = (Button) findViewById(R.id.saveChanges);
        filterDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: filter selection finished.");
                clearList();
            }
        });

        String items = "";
        for(String item:selectedItemsforToast){
            items += "-" +item + "\n";
        }
        Toast.makeText(this, "You have selected \n" + items,Toast.LENGTH_LONG).show();

    }
}
//    ListView checkList = (ListView) findViewById(R.id.checkableList);
//        checkList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//        String[] items= {"Chinese Food","Open Now", "Distance", "rating"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.layout_center_filter, R.id.checkBox, items);
//        checkList.setAdapter(adapter);
//        checkList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String selectedItem = ((TextView)view).getText().toString();
//                if(selectedItems.contains(selectedItem)){
//                    selectedItems.remove(selectedItem); //unchecked item
//                } else
//                    selectedItems.add(selectedItem);
//            }
//        });

//           /**
////         * spinner-----------------------------------------------------------------------------------------
////         */
////        Spinner mySpinner1 = (Spinner) findViewById(R.id.spinner1);
////        ArrayAdapter<String> myAdapter1 = new ArrayAdapter<String>(FilterActivity.this,
////                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Country));
////        myAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////        mySpinner1.setAdapter(myAdapter1);
////
////        mySpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
////            @Override
////            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////                if (position == 1){
////                    startActivity(new Intent(FilterActivity.this, SearchActivity.class));
////                }
////            }
////
////            @Override
////            public void onNothingSelected(AdapterView<?> parent) {
////
////            }
////        });