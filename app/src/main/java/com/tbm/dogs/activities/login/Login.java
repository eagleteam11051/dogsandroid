package com.tbm.dogs.activities.login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tbm.dogs.Helper.Action;
import com.tbm.dogs.Helper.Shared;
import com.tbm.dogs.Helper.Var;
import com.tbm.dogs.R;
import com.tbm.dogs.activities.main.Main;

public class Login extends AppCompatActivity implements Results{


    EditText eUserName,ePassWord;
    Button bLogin;
    TextView tForgot;
    Shared shared;
    HandlerP handlerP;
    Action action;
    ProgressDialog progressDialog;

    void init(){

        eUserName = findViewById(R.id.eUserName);
        ePassWord = findViewById(R.id.ePassWord);
        bLogin = findViewById(R.id.bLogin);
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlerP.actionLogin(eUserName,ePassWord);
            }
        });
        tForgot = findViewById(R.id.tForgot);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang xử lý, xin chờ giây lát!");
        progressDialog.setCancelable(false);
        shared = new Shared(this);
        handlerP = new HandlerP(this);
        action = new Action();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.login);
        init();
        action.requestPermission(this,Var.permissions,Var.PermissionAll);
        Var.currentTokenFCM = shared.getTokenFCM();
        Log.e("currentToken:",Var.currentTokenFCM);
    }

    @Override
    public void saveInfoUser(String response1) {
        shared.saveInfoUser(response1);
    }

    @Override
    public void startMain(Bundle user) {
        startActivity(new Intent(this, Main.class).putExtras(user));
        finish();
    }

    @Override
    public void showError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Lỗi!");
        builder.setMessage("Số điện thoại hoặc mật khẩu của bạn không chính xác!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.create().show();
    }

    @Override
    public void dismisDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void showDialog() {
        progressDialog.show();
    }

    @Override
    public void showConnectError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Lỗi!");
        builder.setMessage("Vui lòng kiểm tra kết nối mạng của bạn và thử lại!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.create().show();
    }
}
