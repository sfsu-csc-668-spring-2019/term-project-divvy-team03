package com.example.divvy.models;


import org.json.JSONException;
import org.json.JSONObject;

public class Review {
    int reviewid;
    double rating;
    User owner;
    String comment;
    User targetUser;

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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(User targetUser) {
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
