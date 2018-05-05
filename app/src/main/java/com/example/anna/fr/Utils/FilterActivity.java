package com.example.anna.fr.Utils;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anna.fr.R;

import java.util.ArrayList;

/**
 * Created by anna on 24/4/18.
 */

public class FilterActivity extends AppCompatActivity {
    private static final String TAG = "FilterActivity";

    ArrayList<String> selectedItems = new ArrayList<>();

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Log.d(TAG, "onCreate: started");

        ListView checkList = (ListView) findViewById(R.id.checkableList);
        checkList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        String[] items= {"Chinese Food","Open Now", "Distance", "rating"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.layout_center_filter, R.id.checkBox, items);
        checkList.setAdapter(adapter);
        checkList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = ((TextView)view).getText().toString();
                if(selectedItems.contains(selectedItem)){
                    selectedItems.remove(selectedItem); //unchecked item
                } else
                    selectedItems.add(selectedItem);
            }
        });

        //setup the cancel button for navigating back to "HomeActivity"
        Button backArrow = (Button) findViewById(R.id.cancelChanges);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: naviagating back to 'HomeActivity'");
                finish();
            }
        });
    }

    public void saveChangesButton(View view){
        String items = "";
        for(String item:selectedItems){
            items += "-" +item + "\n";
        }
        Toast.makeText(this, "You have selected \n" + items,Toast.LENGTH_LONG).show();
    }

    public void cancelChangesButton(View view){

    }
}
