package com.example.anna.fr.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anna.fr.R;
import com.example.anna.fr.models.RestaurantDetails;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder>{
    Context context;

    private Context mContext;
    private LayoutInflater bsman = null;

    private ArrayList<String> s;

    ArrayList<String> nameList;
    ArrayList<String> addressList;
     ArrayList<String> profile_photoList;

    class SearchViewHolder extends RecyclerView.ViewHolder{
        ImageView profileImage;
        TextView name, address;

        public SearchViewHolder(View itemView){
            super(itemView);
            profileImage = (ImageView) itemView.findViewById( R.id.restaurantImage );
            name = (TextView) itemView.findViewById( R.id.restaurantName );
            address = (TextView) itemView.findViewById( R.id.restaurantAddress );
        }
    }

    public SearchAdapter(Context context, ArrayList<String> nameList,ArrayList<String> addressList, ArrayList<String> profile_photoList){
        this.context = context;
        this.nameList = nameList;
        this.addressList = addressList;
        this.profile_photoList = profile_photoList;

        this.mContext = context;
        bsman = LayoutInflater.from(context);

    }

    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( context ).inflate( R.layout.snippet_center_restaurant_introduction ,parent,false);
        return new SearchAdapter.SearchViewHolder( view );
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, final int position) {
        s = nameList;

        holder.name.setText( nameList.get( position ) );
        holder.address.setText( addressList.get( position ) );

        Picasso.with( context ).load( profile_photoList.get( position )).placeholder( R.mipmap.ic_launcher_round ).into( holder.profileImage );


        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText( context, "skip page", Toast.LENGTH_SHORT ).show();






            }

        });

    }





    @Override
    public int getItemCount() {
        return nameList.size();
    }



}
