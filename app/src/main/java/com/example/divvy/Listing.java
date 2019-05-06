package com.example.divvy;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class Listing {
    String title;
    String desc;
    int capacity;
    //change to User object when created
    User owner;
    String status;
    int listingid;

    public Listing(String title, String desc, int capacity, User owner, String status, int listingid) {
        this.title = title;
        this.desc = desc;
        this.capacity = capacity;
        this.owner = owner;
        this.status = status;
        this.listingid = listingid;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public int getListingid() {
        return listingid;
    }
    public void setListingid(int listingid) {
        this.listingid = listingid;
    }
    public void postData(){
        JSONObject data = new JSONObject();
        try {
            data.put("name", title);
            data.put("description", desc);
            data.put("username", "alex");
            //change to owner_id
        }
        catch(JSONException e) {
            Log.d("Listing JSON", "Error adding data to Listing JSON");
        }
        System.out.println(data.toString());
        /*try {
            httprequest.post(httprequest.ROOT_ADD + "/newListing", data.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
