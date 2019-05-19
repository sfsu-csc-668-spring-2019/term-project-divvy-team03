package com.example.divvy;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.divvy.models.Review;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class CreateRatingService extends IntentService {
    public CreateRatingService() {
        super("Get Rating Service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            JSONObject json = new JSONObject();
            json.put("rating", bundle.getString(("rating")));
            json.put("user_rated", bundle.getString(("user_rated")));
            json.put("user_rating", bundle.getString(("user_rating")));
            json.put("comments", bundle.getString(("comments")));
            String uri = bundle.getString("uri");
            Log.d("POST REQUEST", httprequest.post(uri, json.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void createReviewByUsername(Context context, Review review) {
        Intent i = new Intent(context, CreateRatingService.class);
        i.putExtra("rating", review.getRating());
        i.putExtra("user_rated", review.getTargetUser());
        i.putExtra("uri", httprequest.ROOT_ADDRESS + "/rate");
        i.putExtra("user_rating", review.getOwner());
        i.putExtra("comments", review.getComment());
        context.startService(i);
    }
}