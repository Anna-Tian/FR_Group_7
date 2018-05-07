package com.example.anna.fr.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

import com.example.anna.fr.R;

/**
 * Created by anna on 14/4/18.
 */

public class SavedFragment extends Fragment {
    private static final String TAG = "SavedFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_saved, container, false);

        return view;
    }
}
