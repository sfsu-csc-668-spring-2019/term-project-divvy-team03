package com.example.divvy.Controllers;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.divvy.NetworkReceiver;
import com.example.divvy.R;
import com.example.divvy.models.Listing;
import java.util.ArrayList;

public class DisplayListingsActivity extends AppCompatActivity implements NetworkReceiver.GetListingReceiver {

    public DisplayListingsActivity(){

    }
    ArrayList<Listing> listings;
    NetworkReceiver mReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout .listings_view);
        if(intent.getSerializableExtra("data") != null) {
            listings = (ArrayList<Listing>) intent.getSerializableExtra("data");
        } else if(savedInstanceState != null){
            listings = (ArrayList<Listing>)savedInstanceState.getSerializable("data");
        }
        mReceiver = new NetworkReceiver(new Handler(Looper.getMainLooper()), this);
        ListingListAdapter adapter = new ListingListAdapter(listings);

        RecyclerView recyclerView = findViewById(R.id.listings_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Bundle savedState = new Bundle();
        savedState.putSerializable("data",listings);
        this.onSaveInstanceState(savedState);

    }
    public void UpdateListingsView(){
        ListingListAdapter adapter = new ListingListAdapter(listings);

        RecyclerView recyclerView = findViewById(R.id.listings_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        listings = (ArrayList<Listing>)resultData.getSerializable("data");
        UpdateListingsView();
    }
}
