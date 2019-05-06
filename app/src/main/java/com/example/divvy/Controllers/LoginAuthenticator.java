package com.example.divvy.Controllers;

import android.content.Context;
import android.content.Intent;
import com.example.divvy.User;

//SINGLETON
public class LoginAuthenticator {
    private final int LOGGED_IN = 1;
    private final int LOGGED_OUT = 0;
    private int userid;
    private int loggedIn;
    private static LoginAuthenticator instance = null;
    private LoginAuthenticator(){
        userid = -1;
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
            Intent i = new Intent(context, CreateListing.class);
            context.startActivity(i);
            return false;
        }
    }
    public void LogInUser(User user){
        userid = 1; // change to user id
        loggedIn = LOGGED_IN;
    }
}
