package com.example.anna.fr.utils;

public class UserComment {
    private String name;
    private String comment;
    private int rating;
    private String uID;

    public UserComment(){

    }

    public UserComment(String name, String comment, int rating, String uID){
        this.name = name;
        this.comment = comment;
        this.rating = rating;
        this.uID = uID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }
}
