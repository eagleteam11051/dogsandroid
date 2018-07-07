package com.tbm.dogs.activities.login;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tbm.dogs.Helper.Var;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HandlerP {
    Results results;
    public HandlerP(Results results){
        this.results = results;
    }

    public void actionLogin(EditText eUserName, EditText ePassWord) {
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
            new handlerLogin().execute(Var.API_SIGNIN,"0945333445"/*eUserName.getText().toString()*/,"123456"/*ePassWord.getText().toString()*/,Var.currentTokenFCM);
        }
    }
    public class handlerLogin extends AsyncTask<String,Void,String> {
        OkHttpClient client = new OkHttpClient();
        @Override
        protected String doInBackground(String... strings) {
            Uri buildURI = Uri.parse(strings[0])
                    .buildUpon()
                    .appendQueryParameter("mobile",strings[1])
                    .appendQueryParameter("password",strings[2])
                    .appendQueryParameter("token",strings[3])
                    .appendQueryParameter("phone_os","1")
                    .build();
            Log.e("URI",buildURI.toString());
            URL url = null;
            try {
                url = new URL(buildURI.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Request.Builder builder = new Request.Builder();
            builder.url(url);
            builder.addHeader("X-API-KEY",Var.tokenLogin);
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
    private void handlerResult(String response){
        Log.e("response",response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if(jsonObject.getString("status").equals("success")){
                Gson gson = new Gson();
                String response1 = jsonObject.getString("response");
                results.saveInfoUser(response1);
                Bundle b = new Bundle();
                b.putString("user",response1);
                results.startMain(b);
            }else{
                results.showError("Đăng nhập bị lỗi, hãy kiểm tra lại!",Toast.LENGTH_LONG);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
