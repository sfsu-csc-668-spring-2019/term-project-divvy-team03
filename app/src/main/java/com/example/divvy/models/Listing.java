package com.example.divvy.models;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.divvy.httprequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class Listing implements Serializable {
    String title;
    String descr;
    String username;
    int status;
    long listing_id;

    public Listing(String title, String desc, String username, int status, long listingid) {
        this.title = title;
        this.descr = desc;
        this.username = username;
        this.status = status;
        this.listing_id = listingid;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescr() {
        return descr;
    }
    public void setDescr(String descr) {
        this.descr = descr;
    }
    public String getOwner() {
        return username;
    }
    public void setOwner(String username) {
        this.username = username;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public long getListingid() {
        return listing_id;
    }
    public void setListingid(int listingid) {
        this.listing_id = listingid;
    }
    public void postData(Context currentContext){
        JSONObject data = new JSONObject();
        try {
            data.put("username", getOwner());
            data.put("descr", descr);
            data.put("title", title);
            //change to owner_id
        }
        catch(JSONException e) {
            Log.d("Listing JSON", "Error adding data to Listing JSON");
        }
        System.out.println(data.toString());
        Intent intent = new Intent(currentContext, httprequest.class);
        intent.putExtra("data", data.toString());
        intent.putExtra("type", httprequest.POST_CODE);
        intent.putExtra("uri", httprequest.ROOT_ADDRESS + "/newListing");
        currentContext.startService(intent);
    }
    public String toString(){
       return "{\"username\": " + getOwner() + ",\"descr\": " + getDescr() + ",\"title\":" + getTitle()+ "}" ;
    }
}
