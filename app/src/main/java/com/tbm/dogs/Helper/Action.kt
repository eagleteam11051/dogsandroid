package com.tbm.dogs.Helper

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat

class Action {
    fun hasPermissions(context: Context?, vararg permissions: String): Boolean {
        if (Build.VERSION.SDK_INT >= 23 && context != null && permissions != null) {
            for (pms in permissions) {
                if (ActivityCompat.checkSelfPermission(context, pms) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
        }
        return true
    }

    fun requestPermission(activity: Activity, permissions: Array<String>, requestCode: Int) {
        if (!hasPermissions(activity, *permissions)) {
            ActivityCompat.requestPermissions(activity, permissions, requestCode)
        }
    }
}
