package com.example.anna.fr.models;

import java.util.Comparator;

public class RestaurantDetails {

    private long res_id;
    private String name;
    private String profile_photo;
    private long phone;
    private String category;
    private String address;
    private int rating;


    public RestaurantDetails(long res_id, String name, String profile_photo, long phone, String category, String address,int rating) {
        this.res_id = res_id;
        this.name = name;
        this.profile_photo = profile_photo;
        this.phone = phone;
        this.category = category;
        this.address = address;
        this.rating=rating;
    }

    public RestaurantDetails() {

    }


    public static final Comparator<RestaurantDetails> ComparatorBy = new Comparator<RestaurantDetails>() {
        @Override
        public int compare(RestaurantDetails o1, RestaurantDetails o2) {
            if (o1.getRating() > o2.getRating()){
                return -1;}
            else if (o1.getRating() < o2.getRating()){
                return 1;}
            else{
                return 0;}

        }
    };
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getRes_id() {
        return res_id;
    }

    public void setRes_id(long res_id) {
        this.res_id = res_id;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "RestaurantDetails{" +
                "res_id=" + res_id +
                ", name='" + name + '\'' +
                ", profile_photo='" + profile_photo + '\'' +
                ", phone=" + phone +
                ", category='" + category + '\'' +
                ", address='" + address + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }

    public float getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
