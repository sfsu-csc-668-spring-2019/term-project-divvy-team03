package com.example.divvy;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;

import com.example.divvy.models.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.divvy.httprequest.get;

public class GetRatingService extends IntentService {

    public GetRatingService() {
        super("GetRatingService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Bundle bundle = new Bundle();
        ResultReceiver receiver = intent.getParcelableExtra("receiver");
        try {
            String data = get((HashMap<String, String>) intent.getSerializableExtra("data"), intent.getStringExtra("uri"));
            System.err.println(data);
            ArrayList<Review> reviews = convertDataToRating(data);
            bundle.putSerializable("data", reviews);
            receiver.send(1, bundle);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    public static ArrayList<Review> convertDataToRating(String s) throws JSONException {
        ArrayList<Review> reviews = new ArrayList<>();
        JSONArray array = new JSONArray(s);
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObject = (JSONObject) array.get(i);
            Review review = new Review(
                    jsonObject.getInt("rating_id"),
                    (float)jsonObject.getDouble("rating"),
                    jsonObject.getString("user_rating"),
                    jsonObject.getString("comments"),
                    jsonObject.getString("user_rated"),
                    jsonObject.getString("date"));
            reviews.add(review);
        }
        return reviews;
    }

    // helper method to call this from any controller.
    public static void GetReviewsByUsername(Context context, ResultReceiver receiver, String owner) {
        Intent i = new Intent(context, GetRatingService.class);
        HashMap<String, String> data = new HashMap<>();
        data.put("username", owner);
        i.putExtra("data", data);
        i.putExtra("type", httprequest.GET_CODE);
        i.putExtra("uri", httprequest.ROOT_ADDRESS + "/searchRatingByUserRated");
        i.putExtra("receiver", receiver);
        context.startService(i);
    }



}
