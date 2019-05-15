package com.example.divvy.Controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.divvy.models.User;

//SINGLETON
public class LoginAuthenticator {
    private final int LOGGED_IN = 1;
    private final int LOGGED_OUT = 0;
    private String username;
    private int loggedIn;
    private static LoginAuthenticator instance = null;
    private LoginAuthenticator(){
        username = "";
        loggedIn = LOGGED_OUT;
    }

    public static LoginAuthenticator getInstance(){
        if(instance == null){
            instance = new LoginAuthenticator();
        }
        return instance;
    }
    public boolean VerifyLoginState(Context context){
        if(loggedIn == LOGGED_IN){
            return true;
        }else{
            Intent i = new Intent(context, CreateListingController.class);
            context.startActivity(i);
            return false;
        }
    }
    public void LogInUser(User user, Context context){
        username = "loggedin-username"; // change to user id
        loggedIn = LOGGED_IN;
        SharedPreferences.Editor editor =
                context.getSharedPreferences("LoginState",Context.MODE_PRIVATE).edit();
        editor.putInt("loggedIn", loggedIn);
        editor.putString("username", "loggedin user");
    }
}
