package com.example.anna.fr.models;


import android.support.v7.widget.CardView;

import com.example.anna.fr.R;

public class RestaurantDetails {

    private long res_id;
    private String name;
    private String profile_photo;
    private long phone;
    private String category;
    private String address;
    private String byo;
    private String card;
    private String cuisine;
    private String outdoor_seat;
    private long rating;
    private String wifi;


    public RestaurantDetails(long res_id, String name, String profile_photo, long phone, String category, String address, String byo, String card, String cuisine, String outdoor_seat, long rating, String wifi) {
        this.res_id = res_id;
        this.name = name;
        this.profile_photo = profile_photo;
        this.phone = phone;
        this.category = category;
        this.address = address;
        this.byo = byo;
        this.card = card;
        this.cuisine = cuisine;
        this.outdoor_seat = outdoor_seat;
        this.rating = rating;
        this.wifi = wifi;
    }

    public RestaurantDetails() {

    }

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

    public String getByo() {
        return byo;
    }

    public void setByo(String byo) {
        this.byo = byo;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getOutdoor_seat() {
        return outdoor_seat;
    }

    public void setOutdoor_seat(String outdoor_seat) {
        this.outdoor_seat = outdoor_seat;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public String getWifi() {
        return wifi;
    }

    public void setWifi(String wifi) {
        this.wifi = wifi;
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
                ", byo='" + byo + '\'' +
                ", card='" + card + '\'' +
                ", cuisine='" + cuisine + '\'' +
                ", outdoor_seat='" + outdoor_seat + '\'' +
                ", rating='" + rating + '\'' +
                ", wifi='" + wifi + '\'' +
                '}';
    }
}
