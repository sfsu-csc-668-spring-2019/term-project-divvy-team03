package com.example.divvy.Controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import com.example.divvy.GetListingsService;
import com.example.divvy.R;
import com.example.divvy.models.RecyclerViewAdapter;
import com.google.gson.Gson;
import org.json.JSONException;

public class SearchController extends DisplayListingsController {

    public static final String SEARCH_RESULTS = "search_results";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_controller);
        EditText queryView = findViewById(R.id.query_editText);
        Button searchButton = findViewById(R.id.btn_search);
        NavBarController.setUpListners(findViewById(R.id.navigation), this);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = queryView.getText().toString();
                System.out.println(query);
                GetListingsService.GetListingsBySearch(SearchController.this,mReceiver,query);
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(SEARCH_RESULTS,MODE_PRIVATE);
        if(sharedPreferences.contains(SEARCH_RESULTS)){
           String search_results = sharedPreferences.getString(SEARCH_RESULTS,"");
            try {
               listings = GetListingsService.convertDataToListings(search_results);
               System.out.println("retreived listings" + search_results);
               System.out.println("listings size:" + listings.size());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        RecyclerView recyclerView = findViewById(R.id.listings_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(listings);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = getSharedPreferences(SEARCH_RESULTS, MODE_PRIVATE).edit();
        String listingsJSONString = new Gson().toJson(listings);
        editor.putString(SEARCH_RESULTS,listingsJSONString);
        System.out.println("sharedPref : " + getSharedPreferences(SEARCH_RESULTS,MODE_PRIVATE).getString(SEARCH_RESULTS,""));
        editor.apply();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putSerializable("data", listings);
    }
}
