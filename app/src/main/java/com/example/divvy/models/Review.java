package com.example.divvy.models;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Review {
    int reviewid;
    double rating;
    String owner;
    String comment;
    String targetUser;
    private String date;

    public Review(int reviewid, double rating, String owner, String comment, String targetUser, String date){
        this.reviewid = reviewid;
        this.rating = rating;
        this.owner = owner;
        this.comment = comment;
        this.targetUser = targetUser;
    }

    public int getReviewid() {
        return reviewid;
    }

    public void setReviewid(int reviewid) {
        this.reviewid = reviewid;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(String targetUser) {
        this.targetUser = targetUser;
    }

    public String sendData(){
        JSONObject data = new JSONObject();
        try{
        data.put("reviewid", reviewid);
        data.put("comment", comment);
        data.put("owner", owner);
        data.put("targetUser",targetUser);
        data.put("rating",rating);
        }
        catch(JSONException e){

        }
        System.out.println(data.toString());
        return data.toString();
    }
}
