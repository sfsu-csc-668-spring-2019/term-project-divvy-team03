package com.example.divvy.models;


import android.content.Context;
import android.content.Intent;

import com.example.divvy.httprequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

public class Review implements Serializable {
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
        this.date = date;
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

    public String getDate(){ return this.date;}

    public String sendData(Context currentContext){
        JSONObject data = new JSONObject();
        try{
        data.put("comments", comment);
        data.put("user_rating", owner);
        data.put("user_rated",targetUser);
        data.put("rating",rating);
        }
        catch(JSONException e){

        }
        System.out.println(data.toString());
        Intent intent = new Intent(currentContext, httprequest.class);
        intent.putExtra("data", data.toString());
        intent.putExtra("type", httprequest.POST_CODE);
        intent.putExtra("uri", httprequest.ROOT_ADDRESS + "/rate");
        return data.toString();
    }
}
