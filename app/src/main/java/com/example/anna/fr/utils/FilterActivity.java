package com.example.anna.fr.utils;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.anna.fr.R;

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

    ArrayList<String> selectedItems = new ArrayList<>();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Log.d(TAG, "onCreate: started");

//        ListView checkList = (ListView) findViewById(R.id.checkableList);
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

        //setup the cancel button for navigating back to "HomeActivity"
        Button backArrow = (Button) findViewById(R.id.cancelChanges);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: naviagating back to 'HomeActivity'");
                finish();
            }
        });

//        /**
//         * spinner-----------------------------------------------------------------------------------------
//         */
//        Spinner mySpinner1 = (Spinner) findViewById(R.id.spinner1);
//        ArrayAdapter<String> myAdapter1 = new ArrayAdapter<String>(FilterActivity.this,
//                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Country));
//        myAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        mySpinner1.setAdapter(myAdapter1);
//
//        mySpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (position == 1){
//                    startActivity(new Intent(FilterActivity.this, SearchActivity.class));
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
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
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";
                        for(int i = 0; i < mUserItems1.size(); i++){
                            item = item + listItems1[mUserItems1.get(i)];
                            if (i != mUserItems1.size() -1){
                                item = item + ",";
                            }
                        }
                        mItemSelected.setText(item);
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
                mBuilder.setCancelable(false);
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
                mBuilder.setCancelable(false);
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
    }
    //-----------------------------------------------------------------------------------------------

    public void saveChangesButton(View view){
        String items = "";
        for(String item:selectedItems){
            items += "-" +item + "\n";
        }
        Toast.makeText(this, "You have selected \n" + items,Toast.LENGTH_LONG).show();
    }
}
