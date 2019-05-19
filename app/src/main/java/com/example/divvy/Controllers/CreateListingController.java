package com.example.divvy.Controllers;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.divvy.NetworkReceiver;
import com.example.divvy.httprequest;
import com.example.divvy.models.Listing;
import com.example.divvy.R;

public class CreateListingController extends AppCompatActivity implements NetworkReceiver.DataReceiver {

    Listing listing;
    NetworkReceiver mReceiver;
    EditText title;
    EditText desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);
        mReceiver = new NetworkReceiver(null,this);
        Button submit = findViewById(R.id.create_list_submit);
        Button cancel = findViewById(R.id.create_list_cancel);
        NavBarController.setUpListners(findViewById(R.id.navigation), this);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //just go back
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //closes keyboard
                try {
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                } catch (Exception e) {
                    // keyboard wasnt open
                }

                title = findViewById(R.id.create_list_name);
                desc = findViewById(R.id.create_list_desc);
                listing = new Listing(
                        title.getText().toString(),
                        desc.getText().toString(),
                        "alex",
                        1,
                        01);
                if(validateForm()) {
                    listing.postData(CreateListingController.this, mReceiver);
                }else{
                    Toast.makeText(CreateListingController.this, "Please enter a valid title and description", Toast.LENGTH_SHORT).show();
                }
                //if successful, go to next page
                //else keep user here
            }
        });
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        if(resultCode == httprequest.SUCCESS_CODE){
            Intent intent = new Intent(this, MyListingsController.class);
            startActivity(intent);
        }else{
            Toast.makeText(this,"Failed to create listing", Toast.LENGTH_LONG).show();
        }
    }
    private boolean validateForm(){
        if(desc.getText().toString().length() < 20|| title.getText().toString().length() < 4){
            return false;
        }
        return true;
    }
}
