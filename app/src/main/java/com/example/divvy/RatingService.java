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

public class RatingService {

    public class GetRatingService extends IntentService{
        public GetRatingService(){
            super("Get Rating Service");
        }
        @Override
        protected void onHandleIntent( @Nullable Intent intent) {
            Bundle bundle = new Bundle();
            ResultReceiver receiver = intent.getParcelableExtra("receiver");
            try {
                String data = get((HashMap<String, String>) intent.getSerializableExtra("data"),intent.getStringExtra("uri"));
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
    }

    public class CreateRatingService extends IntentService{
        public CreateRatingService(){
            super("Get Rating Service");
        }
        @Override
        protected void onHandleIntent( @Nullable Intent intent) {
            try {
                Bundle bundle = intent.getExtras();
                JSONObject json = new JSONObject();
                json.put("rating", bundle.getString(("rating")));
                json.put("user_rated", bundle.getString(("user_rated")));
                json.put("user_rating", bundle.getString(("user_rating")));
                json.put("comments", bundle.getString(("comments")));
                String uri = bundle.getString("uri");
                httprequest.post(uri, json.toString());
            } catch(IOException e){
                e.printStackTrace();
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<Review> convertDataToRating(String s) throws JSONException {
        ArrayList<Review> reviews = new ArrayList<>();
        JSONArray array = new JSONArray(s);
        for(int i = 0; i < array.length();i++){
            JSONObject jsonObject = (JSONObject)array.get(i);
            Review review = new Review(
                    jsonObject.getInt("rating_id"),
                    jsonObject.getDouble("rating"),
                    jsonObject.getString("user_rating"),
                    jsonObject.getString("comments"),
                    jsonObject.getString("user_rated"),
                    jsonObject.getString("date"));
            reviews.add(review);
        }
        return reviews;
    }

    // helper method to call this from any controller.
    public static void GetReviewsByUsername(Context context, ResultReceiver receiver, String owner){
        Intent i = new Intent(context, GetRatingService.class);
        HashMap<String,String> data = new HashMap<>();
        data.put("username", owner);
        i.putExtra("data",data);
        i.putExtra("type", httprequest.GET_CODE);
        i.putExtra("uri", httprequest.ROOT_ADDRESS + "/searchRatingByUserRating");
        i.putExtra("receiver", receiver);
        context.startService(i);
    }

    public static void createReviewByUsername(Context context, Review review){
        Intent i = new Intent(context, CreateRatingService.class);
        i.putExtra("rating",review.getRating());
        i.putExtra("user_rated", review.getTargetUser());
        i.putExtra("uri", httprequest.ROOT_ADDRESS + "/rate");
        i.putExtra("user_rating", review.getOwner());
        i.putExtra("comments", review.getComment());
        context.startService(i);
    }

}
