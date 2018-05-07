package com.example.anna.fr.search;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anna.fr.R;

/**
 * Created by anna on 14/4/18.
 */

public class HistoryFragment extends Fragment {
    private static final String TAG = "HistoryFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_history, container, false);

        return view;
    }
}
