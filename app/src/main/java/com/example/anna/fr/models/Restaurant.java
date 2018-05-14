package com.example.anna.fr.models;

public class Restaurant {
    private String name;
    private String comment;
    private String location;
    private int ranking;

    public Restaurant(String name, String comment, String location, int ranking) {
        this.name = name;
        this.comment = comment;
        this.location = location;
        this.ranking = ranking;
    }

    public Restaurant() {
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                ", location='" + location + '\'' +
                ", ranking=" + ranking +
                '}';
    }
}
