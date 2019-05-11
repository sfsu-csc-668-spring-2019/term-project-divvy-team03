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

import static com.example.divvy.httprequest.get;

public class GetListingsService extends IntentService {
    public GetListingsService(){
        super("getlistings");
    }
    @Override
    protected void onHandleIntent( @Nullable Intent intent) {
        Bundle bundle = new Bundle();
        ResultReceiver receiver = intent.getParcelableExtra("receiver");
        try {
            String data = get((HashMap<String, String>) intent.getSerializableExtra("data"));
            ArrayList<Listing> listings = convertDataToListings(data);
            System.out.println("Size of arraylist:"  + listings.size());
            bundle.putSerializable("data", listings);
            receiver.send(1, bundle);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
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

    // helper method to call this from any controller.
    public static void GetListingsByUsername(Context context, ResultReceiver receiver){
        Intent i = new Intent(context, GetListingsService.class);
        HashMap<String,String> data = new HashMap<>();
        data.put("username", "alex");
        i.putExtra("data",data);
        i.putExtra("type", httprequest.GET_CODE);
        i.putExtra("owner", "alex");
        i.putExtra("uri", httprequest.ROOT_ADDRESS + "/getbyowner");
        i.putExtra("receiver", receiver);
        context.startService(i);
    }
}
