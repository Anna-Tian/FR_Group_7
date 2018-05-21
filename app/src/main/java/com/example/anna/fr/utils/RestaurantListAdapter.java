
package com.example.anna.fr.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.anna.fr.R;
import com.example.anna.fr.models.Restaurant;
import com.example.anna.fr.models.RestaurantDetails;
import com.example.anna.fr.models.RestaurantIntro;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RestaurantListAdapter extends ArrayAdapter<RestaurantIntro>{

    private static final String TAG = "RestaurantListAdapter";

    private LayoutInflater mInflater;
    private List<RestaurantIntro> mRestaurantIntro = null;
    private List<RestaurantDetails> mRestaurantDetails = null;

    private int layoutResource;
    private Context mContext;

    public RestaurantListAdapter(@NonNull Context context, int resource, @NonNull List<RestaurantIntro> objects) {
        super(context, resource, objects);

        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutResource = resource;
        this.mRestaurantIntro = objects;

        Collections.sort(mRestaurantIntro, RestaurantIntro.ComparatorBy);
        Collections.sort(mRestaurantDetails, RestaurantDetails.ComparatorBy);
    }

    private static class ViewHolder{
        TextView restaurantName, restaurantAddress,ratingBarText;
        ImageView restaurantImage;
        RatingBar ratingBar;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null){
            Log.d(TAG, "getView: test 2");
            convertView = mInflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder();

            holder.restaurantName = (TextView) convertView.findViewById(R.id.restaurantName);
            holder.restaurantAddress = (TextView) convertView.findViewById(R.id.restaurantAddress);
            holder.restaurantImage = (ImageView) convertView.findViewById(R.id.restaurantImage);
            holder.ratingBarText=(TextView) convertView.findViewById(R.id.ratingBarText);
            holder.ratingBar=(RatingBar)convertView.findViewById(R.id.ratingBar) ;

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Log.d(TAG, "getView: test 4");
        holder.restaurantName.setText(mRestaurantIntro.get(position).getName());
        holder.restaurantAddress.setText(getItem(position).getAddress());
        holder.ratingBarText.setText((int)getItem(position).getRating());






        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child(mContext.getString(R.string.dbname_restaurant_details))
                .orderByChild(mContext.getString(R.string.field_restaurant_id))
                .equalTo(mRestaurantDetails.get(position).getRes_id());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                    Log.d(TAG, "onDataChange: found restaurant: " +
                            singleSnapshot.getValue(RestaurantIntro.class).toString());

                    ImageLoader imageLoader = ImageLoader.getInstance();
                    imageLoader.displayImage(singleSnapshot.getValue(RestaurantIntro.class).getProfile_photo(), holder.restaurantImage);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return convertView;

    }
}

