package com.example.anna.fr.models;

import android.support.v7.widget.CardView;

import com.example.anna.fr.R;

public class RestaurantIntro {
    private String name;
    private String profile_photo;
    private String address;

    public RestaurantIntro(String name, String profile_photo, String address) {
        this.name = name;
        this.profile_photo = profile_photo;
        this.address = address;
    }

    public RestaurantIntro() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "RestaurantIntro{" +
                "name='" + name + '\'' +
                ", profile_photo='" + profile_photo + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
