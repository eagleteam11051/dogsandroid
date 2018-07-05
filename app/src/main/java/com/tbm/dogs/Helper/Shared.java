package com.tbm.dogs.Helper;

import android.content.Context;
import android.content.SharedPreferences;

public class Shared {
    SharedPreferences shared;
    Context context;

    public Shared(Context context) {
        this.context = context;
        shared = context.getSharedPreferences("login",Context.MODE_PRIVATE);
    }
    public void save(String s){
        shared.edit().putString("obj",s).apply();
    }
    public String get(){
        return shared.getString("obj","null");
    }
}
