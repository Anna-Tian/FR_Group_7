package com.example.anna.fr.random;

/**
 * Created by anna on 24/4/18.
 */

public class ItemView {
    private String name;
    private int photo;
    private String address;

    public ItemView(String name, int photo, String address) {
        this.name = name;
        this.photo = photo;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
