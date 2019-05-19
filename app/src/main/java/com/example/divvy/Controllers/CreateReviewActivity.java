package com.example.divvy.Controllers;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.divvy.CreateRatingService;
import com.example.divvy.R;
import com.example.divvy.models.Review;

import java.text.SimpleDateFormat;
import java.util.Date;


public class CreateReviewActivity extends AppCompatActivity{

    private EditText comment_text_field;
    private Button sendButton;
    private TextView review_title;
    private RatingBar rating_bar;
    private SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
    private String username = "reviewed", currentUser = "reviewer";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_review);
        setUpUi();
        setUpListeners();
        //username = getIntent().getExtras().getString(MainActivity.USERNAME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setUpUi() {
        username = getIntent().getExtras().getString("username");
        comment_text_field = findViewById(R.id.comment);
        sendButton = findViewById(R.id.confirm_button);
        review_title = findViewById(R.id.review_title);
        rating_bar = findViewById(R.id.rating_bar);
        review_title.setText("Create Review for " + username );
        NavBarController.setUpListners(findViewById(R.id.navigation), this);
    }

    private void setUpListeners() {
        sendButton.setOnClickListener(view -> {
            Review review = new Review(0,
                    rating_bar.getRating(),
                    currentUser,
                    comment_text_field.getText().toString(),
                    username,
                    format.format(new Date()));
            CreateRatingService.createReviewByUsername(this, review);
            finish();
        });

    }



}
