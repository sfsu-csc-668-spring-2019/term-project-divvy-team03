package com.example.divvy.Controllers;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.divvy.Controllers.Services.GetRatingService;
import com.example.divvy.Controllers.Services.NetworkReceiver;
import com.example.divvy.Controllers.Services.httprequest;
import com.example.divvy.Controllers.helpers.LoginAuthenticator;
import com.example.divvy.R;
import com.example.divvy.Controllers.helpers.RecyclerViewAdapter;
import com.example.divvy.models.Review;

import java.util.ArrayList;
import java.util.List;


public class UserProfileViewController extends AppCompatActivity implements NetworkReceiver.DataReceiver{

    private RecyclerView recyclerView;
    private TextView usernameView;
    private ImageView image;
    private RatingBar ratingBar;
    private Button profile_button;
    private BottomNavigationView navigation;

    private String owner;
    private List<Review> reviewsList;
    private RecyclerViewAdapter reviewAdapter;
    private NetworkReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        reviewsList = new ArrayList<>();
        mReceiver = new NetworkReceiver(new Handler(Looper.getMainLooper()), this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        owner = getIntent().getStringExtra("owner");
        setUpUi();
        setUpListeners();
        GetRatingService.GetReviewsByUsername(this, mReceiver, owner);
    }

    private void setUpUi() {
        recyclerView = findViewById(R.id.reviews);
        usernameView = findViewById(R.id.username);
        usernameView.setText(owner);
        image = findViewById(R.id.user_image);
        profile_button = findViewById(R.id.profile_button);
        if(owner.equals(LoginAuthenticator.getInstance().getUser(this))){
            profile_button.setText("View My Listings");
        }else{
            profile_button.setText("Leave A Review");
        }
        navigation = findViewById(R.id.navigation);
        AsyncTask i = new ImageSelect.ImageRetrieverTask(image);
        Object[] images = {httprequest.ROOT_ADDRESS + "/" +owner + ".png"};
        i.execute(images);
        ratingBar = findViewById(R.id.ratingBar);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private void setUpListeners(){

        profile_button.setOnClickListener(view -> {
            Intent intent;
            if(owner.equals(LoginAuthenticator.getInstance().getUser(this))){
                intent = new Intent(this,MyListingsController.class);
            }else {
                intent = new Intent(this, CreateReviewController.class);
                intent.putExtra("targetUser", owner);
            }
            startActivity(intent);
        });

        NavBarController.setUpListners(navigation, this);
    }



    
    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        reviewsList = (ArrayList<Review>)resultData.getSerializable("data");
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(reviewsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        setAverageRating();
    }

    public void setAverageRating(){
        float total = 0;
        for(Review review: reviewsList){
            total += review.getRating();
        }
        ratingBar.setRating(total/reviewsList.size());
    }



}
