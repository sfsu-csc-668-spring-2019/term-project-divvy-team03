package com.example.divvy;


import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import java.io.IOException;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class httprequest extends IntentService{

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    public static final String ROOT_ADDRESS = "http://ec2-34-226-139-149.compute-1.amazonaws.com";
    public static final int GET_CODE = 1;
    public static final int POST_CODE = 2;
    private static OkHttpClient client = new OkHttpClient();

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     *
     */
    public httprequest(){
        super("httprequest");
    }
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

    @Override
    protected void onHandleIntent(Intent intent) {
        int type = intent.getIntExtra("type",-1);
        ResultReceiver receiver = intent.getParcelableExtra("receiver");
        Bundle bundle = new Bundle();
        if(type == GET_CODE){
            try {
                String data = get((Map<String, String>) intent.getSerializableExtra("data"), "http://ec2-34-226-139-149.compute-1.amazonaws.com/getbyowner");
                bundle.putString("data", data);
                receiver.send(1, bundle);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type == POST_CODE){
            System.out.println(intent.getStringExtra("uri"));
            System.out.println(intent.getStringExtra("data"));
            try {
                System.out.println("Output post: " + post(intent.getStringExtra("uri"), intent.getStringExtra("data")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
