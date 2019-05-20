package com.example.divvy.Controllers.Services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;

import java.io.IOException;

public class RegService extends IntentService {
    public RegService() {
        super("regthread");
    }

    protected void onHandleIntent(@Nullable Intent intent) {
        Bundle bundle = new Bundle();
        ResultReceiver receiver = intent.getParcelableExtra("receiver");
        System.out.println("URI: " + intent.getStringExtra("uri"));
        try {
            String data = httprequest.post(intent.getStringExtra("uri"),intent.getStringExtra("data"));
            System.out.println("Reg post: " + data);
            bundle.putString("response",data);
            receiver.send(1, bundle);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // helper method to call this from any controller.
    public static void postData(Context context, ResultReceiver receiver, String json){
        Intent i = new Intent(context, RegService.class);
        i.putExtra("data", json);
        i.putExtra("type", httprequest.POST_CODE);
        i.putExtra("uri", (httprequest.ROOT_ADDRESS + "/reg"));
        i.putExtra("receiver", receiver);
        context.startService(i);
    }
}
