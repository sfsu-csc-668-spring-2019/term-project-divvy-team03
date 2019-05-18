package com.example.divvy;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;

import com.example.divvy.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class LoginService extends IntentService {


    public LoginService() {
        super("loginthread");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Bundle bundle = new Bundle();
        ResultReceiver receiver = intent.getParcelableExtra("receiver");
        try {
            String data = httprequest.get(
                    (LinkedHashMap<String,String>)intent.getSerializableExtra("data"),
                    intent.getStringExtra("uri"));
            System.out.println("data:" + data);
            String username = convertDataToUsername(data);
            System.out.println(username);
            bundle.putString("response",username);
            receiver.send(1, bundle);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static void getData(Context context, ResultReceiver receiver, LinkedHashMap<String,String> data){
        Intent i = new Intent(context, LoginService.class);
        i.putExtra("data", data);
        i.putExtra("type", httprequest.GET_CODE);
        i.putExtra("uri", (httprequest.ROOT_ADDRESS + "/login"));
        i.putExtra("receiver", receiver);
        context.startService(i);
    }
    public static String convertDataToUsername(String data) throws JSONException {
        JSONArray array = new JSONArray(data);
        if(array.length() != 0) {
            JSONObject jsonObject = (JSONObject) array.get(0);
            return jsonObject.getString("username");
        }
        return "FAIL";
    }
}
