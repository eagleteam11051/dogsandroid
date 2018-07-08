package com.tbm.dogs.Helper;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

public class Action {
    public boolean hasPermissions(Context context, String... permissions){
        if(Build.VERSION.SDK_INT >= 23 && context != null && permissions != null){
            for(String pms:permissions){
                if(ActivityCompat.checkSelfPermission(context,pms) != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }
    public void requestPermission(Activity activity,String[] permissions, int requestCode){
        if(!hasPermissions(activity,permissions)){
            ActivityCompat.requestPermissions(activity,permissions,requestCode);
        }
    }
}
