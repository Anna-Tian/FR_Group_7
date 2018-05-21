package com.example.anna.fr.models;

import java.util.Comparator;

public class RestaurantIntro {
    private String name;
    private String profile_photo;
    private String address;
    private int rating;

    public RestaurantIntro(String name, String profile_photo, String address, int rating) {
        this.name = name;
        this.profile_photo = profile_photo;
        this.address = address;
        this.rating = rating;
    }

    public RestaurantIntro() {

    }

    public static final Comparator<RestaurantIntro> ComparatorBy = new Comparator<RestaurantIntro>() {
        @Override
        public int compare(RestaurantIntro o1, RestaurantIntro o2) {
            if (o1.getRating() > o2.getRating()){
                return -1;}
            else if (o1.getRating() < o2.getRating()){
                return 1;}
            else{
                return 0;}

        }
    };

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
                ", rating='" + rating + '\'' +
                '}';
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
