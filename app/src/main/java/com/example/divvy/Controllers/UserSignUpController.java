package com.example.divvy.Controllers;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.divvy.NetworkReceiver;
import com.example.divvy.R;
import com.example.divvy.RegService;
import com.example.divvy.httprequest;
import com.example.divvy.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class UserSignUpController extends AppCompatActivity implements NetworkReceiver.DataReceiver {
    // Buttons specific to sign up view
    private final String rootUrl = "ec2-34-226-139-149.compute-1.amazonaws.com";
    //
    private Button btnCreateAccount;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextUserName;
    private EditText editTextRegisterEmail;
    private EditText editTextMakePassword;
    private Button btnLinkToLoginPage;
    //

    private NetworkReceiver mReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_signup_view);
        // Set up the references
        btnCreateAccount = findViewById(R.id.button_signUp);
        editTextFirstName = findViewById(R.id.editText_firstName);
        editTextLastName = findViewById(R.id.editText_lastName);
        editTextRegisterEmail = findViewById(R.id.editText_emailAddress);
        editTextMakePassword = findViewById(R.id.editText_makePassword);
        btnLinkToLoginPage = findViewById(R.id.button_LogInLink);
        editTextUserName = findViewById(R.id.editText_UserName);

        mReceiver = new NetworkReceiver(null,this);

        createListeners();
        //

    }
    private boolean attemptToSignUp(JSONObject toSendJson){
        httprequest networkCall = new httprequest();

        try
        {
            String responseBody = networkCall.post("reg",toSendJson.toString());
            if(!responseBody.isEmpty()){
                Log.i("Success",responseBody);
                return true;
            }else{
                return false;
            }

        }catch (IOException e){
            Toast.makeText(getApplicationContext(),"Something went wrong there!", Toast.LENGTH_SHORT).show();

            return false;
        }


    }
    /*
        Creates a user object from the fields. However, it has NO checking so be careful calling it.
     */
    private User createNewUserObject(){
        User createdUser = new User();
        createdUser.setUserFirstName(editTextFirstName.getText().toString());
        createdUser.setUserLastName(editTextLastName.getText().toString());
        createdUser.setUserEmail(editTextRegisterEmail.getText().toString());
        createdUser.setUserName(editTextUserName.getText().toString());
        createdUser.setUserCity("");
        return createdUser;
    }
    public JSONObject createSignUpJSON(User newUser){
        JSONObject json = new JSONObject();
        if(validateForm()){
            try{
                json.put("username", newUser.getUserName());
                json.put("email", newUser.getEmailAddress());
                json.put("password", editTextMakePassword.getText().toString());
                json.put("first_name", newUser.getUserFirstName());
                json.put("last_name", newUser.getUserLastName());
                json.put("city", newUser.getUserCity());
                json.put("descr", "");

                return json;
            }catch(JSONException e){
                Log.d("ERROR: ", e.toString());
            }
        }

        return null;
    }
    /*
        This checks to make sure the fields are correct before even attempting to create a new user. It
        returns false if any of them have issues.
     */
    private boolean validateForm(){
        //First check email

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (!editTextRegisterEmail.getText().toString().matches(emailPattern))
        {
            Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(editTextUserName.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Invalid user name", Toast.LENGTH_SHORT).show();
            return false;
        } else if(editTextFirstName.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Invalid first name", Toast.LENGTH_SHORT).show();
            return false;
        } else if(editTextLastName.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Invalid last name", Toast.LENGTH_SHORT).show();
            return false;
        } else if(editTextMakePassword.getText().toString().length()<3){
            Toast.makeText(getApplicationContext(),"Please choose a password at least 3 letters.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }

    }
    /*
        Here we have the listeners for the buttons. Please do not create any actual functions here. Just call them.
     */
    private void createListeners() {
        // Setup the listeners
        btnLinkToLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

       btnCreateAccount.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v) {

               signUpButtonHander();


           }
       });
    }
    private void signUpButtonHander(){
        if(validateForm()){
            User createdUser = createNewUserObject();
            JSONObject createdJson = createSignUpJSON(createdUser);
            RegService.postData(this,mReceiver,createdJson.toString());
            //if(attemptToSignUp(createdJson)){
            //    Toast.makeText(getApplicationContext(),"Success?!.", Toast.LENGTH_SHORT).show();

           // }
        }

    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        System.out.println("Reg result: " + resultData.getString("data"));
    }
}
