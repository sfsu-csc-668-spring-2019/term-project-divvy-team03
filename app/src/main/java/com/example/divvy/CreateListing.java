package com.example.divvy;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateListing extends AppCompatActivity {
    private final int REQUEST_CODE_CANCEL = 0;
    private final int REQUEST_CODE_SUBMIT = 1;

    Listing listing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);
        Button submit = findViewById(R.id.create_list_submit);
        Button cancel = findViewById(R.id.create_list_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //just go back
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //make request to send data to backend
                //if successful, go to next page
                //else keep user here
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EditText listName = findViewById(R.id.create_list_name);
        EditText size = findViewById(R.id.create_grp_size);
        EditText desc = findViewById(R.id.create_list_desc);

        data.putExtra("name", listName.getText().toString());
        data.putExtra("size", Integer.parseInt(size.getText().toString()));
        data.putExtra("desc", desc.getText().toString());
    }
}
