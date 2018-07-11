package com.tbm.dogs.Helper

import android.Manifest
import com.tbm.dogs.model.obj.Job

import com.tbm.dogs.model.obj.Shiper

object Var {
    val API = "http://shipx.vn/api/index.php/"//VinterSignin/?token=da&mobile=0945333445&password=123456&phone_os=1";
    val API_SIGNIN = API + "VinterSignin/?"
    val API_SIGNOUT = API + "VinterSignout/?"
    val API_ACCEPT_ORDER = API + "VinterAcceptOrder/?"
    val API_REMOVE_ORDER = API + "VinterRemoveOrder/?"
    val API_CANCEL_ORDER = API + "VinterCancelOrder/?"
    val API_WORKING = API + "VinterWoking/?"
    val API_DENY_ORDER = API + "CustomerDenyOrder/?"
    val API_SUCCESS = API + "OrderSuccess/?"
    val API_GET_ORDERS = API + "VinterGetOrders/?"
    val API_GET_NEW_JOBS = API + "VinterGetNewJobs/?"
    val API_GET_ORDER_DETAILS = API + "VinterGetOrderDetails/?"
    val API_GET_TRANSACTIONS = API + "VinterGetTransactions/?"
    val API_CHANGE_PASSWORD = API + "VinterChangePassword/?"
    val API_REQUEST_OTP = API + "VinterRequestOTP/?"
    val API_CREATE_NEW_PASSWORD = API + "VinterCreateNewPassword/?"
    val API_SEND_GEO_LOCATION = API + "VinterSendGeoLocation/?"
    val API_GET_MESSAGES = API + "VinterGetMessages/?"
    val API_GET_JOBS = API + "VinterGetJobs/?"
    val API_GET_BANK_INFO = API + "VinterGetBankInfo/?"
    val HEADER = "X-API-KEY"
    val delayLaunchScreen = 1000
    val tokenLogin = "8s0wswowcgc4owoc0oc8g00cwok8gkw800k8o08w"
    val MAP_DIRECTION_KEY = "AIzaSyC0vuulZn6ijkilXEGajyWBRA85bB7u3L8"
    val MAP_DIRECTION_URL = "https://maps.googleapis.com/maps/api/directions/json?"
    var tokenOther = ""
    var currentTokenFCM = ""
    var shiper: Shiper? = null
    val PermissionAll = 1
    //    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    //    <uses-permission android:name="android.permission.INTERNET" />
    //    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.GET_TASKS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CALL_PHONE)
    var jobs: ArrayList<Job>? = null
    var jobsWaiting: ArrayList<Job>? = null
    var jobsWorking: ArrayList<Job>? = null
    var jobsDone: ArrayList<Job>? = null
}
