package com.tbm.dogs.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tbm.dogs.Helper.Shared;
import com.tbm.dogs.Helper.Var;
import com.tbm.dogs.R;
import com.tbm.dogs.model.obj.ObjLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Login extends AppCompatActivity {


    TextView tError;
    EditText eUserName,ePassWord;
    Button bLogin;
    TextView tForgot;

    void hideToolBar(){
        getSupportActionBar().hide();
    }
    void actionLogin(){
        boolean b1=false,b2=false;
        if(eUserName.getText().length()<1){
            eUserName.setError("Tên người dùng trống!");
        }else{
            b1 = true;
        }
        if(ePassWord.getText().length()<1){
            ePassWord.setError("Mật khẩu trống!");
        }else{
            b2 = true;
        }
        if(b1 && b2){
            //startActivity(new Intent(this,Main.class));
            new handlerLogin().execute(Var.API_LOGIN);
        }
    }
    void init(){
        hideToolBar();
        tError = findViewById(R.id.tError);
        eUserName = findViewById(R.id.eUserName);
        ePassWord = findViewById(R.id.ePassWord);
        bLogin = findViewById(R.id.bLogin);
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionLogin();
            }
        });
        tForgot = findViewById(R.id.tForgot);
    }
    void handlerResult(String response){
        Log.e("response",response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if(jsonObject.getString("status").equals("success")){
                Gson gson = new Gson();
                String response1 = jsonObject.getString("response");
                ObjLogin objLogin = gson.fromJson(response1,ObjLogin.class);
                Shared shared = new Shared(this);
                shared.save(response1);
                //Log.e("get",shared.get());
                startActivity(new Intent(Login.this,Main.class).putExtra("objLogin",response1));
            }else{
                Toast.makeText(this, "LOI", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();
    }
    public class handlerLogin extends AsyncTask<String,Void,String>{
        OkHttpClient client = new OkHttpClient();
        @Override
        protected String doInBackground(String... strings) {
            Request.Builder builder = new Request.Builder();
            builder.url(strings[0]);
            builder.addHeader("X-API-KEY","8s0wswowcgc4owoc0oc8g00cwok8gkw800k8o08w");
            Request request = builder.build();
            try{
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                Log.e("error_handle_login",e.toString());
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            handlerResult(s);
        }
    }
}
