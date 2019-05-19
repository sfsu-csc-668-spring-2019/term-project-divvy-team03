package com.example.divvy.Controllers;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.divvy.NetworkReceiver;
import com.example.divvy.R;
import com.example.divvy.models.Listing;

import java.util.ArrayList;


public abstract class DisplayListingsController extends AppCompatActivity implements NetworkReceiver.DataReceiver {

    ArrayList<Listing> listings;
    NetworkReceiver mReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listings = new ArrayList<>();
        mReceiver = new NetworkReceiver(new Handler(Looper.getMainLooper()), this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Bundle savedState = new Bundle();
        savedState.putSerializable("data",listings);
        this.onSaveInstanceState(savedState);

    }
    public abstract void UpdateListingsView();

    @Override
    public abstract void onReceiveResult(int resultCode, Bundle resultData);
}
