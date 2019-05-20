package com.example.divvy.Controllers;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.divvy.Controllers.Services.LoginService;
import com.example.divvy.Controllers.Services.NetworkReceiver;
import com.example.divvy.Controllers.Services.httprequest;
import com.example.divvy.Controllers.helpers.LoginAuthenticator;
import com.example.divvy.R;

import org.json.JSONException;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class UserLoginController extends AppCompatActivity implements NetworkReceiver.DataReceiver {

    private Button btnLogin; //login in button
    private Button btnLinkToRegister;
    private EditText inputUsername;
    private EditText inputPassword;

    private NetworkReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login_view);
        mReceiver = new NetworkReceiver(new Handler(Looper.getMainLooper()),this);
        inputUsername = (EditText) findViewById(R.id.editText_login);
        inputPassword = (EditText) findViewById(R.id.editText_password);

        // Set up the references here.
        btnLogin = findViewById(R.id.button_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AttemptLogin();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btnLinkToRegister = (Button) findViewById(R.id.button_signUp);
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSignUpView();
            }
        });

        // End setting up references here.
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(LoginAuthenticator.getInstance().LoggedIn(this)){
            finish();
            Intent intent = new Intent(this, UserProfileViewController.class);
            intent.putExtra("owner",LoginAuthenticator.getInstance().getUser(this));
            System.out.println("Owner: " + LoginAuthenticator.getInstance().getUser(this));
            startActivity(intent);
        }
    }

    private void switchToSignUpView(){
        Intent intent = new Intent(this, UserSignUpController.class);

        startActivity(intent);
    }

    private void AttemptLogin() throws JSONException {
        String username = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();

        LinkedHashMap<String,String> data = new LinkedHashMap<>();
        data.put("username",username);
        data.put("psw", password);

        System.out.println("Hashmap: " + data.toString());

        LoginService.getData(this, mReceiver, data);
    }


    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        HashMap<String,String> response = (HashMap<String,String>)resultData.getSerializable("response");
        if( resultCode == httprequest.FAIL_CODE){
            // FAILED TO LOG IN
            Toast.makeText(this, "Failed to login", Toast.LENGTH_SHORT).show();
        }else if(resultCode == httprequest.SUCCESS_CODE){
            // SUCCESS
            LoginAuthenticator authenticator = LoginAuthenticator.getInstance();
            authenticator.LogInUser(response.get("username"),this);
            Intent intent = new Intent(this, UserProfileViewController.class);
            intent.putExtra("owner",authenticator.getUser(this));
            intent.putExtra("profImage", response.get("profImage"));
            startActivity(intent);
        }
    }
}
