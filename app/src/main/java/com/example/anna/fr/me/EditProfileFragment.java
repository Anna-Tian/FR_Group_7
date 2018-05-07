package com.example.anna.fr.me;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.anna.fr.R;
import com.example.anna.fr.utils.UniversalImageLoader;

/**
 * Created by anna on 17/4/18.
 */

public class EditProfileFragment extends Fragment {
    private static final String TAG = "EditProfileFragment";
    private ImageView mProfilePhoto;

    @Nullable
    @Override


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_editprofile, container, false);
        mProfilePhoto = (ImageView) view.findViewById(R.id.profile_photo);

        setProfileImage();

        //setup the backarraw for navigating back to "ProfileActivity"
        ImageView backArrow = (ImageView) view.findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: naviagating back to 'ProfileActivity'");
                getActivity().finish();
            }
        });


        return view;
    }



    private void setProfileImage(){
        Log.d(TAG, "setProfileImage: setting profile image.");
        String imgURL = "pbs.twimg.com/profile_images/875443327835025408/ZvmtaSXW_400x400.jpg";
        UniversalImageLoader.setImage(imgURL, mProfilePhoto, null, "https://");
    }
}

