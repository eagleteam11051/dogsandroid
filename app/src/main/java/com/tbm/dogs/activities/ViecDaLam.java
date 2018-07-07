package com.tbm.dogs.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tbm.dogs.R;

public class ViecDaLam extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_viec_da_lam);
    }
}
