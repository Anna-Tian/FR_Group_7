package com.example.anna.fr.utils;



import android.content.Context;

import android.content.Intent;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;

import android.widget.Button;

import android.widget.ImageView;

import android.widget.RatingBar;

import android.widget.TextView;

import android.widget.Toast;



import com.example.anna.fr.R;

import com.example.anna.fr.models.RestaurantDetails;

import com.google.firebase.database.DataSnapshot;

import com.google.firebase.database.DatabaseError;

import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import com.squareup.picasso.Picasso;



import com.google.firebase.database.DatabaseReference;



import java.util.ArrayList;



public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder>{
    Context context;
    private Context mContext;
    private LayoutInflater bsman = null;
    ArrayList<String> nameList;
    ArrayList<String> addressList;
    ArrayList<String> profile_photoList;
    DatabaseReference databaseReference;

   class SearchViewHolder extends RecyclerView.ViewHolder{
        ImageView profileImage;
        TextView name, address,tRating;
        RatingBar ratingBar;

        public SearchViewHolder(View itemView){
            super(itemView);
            profileImage = (ImageView) itemView.findViewById( R.id.restaurantImage );
            name = (TextView) itemView.findViewById( R.id.restaurantName );
            address = (TextView) itemView.findViewById( R.id.restaurantAddress );
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            tRating = (TextView) itemView.findViewById(R.id.ratingBarText);
        }
    }

    public SearchAdapter(Context context, ArrayList<String> nameList, ArrayList<String> addressList, ArrayList<String> profile_photoList){
        this.context = context;
        this.nameList = nameList;
        this.addressList = addressList;
        this.profile_photoList = profile_photoList;
        this.mContext = context;

        bsman = LayoutInflater.from(context);
    }
    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        View view = LayoutInflater.from( context ).inflate( R.layout.snippet_center_restaurant_introduction ,parent,false);
        return new SearchAdapter.SearchViewHolder( view );
    }

    @Override
    public void onBindViewHolder(final SearchViewHolder holder, final int position) {
        holder.name.setText( nameList.get( position ) );
        holder.address.setText( addressList.get( position ) );

        Picasso.with( context ).load( profile_photoList.get( position )).placeholder( R.mipmap.ic_launcher_round ).into( holder.profileImage );

        databaseReference.child("restaurant_details").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (final DataSnapshot ds:dataSnapshot.getChildren()){
                    String rName = ds.child("name").getValue(String.class);
                    String rAddress = ds.child("address").getValue(String.class);
                    if(areSame(rName,nameList.get(position))&&areSame(rAddress,addressList.get(position))){
                        holder.ratingBar.setRating(ds.child("rating").getValue(float.class));
                        holder.tRating.setText(ds.child("rating").getValue(float.class).toString());
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(mContext, ds.child("name").getValue(String.class), Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(mContext,RestaurantActivity.class);
                                intent.putExtra("name",ds.child("name").getValue(String.class));
                                intent.putExtra("profilePhoto",ds.child("profile_photo").getValue(String.class));
                                intent.putExtra("address",ds.child("address").getValue(String.class));
                                intent.putExtra("phone",ds.child("phone").getValue(String.class));
                                intent.putExtra("rating",ds.child("rating").getValue(float.class));
                                mContext.startActivity(intent);
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "Full Name Clicked", Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent(mContext, RestaurantActivity.class);//你要跳转的界面
//                intent.putExtra("name",nameList.get(position));
//                intent.putExtra("profilePhoto",profile_photoList.get(position));
//                intent.putExtra("address",addressList.get(position));
////                intent.putExtra("phone",model.getPhone());
//                mContext.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }
    private boolean areSame(String string1, String string2){
        return string1.equals(string2);
    }
}