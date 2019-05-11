package com.example.divvy.Controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.divvy.R;
import com.example.divvy.models.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class UserProfileActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private TextView usernameView;

    private String username = "username", listing_id;
    private List reviewsList;
    private RecyclerViewAdapter chatBoxAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        setUpUi();
        reviewsList = new ArrayList<>();
        //username = getIntent().getExtras().getString(MainActivity.USERNAME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setUpUi() {
        recyclerView = findViewById(R.id.chat_box);
        usernameView = findViewById(R.id.listing_name);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


}
