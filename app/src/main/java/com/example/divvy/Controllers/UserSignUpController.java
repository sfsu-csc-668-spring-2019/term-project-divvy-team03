package com.example.divvy.Controllers;

import android.support.v7.app.AppCompatActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.divvy.R;
import com.example.divvy.httprequest;

public class UserSignUpController extends AppCompatActivity {
    // Buttons specific to sign up view
    private Button buttonCreateAccount;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextRegisterEmail;
    private EditText editTextMakePassword;
    private Button btnLinkToLoginPage;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_signup_view);
        // Set up the references
        buttonCreateAccount = findViewById(R.id.register_submit_btn);
        editTextFirstName = findViewById(R.id.editText_firstName);
        editTextLastName = findViewById(R.id.editText_lastName);
        editTextRegisterEmail = findViewById(R.id.editText_emailAddress);
        editTextMakePassword = findViewById(R.id.editText_password);
        btnLinkToLoginPage = findViewById(R.id.button_LogInLink);

        // Setup the listeners
        btnLinkToLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("HELLO","Switching view");

            }
        });

        createListeners();
        //

    }

    private void createListeners() {
        btnLinkToLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }
}
