package com.tbm.dogs.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.tbm.dogs.Helper.Var;
import com.tbm.dogs.R;
import com.tbm.dogs.activities.login.Login;

public class LaunchScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_launch_screen);
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(LaunchScreen.this,Login.class);
                LaunchScreen.this.startActivity(mainIntent);
                LaunchScreen.this.finish();
            }
        }, Var.delayLaunchScreen);
    }
}
