package com.example.divvy.models;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.divvy.httprequest;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.divvy.Factories.*;
import java.io.Serializable;


public class Listing implements Serializable {
    String title;
    String desc;
    String owner;
    int status;
    int listing_id;

    public Listing(String title, String desc, String owner, int status, int listingid) {
        this.title = title;
        this.desc = desc;
        this.owner = owner;
        this.status = status;
        this.listing_id = listingid;
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
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public int getListingid() {
        return listing_id;
    }
    public void setListingid(int listingid) {
        this.listing_id = listingid;
    }
    public void postData(Context currentContext){
        JSONObject data = new JSONObject();
        try {
            data.put("name", title);
            data.put("description", desc);
            data.put("username", getOwner());
            //change to owner_id
        }
        catch(JSONException e) {
            Log.d("Listing JSON", "Error adding data to Listing JSON");
        }
        System.out.println(data.toString());
        Intent i = new Intent(currentContext, httprequest.class);
        i.putExtra("data", data.toString());
        i.putExtra("type", httprequest.POST_CODE);
        i.putExtra("uri", httprequest.ROOT_ADDRESS + "/newListing");
        currentContext.startService(i);

    }
    /*public void getListings(Context currentContext){
        Intent i = new Intent(currentContext,httprequest.class);
        HashMap<String,String> map = new HashMap<>();
        map.put("username","jonh");
        i.putExtra("data",map);
        i.putExtra("type", httprequest.GET_CODE);
        i.putExtra("uri", httprequest.ROOT_ADDRESS + "/getbyowner");
        currentContext.startService(i);
    }*/
}
