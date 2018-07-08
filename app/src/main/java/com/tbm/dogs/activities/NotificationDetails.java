package com.tbm.dogs.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.tbm.dogs.R;

public class NotificationDetails extends AppCompatActivity {

    TextView tTitle,tBody;

    private void init(){
        tTitle = findViewById(R.id.tTitle);
        tBody = findViewById(R.id.tBody);
    }
    private void initNotification(){
        Intent it = getIntent();
        tTitle.setText(it.getStringExtra("title"));
        tBody.setText(it.getStringExtra("body"));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details);
        init();
        initNotification();
    }
}
