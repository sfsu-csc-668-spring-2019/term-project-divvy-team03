package com.example.divvy.Controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.divvy.R;
import com.example.divvy.httprequest;

public class UserLoginController extends AppCompatActivity {

    private Button btnLogin; //login in button
    private Button btnLinkToRegister; 
    private EditText inputEmail;
    private EditText inputPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login_view);
        // Set up the references here.
        btnLogin = findViewById(R.id.button_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("INFO","Hello, Button was clicked");

            }
        });


        btnLinkToRegister = (Button) findViewById(R.id.button_signUp);
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.user_signup_view);
            }
        });
        inputEmail = (EditText) findViewById(R.id.editText_login);
        inputPassword = (EditText) findViewById(R.id.editText_password);

        // End setting up references here.
    }

    private boolean submitLoginRequest(){
        // Get the text from the views,
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        httprequest webRequester  = new httprequest();
        //set up to create new web request.



        return false;
    }



}
