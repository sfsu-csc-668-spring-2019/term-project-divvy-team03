package com.example.divvy.Controllers;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.divvy.CreateRatingService;
import com.example.divvy.NetworkReceiver;
import com.example.divvy.R;
import com.example.divvy.httprequest;
import com.example.divvy.models.Review;

import java.text.SimpleDateFormat;
import java.util.Date;


public class CreateReviewActivity extends AppCompatActivity implements NetworkReceiver.DataReceiver {

    private EditText comment_text_field;
    private Button sendButton;
    private TextView review_title;
    private RatingBar rating_bar;
    private SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
    private String targetUser, currentUser;
    private NetworkReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_review);
        mReceiver = new NetworkReceiver(null, this);
        setUpUi();
        setUpListeners();
        //username = getIntent().getExtras().getString(MainActivity.USERNAME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setUpUi() {
        targetUser = getIntent().getStringExtra("targetUser");
        currentUser = LoginAuthenticator.getInstance().getUser(this);
        comment_text_field = findViewById(R.id.comment);
        sendButton = findViewById(R.id.confirm_button);
        review_title = findViewById(R.id.review_title);
        rating_bar = findViewById(R.id.rating_bar);
        review_title.setText("Create Review for " + targetUser );
        NavBarController.setUpListners(findViewById(R.id.navigation), this);
    }

    private void setUpListeners() {
        sendButton.setOnClickListener(view -> {
            Review review = new Review(0,
                    rating_bar.getRating(),
                    currentUser,
                    comment_text_field.getText().toString(),
                    targetUser,
                    format.format(new Date()));
            if(validateForm()){
                review.postData(this,mReceiver);
            }else{
                Toast.makeText(this,"Please choose a rating and leave a comment",Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        if(resultCode == httprequest.SUCCESS_CODE){
            finish();
        }else if(resultCode == httprequest.FAIL_CODE){
            Toast.makeText(this, "Failed to create review", Toast.LENGTH_LONG).show();
        }
    }
    public boolean validateForm(){
        if(rating_bar.getRating() == 0 || comment_text_field.getText().toString().equals("")){
            return false;
        }
        return true;
    }

}
