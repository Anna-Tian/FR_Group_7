package com.example.anna.fr.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.anna.fr.R;

public class ReviewFragment extends Fragment{
    private static final String TAG = "ReviewFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_review, container, false);
        ImageView btnBackArrow = (ImageView) view.findViewById(R.id.backArrow);

        btnBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating back to 'MeActivity'");
                Intent intent = new Intent(getActivity(), MeActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
