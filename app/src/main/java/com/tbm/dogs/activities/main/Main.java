package com.tbm.dogs.activities.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.tbm.dogs.Helper.Shared;
import com.tbm.dogs.Helper.Var;
import com.tbm.dogs.R;
import com.tbm.dogs.activities.ViecDaLam;
import com.tbm.dogs.activities.ViecDangCho;
import com.tbm.dogs.activities.ViecDangLam;
import com.tbm.dogs.activities.viecdangco.ViecDangCo;
import com.tbm.dogs.model.obj.Shiper;

public class Main extends AppCompatActivity implements View.OnClickListener, Results {

    RelativeLayout layoutViecDangCo,layoutViecDangCho,layoutViecDangLam,layoutViecDaLam;
    ImageView imgAvatar;
    TextView tName,tId,tMoney,tAddress;
    Shiper shiper;
    Gson gson;
    Shared shared;
    HandlerP handlerP;

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
        initUser();
        initData();
        layoutViecDangLam.setOnClickListener(this);
        layoutViecDangCo.setOnClickListener(this);
        layoutViecDaLam.setOnClickListener(this);
        layoutViecDangCho.setOnClickListener(this);
    }

    private void initData() {
        tName.setText(shiper.getFullname());
        tId.setText("ID: "+shiper.getHero_id());
        tMoney.setText("Tài Khoản: "+shiper.getBalance());
        tAddress.setText("Địa Chỉ: "+shiper.getAddress());
        Picasso.get().load(shiper.getImage()).into(imgAvatar);
        shared = new Shared(this);
        handlerP = new HandlerP(this);
    }

    void initUser(){
        gson = new Gson();
        shiper = gson.fromJson(getIntent().getStringExtra("user"),Shiper.class);
        Var.shiper = shiper;
        Var.tokenOther = shiper.getToken();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.main);
        init();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        handlerP.onBackPressed();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Toast.makeText(this, intent.getStringExtra("data"), Toast.LENGTH_SHORT).show();
        super.onNewIntent(intent);
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
                startActivity(new Intent(this,ViecDaLam.class));
                break;
            }
            case R.id.layout_viec_dang_cho:{
                startActivity(new Intent(this,ViecDangCho.class));
                break;
            }
            case R.id.layout_viec_dang_co:{
                startActivity(new Intent(this,ViecDangCo.class));
                break;
            }
            case R.id.layout_viec_dang_lam:{
                startActivity(new Intent(this,ViecDangLam.class));
                break;
            }

        }
    }

    @Override
    public void tapAgain() {
        Toast.makeText(this, "Bấm back lần nữa để thoát", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishApp() {
        finish();
    }
}
