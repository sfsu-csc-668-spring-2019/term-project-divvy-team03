package com.example.divvy.Controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.divvy.R;

public class UserLoginController extends AppCompatActivity {

    private Button btnLogin; //login in button
    private Button btnLinkToRegister; 
    private EditText inputEmail;
    private EditText inputPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login_view);
    }
}
