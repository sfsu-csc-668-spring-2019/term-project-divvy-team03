package com.example.divvy.Controllers.Services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;

import com.example.divvy.Factories.MessageFactory;
import com.example.divvy.models.Message;
import com.example.divvy.models.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.divvy.Controllers.Services.httprequest.get;

public class ChatHistoryService extends IntentService {

    public ChatHistoryService() {
        super("GetRatingService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Bundle bundle = new Bundle();
        ResultReceiver receiver = intent.getParcelableExtra("receiver");
        try {
            String data = get((HashMap<String, String>) intent.getSerializableExtra("data"), intent.getStringExtra("uri"));
            System.err.println(data);
            ArrayList<Message> messages = convertDataToMessages(data);
            bundle.putSerializable("data", messages);
            receiver.send(1, bundle);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    public static ArrayList<Message> convertDataToMessages(String s) throws JSONException {
        ArrayList<Message> messages = new ArrayList<>();
        JSONArray array = new JSONArray(s);
        for (int i = array.length() - 1; i >= 0; i--) {
            JSONObject jsonObject = (JSONObject) array.get(i);
            Message message = MessageFactory.create(jsonObject);
            messages.add(message);
        }
        return messages;
    }

    // helper method to call this from any controller.
    public static void GetChatHistoryById(Context context, ResultReceiver receiver, Long id) {
        Intent i = new Intent(context, ChatHistoryService.class);
        HashMap<String, String> data = new HashMap<>();
        data.put("id", Long.toString(id));
        i.putExtra("data", data);
        i.putExtra("type", httprequest.GET_CODE);
        i.putExtra("uri", httprequest.ROOT_ADDRESS + "/chatbyID");
        i.putExtra("receiver", receiver);
        context.startService(i);
    }



}
