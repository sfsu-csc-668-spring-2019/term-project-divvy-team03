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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class LoginService extends IntentService {


    public LoginService() {
        super("loginthread");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Bundle bundle = new Bundle();
        ResultReceiver receiver = intent.getParcelableExtra("receiver");
        LinkedHashMap<String,String> hashMap =
                convertListToLinkedHashMap(
                        (ArrayList< ArrayList<String>>)intent.getSerializableExtra("data"));
        try {
            String data = httprequest.get( hashMap, intent.getStringExtra("uri"));
            String username = convertDataToUsername(data);
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
        i.putExtra("data", convertLinkedHashMapToList(data));
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
    public static LinkedHashMap<String,String> convertListToLinkedHashMap(ArrayList<ArrayList<String>> keyvalues){
        LinkedHashMap<String,String> linkedmap = new LinkedHashMap<>();
        ArrayList<String> keys = keyvalues.get(0);
        ArrayList<String> values = keyvalues.get(1);
        for(int i =0; i < keys.size(); i++){
            linkedmap.put(keys.get(i),values.get(i));
        }
        return linkedmap;
    }
}
