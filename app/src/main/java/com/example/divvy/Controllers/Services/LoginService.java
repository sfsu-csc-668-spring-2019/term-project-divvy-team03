package com.example.divvy.Controllers.Services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
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
        LinkedHashMap<String,String> hashMap =
                httprequest.convertListToLinkedHashMap(
                        (ArrayList< ArrayList<String>>)intent.getSerializableExtra("data"));
        try {
            String data = httprequest.get( hashMap, intent.getStringExtra("uri"));
            HashMap<String,String> user = convertDataToUsername(data);
            bundle.putSerializable("response",user);
            receiver.send(1, bundle);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static void getData(Context context, ResultReceiver receiver, LinkedHashMap<String,String> data){
        Intent i = new Intent(context, LoginService.class);
        i.putExtra("data", httprequest.convertLinkedHashMapToList(data));
        i.putExtra("type", httprequest.GET_CODE);
        i.putExtra("uri", (httprequest.ROOT_ADDRESS + "/login"));
        i.putExtra("receiver", receiver);
        context.startService(i);
    }
    public static HashMap<String,String> convertDataToUsername(String data) throws JSONException {
        JSONArray array = new JSONArray(data);
        HashMap<String, String> userMap = new HashMap<>();
        if(array.length() != 0) {
            JSONObject jsonObject = (JSONObject) array.get(0);
             userMap.put("username", jsonObject.getString("username"));
             userMap.put("profImage", jsonObject.getString("profImage"));
        }
        return userMap;
    }

}
