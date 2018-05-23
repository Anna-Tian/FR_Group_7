package com.example.anna.fr.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.anna.fr.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    private static final String TAG = "CommentAdapter";

    private Context mContext;
    private ArrayList<String> uID = new ArrayList<>();
    private String rID;
    DatabaseReference databaseReference;


    public CommentAdapter(Context mContext, ArrayList<String> uID,String rID) {
        this.mContext = mContext;
        this.uID = uID;
        this.rID = rID;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        databaseReference = FirebaseDatabase.getInstance().getReference();

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.snippet_center_review, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");
        databaseReference.child("restaurant_comments").child(rID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(final DataSnapshot ds:dataSnapshot.getChildren()){
                    String userId =  ds.child("uID").getValue(String.class);
                    if(areSame(userId,uID.get(position))){
                        holder.username.setText(ds.child("name").getValue(String.class));
                        holder.usercomment.setText(ds.child("comment").getValue(String.class));
                        holder.ratingBar.setRating(ds.child("rating").getValue(float.class));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return uID.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView username;
        RatingBar ratingBar;
        TextView usercomment;


        public ViewHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.display_name);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            usercomment = itemView.findViewById(R.id.userReview);
        }
    }

    private boolean areSame(String string1, String string2){
        return string1.equals(string2);
    }
}
