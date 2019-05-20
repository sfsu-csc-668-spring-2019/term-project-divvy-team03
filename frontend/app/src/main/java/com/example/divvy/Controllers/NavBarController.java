package com.example.divvy.Controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;

import com.example.divvy.Controllers.helpers.LoginAuthenticator;
import com.example.divvy.R;

public class NavBarController {

    public static void setUpListners(BottomNavigationView navigation, Context context){
        navigation.setOnNavigationItemSelectedListener(item -> {
            Intent intent = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    intent = new Intent(context, UserProfileViewController.class);
                    intent.putExtra("owner", LoginAuthenticator.getInstance().getUser(context));
                    break;
                case R.id.navigation_dashboard:
                    intent = new Intent(context, CreateListingController.class);
                    break;
                case R.id.navigation_notifications:
                    LoginAuthenticator.getInstance().LogoutUser(context);
                    return true;
                case R.id.search:
                    intent = new Intent(context, SearchController.class);
                    break;
            }
            ((Activity)context).finish();
            context.startActivity(intent);
            return true;
        });
    }
}
