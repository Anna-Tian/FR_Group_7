package com.example.anna.fr.utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.anna.fr.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RestaurantDetails3 extends AppCompatActivity {
    private TextView name;
    private TextView address;
    private TextView phone;
    private DatabaseReference mReference;
    private String s = "003";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_restaurant_details );
        //初始化控件
        iniUI();
        //初始化数据
        initData();
    }


    private void initData() {
        //构建数据库实例对象，引用为mReference
        mReference = FirebaseDatabase.getInstance().getReference("restaurant_details").child(s );
        //通过键名，获取数据库实例对象的子节点对象
        DatabaseReference date = mReference.child("name");
        DatabaseReference content = mReference.child("address");


        //注册子第一个节点对象数据变化的监听者对象
        date.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //数据库数据变化时调用此方法
                String value = dataSnapshot.getValue(String.class);
                name.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //注册子第二个节点对象数据变化的监听者对象
        content.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //数据库数据变化时调用此方法
                String value = dataSnapshot.getValue(String.class);
                address.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void iniUI() {
        name = (TextView) findViewById(R.id.restaurantName);
        address = (TextView) findViewById(R.id.restaurantAddress);

    }
}


