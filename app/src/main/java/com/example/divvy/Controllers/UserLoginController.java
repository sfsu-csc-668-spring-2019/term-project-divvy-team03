package com.example.divvy.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.divvy.LoginService;
import com.example.divvy.NetworkReceiver;
import com.example.divvy.R;
import com.example.divvy.httprequest;

import org.json.JSONException;
import org.json.JSONObject;

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
        mReceiver = new NetworkReceiver(null,this);
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

    private boolean submitLoginRequest(){
        // Get the text from the views,
        String username = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();

        return false;
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
        String response = resultData.getString("response");
        if( response != null && response.equals("FAIL")){
            // FAILED TO LOG IN
        }else{
            // SUCCESS
            LoginAuthenticator authenticator = LoginAuthenticator.getInstance();
            authenticator.LogInUser(response,this);
            Intent intent = new Intent(this,MyListingsController.class);
            System.out.println("logged in" + authenticator.getUser(this));
            intent.putExtra("username",authenticator.getUser(this));
            startActivity(intent);
        }
    }
}
