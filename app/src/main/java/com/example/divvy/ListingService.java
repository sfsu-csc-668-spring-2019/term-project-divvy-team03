package com.example.divvy;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;

import com.example.divvy.models.Listing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static com.example.divvy.httprequest.GET_CODE;
import static com.example.divvy.httprequest.POST_CODE;
import static com.example.divvy.httprequest.get;

public class ListingService extends IntentService {
    public ListingService(){
        super("getlistings");
    }
    @Override
    protected void onHandleIntent( @Nullable Intent intent) {
        Bundle bundle = new Bundle();
        ResultReceiver receiver = intent.getParcelableExtra("receiver");
        int request_type = intent.getIntExtra("type",-1);
        String uri = intent.getStringExtra("uri");
        if(request_type == POST_CODE) {
            try {
                System.out.println("Output post: " + httprequest.post(uri, intent.getStringExtra("data")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(request_type == GET_CODE) {
            try {
                String data = get((HashMap<String, String>) intent.getSerializableExtra("data"), uri);
                ArrayList<Listing> listings = convertDataToListings(data);
                bundle.putSerializable("data", listings);
                receiver.send(1, bundle);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            System.err.println("Request type unknown");
        }

    }
    public static ArrayList<Listing> convertDataToListings(String s) throws JSONException {
        ArrayList<Listing> listings = new ArrayList<>();
        JSONArray array = new JSONArray(s);
        for(int i = 0; i < array.length();i++){
            JSONObject jsonObject = (JSONObject)array.get(i);
            Listing listing = new Listing(
                    jsonObject.getString("title"),
                    jsonObject.getString("descr"),
                    jsonObject.getString("username"),
                    jsonObject.getInt("status"),
                    jsonObject.getLong("listing_id"));
            listings.add(listing);
        }
        return listings;
    }
    // helper method to call this from any controller.
    public static void GetListingsByUsername(Context context, ResultReceiver receiver, String owner){
        Intent i = new Intent(context, ListingService.class);
        HashMap<String,String> data = new HashMap<>();
        data.put("username", "alex");
        i.putExtra("data",data);
        i.putExtra("type", httprequest.GET_CODE);
        i.putExtra("uri", httprequest.ROOT_ADDRESS + "/searchbyusername");
        i.putExtra("receiver", receiver);
        context.startService(i);
    }
    public static void GetListingsBySearch(Context context, ResultReceiver receiver, String query){
        Intent i = new Intent(context, ListingService.class);
        HashMap<String,String> data = new HashMap<>();
        data.put("like", query);
        i.putExtra("data",data);
        i.putExtra("type", httprequest.GET_CODE);
        i.putExtra("uri", httprequest.ROOT_ADDRESS + "/search");
        i.putExtra("receiver", receiver);
        context.startService(i);
    }
    public static void GetListingById(Context context, ResultReceiver receiver, Long listing_id){
        Intent i = new Intent(context, ListingService.class);
        HashMap<String,String> data = new HashMap<>();
        data.put("id", listing_id.toString());
        i.putExtra("data",data);
        i.putExtra("type", httprequest.GET_CODE);
        i.putExtra("uri", httprequest.ROOT_ADDRESS + "/searchbyID");
        i.putExtra("receiver", receiver);
        context.startService(i);
    }

}
