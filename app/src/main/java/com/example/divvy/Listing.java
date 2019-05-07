package com.example.divvy;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


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
    public void postData(Context currentContext){
        JSONObject data = new JSONObject();
        try {
            data.put("name", title);
            data.put("description", desc);
            data.put("username", "alex3222");
            //change to owner_id
        }
        catch(JSONException e) {
            Log.d("Listing JSON", "Error adding data to Listing JSON");
        }
        System.out.println(data.toString());
        Intent i = new Intent(currentContext,httprequest.class);
        i.putExtra("data", data.toString());
        i.putExtra("type", httprequest.POST_CODE);
        i.putExtra("uri", httprequest.ROOT_ADDRESS + "/newListing");
        currentContext.startService(i);

    }
    public void getListings(Context currentContext){
        Intent i = new Intent(currentContext,httprequest.class);
        HashMap<String,String> map = new HashMap<>();
        map.put("username","jonh");
        i.putExtra("data",map);
        i.putExtra("type", httprequest.GET_CODE);
        i.putExtra("uri", httprequest.ROOT_ADDRESS + "/getbyowner");
        currentContext.startService(i);
    }
}
