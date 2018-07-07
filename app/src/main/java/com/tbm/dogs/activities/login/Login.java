package com.tbm.dogs.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tbm.dogs.Helper.Shared;
import com.tbm.dogs.Helper.Var;
import com.tbm.dogs.R;
import com.tbm.dogs.activities.main.Main;

public class Login extends AppCompatActivity implements Results{


    TextView tError;
    EditText eUserName,ePassWord;
    Button bLogin;
    TextView tForgot;
    Shared shared;
    HandlerP handlerP;

    void init(){

        tError = findViewById(R.id.tError);
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
        shared = new Shared(this);
        handlerP = new HandlerP(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.login);
        init();
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
    public void showError(String s, int lengthLong) {
        Toast.makeText(this, s, lengthLong).show();
    }
}
