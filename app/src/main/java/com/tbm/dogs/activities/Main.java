package com.tbm.dogs.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.tbm.dogs.R;
import com.tbm.dogs.model.obj.ObjLogin;

import java.util.ArrayList;

public class Main extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout layoutViecDangCo,layoutViecDangCho,layoutViecDangLam,layoutViecDaLam;
    ImageView imgAvatar;
    TextView tName,tId,tMoney,tAddress;
    ObjLogin objLogin;
    Gson gson;

    void init(){
        layoutViecDaLam = findViewById(R.id.layout_viec_da_lam);
        layoutViecDangCho = findViewById(R.id.layout_viec_dang_cho);
        layoutViecDangCo = findViewById(R.id.layout_viec_dang_co);
        layoutViecDangLam = findViewById(R.id.layout_viec_dang_lam);
        imgAvatar = findViewById(R.id.imgAvatar);
        tName = findViewById(R.id.tName);
        tId = findViewById(R.id.tId);
        tMoney = findViewById(R.id.tMoney);
        tAddress = findViewById(R.id.tAddress);
        initObjLogin();
        initData();
        layoutViecDangLam.setOnClickListener(this);
        layoutViecDangCo.setOnClickListener(this);
        layoutViecDaLam.setOnClickListener(this);
        layoutViecDangCho.setOnClickListener(this);
    }

    private void initData() {
        tName.setText(objLogin.getFullname());
        tId.setText("ID: "+objLogin.getHero_id());
        tMoney.setText("Tài Khoản: "+objLogin.getBalance());
        tAddress.setText("Địa Chỉ: "+objLogin.getAddress());
        Picasso.get().load(objLogin.getImage()).into(imgAvatar);
    }

    void initObjLogin(){
        gson = new Gson();
        objLogin = gson.fromJson(getIntent().getStringExtra("objLogin"),ObjLogin.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.main);
        init();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layout_viec_da_lam:{
                break;
            }
            case R.id.layout_viec_dang_cho:{
                break;
            }
            case R.id.layout_viec_dang_co:{
                startActivity(new Intent(this,ViecDangCo.class));
                break;
            }
            case R.id.layout_viec_dang_lam:{
                break;
            }

        }
    }
}
