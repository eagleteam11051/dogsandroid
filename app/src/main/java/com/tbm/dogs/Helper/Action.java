package com.tbm.dogs.Helper;

import android.app.Activity;
import android.content.Intent;

public class Action {
    public <T>void start(Activity activity, T target){
        activity.startActivity(new Intent(activity,target.getClass()));
    }
}
