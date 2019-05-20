package com.example.divvy.Controllers.Services;


import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.HttpUrl;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class httprequest{

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    public static final String ROOT_ADDRESS = "http://ec2-34-226-139-149.compute-1.amazonaws.com";
    public static final int GET_CODE = 1;
    public static final int POST_CODE = 2;
    public static final int SUCCESS_CODE = 1;
    public static final int FAIL_CODE = -1;
    private static OkHttpClient client = new OkHttpClient();

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     *
     */

    //http://ec2-34-226-139-149.compute-1.amazonaws.com/login   (get)
    //http://ec2-34-226-139-149.compute-1.amazonaws.com/reg     (post)
    //http://ec2-34-226-139-149.compute-1.amazonaws.com/newListing  (post)
    public static String get(Map<String,String> params, String uri) throws IOException {
        HttpUrl.Builder httpBuilder = HttpUrl.parse(uri).newBuilder();
        if (params != null) {
            for(Map.Entry<String, String> param : params.entrySet()) {
                httpBuilder.addQueryParameter(param.getKey(),param.getValue());
            }
        }
        String url = httpBuilder.build().toString();
        System.out.println(url);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }

    }
    public static String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
    public static String imagePost(String url, File image) throws IOException {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", "aw2")
                .addFormDataPart("email", "test.com")
                .addFormDataPart("password", "123")
                .addFormDataPart("first_name", "hey")
                .addFormDataPart("last_name", "heyy")
                .addFormDataPart("city", "test")
                .addFormDataPart("descr", "crazy and tired")
                .addFormDataPart("profile", "profile.png",
                        RequestBody.create(MediaType.parse("multipart/form-data"),
                                image))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    /*
     The reason for this is because we iterate over a map to get params for our get request.
     We found out pretty later on that the order of our params arent in the order send them.
     (We originally were sending them in hashmaps). To fix this, we decided to use linkedhashmap
     which keeps the order of insertion. Android doesn't like linkedhashmaps and will convert them to
     regular hashmaps when they are passed through intents. The workaround was to convert these linkedhashmaps
     to two arraylists: keys and values, then pass these to the service that makes the call, and convert
     them back into a linkedhashmap.

     tldr; this lets us pass hashmap data through android intents while keeping the order of insertion.
    */

    // This method converts a linkedhashmap into an arraylist of key and values
    public static ArrayList<ArrayList<String>> convertLinkedHashMapToList(LinkedHashMap<String,String> map){
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        for (Map.Entry<String,String> entry: map.entrySet()){
            keys.add(entry.getKey());
            values.add(entry.getValue());
        }
        ArrayList<ArrayList<String>> keyvalues = new ArrayList<>();
        keyvalues.add(keys);
        keyvalues.add(values);
        return keyvalues;
    }
    // This method converts an Arraylist of: arraylist of keys and arraylist of values into a linkedhashmap
    public static LinkedHashMap<String,String> convertListToLinkedHashMap(ArrayList<ArrayList<String>> keyvalues){
        LinkedHashMap<String,String> linkedMap = new LinkedHashMap<>();
        ArrayList<String> keys = keyvalues.get(0);

        ArrayList<String> values = keyvalues.get(1);
        for(int i = 0; i < keys.size(); i++){
            linkedMap.put(keys.get(i),values.get(i));
        }
        return linkedMap;
    }

}
