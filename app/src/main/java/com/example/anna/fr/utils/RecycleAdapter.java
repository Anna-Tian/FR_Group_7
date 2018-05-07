package com.example.anna.fr.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anna.fr.R;
import com.example.anna.fr.random.ItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anna on 24/4/18.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.Holderview>{

    public RecycleAdapter(List<ItemView> restaurantlist, Context mContext) {
        this.restaurantlist = restaurantlist;
        this.mContext = mContext;
    }

    private List<ItemView> restaurantlist;
    private Context mContext;

    @Override
    public Holderview onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.snippet_center_restaurant_introduction,parent,false);
        return null;
    }

    @Override
    public void onBindViewHolder(Holderview holder, final int position) {
        holder.v_name.setText(restaurantlist.get(position).getName());
        holder.v_address.setText(restaurantlist.get(position).getAddress());
        holder.v_image.setImageResource(restaurantlist.get(position).getPhoto());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"click on" + restaurantlist.get(position).getName(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantlist.size();
    }

    public void setfilter(List<ItemView> listitem){
        restaurantlist = new ArrayList<>();
        restaurantlist.addAll(listitem);
        notifyDataSetChanged();
    }

    class Holderview extends RecyclerView.ViewHolder {
        ImageView v_image;
        TextView v_name;
        TextView v_address;

        Holderview(View itemview){
            super(itemview);
            v_image = (ImageView) itemview.findViewById(R.id.restaurantImage);
            v_name = (TextView) itemview.findViewById(R.id.restaurantName);
            v_address = (TextView) itemview.findViewById(R.id.restaurantAddress);
        }
    }
}
