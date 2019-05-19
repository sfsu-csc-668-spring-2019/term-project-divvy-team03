package com.example.divvy.Controllers.helpers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.divvy.Controllers.UserLoginController;

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
    public void LogoutUser(Context context){
        username = "";
        loggedIn = LOGGED_OUT;
        SharedPreferences.Editor editor =
                context.getSharedPreferences("LoginState",Context.MODE_PRIVATE).edit();
        editor.putInt("loggedIn", LOGGED_OUT);
        editor.putString("username", username);
        editor.apply();
        Intent i = new Intent(context, UserLoginController.class);
        context.startActivity(i);
    }
    public void LogInUser(String user, Context context){
        loggedIn = LOGGED_IN;
        SharedPreferences.Editor editor =
                context.getSharedPreferences("LoginState",Context.MODE_PRIVATE).edit();
        editor.putInt("loggedIn", loggedIn);
        editor.putString("username", user);
        editor.apply();
    }
    public String getUser(Context context){
     SharedPreferences pref = context.getSharedPreferences("LoginState",Context.MODE_PRIVATE);
        return pref.getString("username","");
    }
    public boolean LoggedIn(Context context){
        SharedPreferences pref = context.getSharedPreferences("LoginState",Context.MODE_PRIVATE);
        return !pref.getString("username","").equals("");
    }
}
