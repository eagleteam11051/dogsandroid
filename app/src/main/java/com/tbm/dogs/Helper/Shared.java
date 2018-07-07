package com.tbm.dogs.Helper;

import android.content.Context;
import android.content.SharedPreferences;

public class Shared {
    SharedPreferences shared;
    Context context;

    public Shared(Context context) {
        this.context = context;
        shared = context.getSharedPreferences("dogs",Context.MODE_PRIVATE);
    }
    public void saveInfoUser(String s){
        shared.edit().putString("user",s).apply();
    }
    public String getInfoUser(){
        return shared.getString("user","");
    }
    public void saveTokenFCM(String token){ shared.edit().putString("tokenfcm",token).apply();}
    public String getTokenFCM() { return shared.getString("tokenfcm","");}
}
