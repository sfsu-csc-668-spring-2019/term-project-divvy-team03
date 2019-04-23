package com.example.divvy;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;


public class Listing {
    String title;
    String desc;
    int capacity;
    //change to User object when created
    String owner;
    String status;
    int listingid;

    public Listing(String title, String desc, int capacity, String owner, String status, int listingid) {
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
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
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
    public String sendData(){
        JSONObject data = new JSONObject();
        try {
            data.put("listingid", listingid);
            data.put("title", title);
            data.put("desc", desc);
            data.put("capacity",capacity);
            //change to owner_id
            data.put("owner", owner);
            data.put("status", status);
        }
        catch(JSONException e) {
            Log.d("Listing JSON", "Error adding data to Listing JSON");
        }
        return data.toString();
    }
}
