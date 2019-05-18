package com.example.divvy.Controllers;

import android.media.Rating;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.divvy.R;
import com.example.divvy.models.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class UserProfileActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private TextView usernameView;
    private ImageView image;
    private RatingBar ratingBar;
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
        recyclerView = findViewById(R.id.reviews);
        usernameView = findViewById(R.id.username);
        image = findViewById(R.id.user_image);
        image.setImageDrawable(ImageSelector.LoadImageFromWebOperations("https://www.latimes.com/resizer/LtMM4EEcUqh0cQvysx4WA5nF1n0=/800x0/www.trbimg.com/img-5cb65af2/turbine/la-1555454704-9i89jpnpmo-snap-image"));
        ratingBar = findViewById(R.id.ratingBar);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }
}
