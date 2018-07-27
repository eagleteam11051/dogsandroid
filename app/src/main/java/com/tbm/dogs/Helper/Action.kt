package com.tbm.dogs.Helper

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat.getSystemService
import android.util.Log
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesUtil
import java.net.InetAddress


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

    fun checkServices(activity: Activity): Boolean {
        // Check status of Google Play Services
        val status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity)

        // Check Google Play Service Available
        try {
            if (status != ConnectionResult.SUCCESS) {
                GooglePlayServicesUtil.getErrorDialog(status, activity, 1).show()
                return false
            } else {
                return true
            }
        } catch (e: Exception) {
            Log.e("GooglePlayServiceUtil:", e.toString())
            return false
        }

    }

    fun isNetworkConnected(context: Context): Boolean {
        val cm: ConnectivityManager? = getSystemService(context, ConnectivityManager::class.java)//getSystemService(Context.CONNECTIVITY_SERVICE)

        return cm!!.activeNetworkInfo != null
    }

    fun isInternetAvailable(response:(available:Boolean)->Unit){
        val thread = Thread(Runnable {
            try {
                val ipAddr = InetAddress.getByName("www.google.com")
                //You can replace it with your name
                //response(!ipAddr.equals(""))
                Log.e("available",(!ipAddr.equals("")).toString())
            } catch (e: Exception) {
                Log.e("checkInternet:", e.toString())
                //response(false)
                Log.e("available",false.toString())
            }
        })
        thread.start()

    }
}
