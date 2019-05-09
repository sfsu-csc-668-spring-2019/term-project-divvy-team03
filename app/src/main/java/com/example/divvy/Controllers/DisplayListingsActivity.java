package com.example.divvy.Controllers;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;

import com.example.divvy.ListingsReceiver;
import com.example.divvy.httprequest;
import com.example.divvy.R;
import com.example.divvy.models.Listing;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class DisplayListingsActivity extends AppCompatActivity implements ListingsReceiver.MyListingsReceiver {

    ArrayList<Listing> listings;
    ListingsReceiver mReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listings_view);
        listings = new ArrayList<>();
        mReceiver = new ListingsReceiver(new Handler(Looper.getMainLooper()), this);
        ListingListAdapter adapter = new ListingListAdapter(listings);

        RecyclerView recyclerView = findViewById(R.id.listings_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent i = new Intent(this, httprequest.class);
        HashMap<String,String> data = new HashMap<>();
        data.put("username", "alex");
        i.putExtra("data",data);
        i.putExtra("type", httprequest.GET_CODE);
        i.putExtra("uri", httprequest.ROOT_ADDRESS + "/getbyowner");
        i.putExtra("receiver", mReceiver);
        startService(i);

    }
    public void UpdateListingsView(){
        ListingListAdapter adapter = new ListingListAdapter(listings);

        RecyclerView recyclerView = findViewById(R.id.listings_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        if(resultData != null){
            try {
                listings = convertDataToListings(resultData.getString("data"));
                UpdateListingsView();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public static ArrayList<Listing> convertDataToListings(String s) throws JSONException {
        ArrayList<Listing> listings = new ArrayList<>();
        JSONArray array = new JSONArray(s);
        for(int i = 0; i < array.length();i++){
            JSONObject jsonObject = (JSONObject)array.get(i);
            Listing listing = new Listing(
                    jsonObject.getString("name"),
                    jsonObject.getString("description"),
                    jsonObject.getString("owner"),
                    jsonObject.getInt("status"),
                    jsonObject.getInt("listing_id"));
            listings.add(listing);
        }
        return listings;

    }
}
