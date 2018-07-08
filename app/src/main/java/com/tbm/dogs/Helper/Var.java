package com.tbm.dogs.Helper;

import android.Manifest;

import com.tbm.dogs.model.obj.Shiper;

public class Var {
    public static final String API = "http://shipx.vn/api/index.php/";//VinterSignin/?token=da&mobile=0945333445&password=123456&phone_os=1";
    public static final String API_SIGNIN = API+"VinterSignin/?";
    public static final String API_SIGNOUT = API+"VinterSignout/?";
    public static final String API_ACCEPT_ORDER = API+"VinterAcceptOrder/?";
    public static final String API_REMOVE_ORDER = API+"VinterRemoveOrder/?";
    public static final String API_CANCEL_ORDER = API+"VinterCancelOrder/?";
    public static final String API_WORKING = API+"VinterWoking/?";
    public static final String API_DENY_ORDER = API+"CustomerDenyOrder/?";
    public static final String API_SUCCESS = API+"OrderSuccess/?";
    public static final String API_GET_ORDERS = API+"VinterGetOrders/?";
    public static final String API_GET_ORDER_DETAILS = API+"VinterGetOrderDetails/?";
    public static final String API_GET_TRANSACTIONS = API+"VinterGetTransactions/?";
    public static final String API_CHANGE_PASSWORD = API+"VinterChangePassword/?";
    public static final String API_REQUEST_OTP = API+"VinterRequestOTP/?";
    public static final String API_CREATE_NEW_PASSWORD = API+"VinterCreateNewPassword/?";
    public static final String API_SEND_GEO_LOCATION = API+"VinterSendGeoLocation/?";
    public static final String API_GET_MESSAGES = API+"VinterGetMessages/?";
    public static final String API_GET_JOBS = API+"VinterGetJobs/?";
    public static final String API_GET_BANK_INFO = API+"VinterGetBankInfo/?";
    public static final String HEADER = "X-API-KEY";
    public static final int delayLaunchScreen = 1000;
    public static final String tokenLogin = "8s0wswowcgc4owoc0oc8g00cwok8gkw800k8o08w";
    public static String tokenOther = "";
    public static String currentTokenFCM = "";
    public static Shiper shiper = null;
    public static final int PermissionAll = 1;
//    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
//    <uses-permission android:name="android.permission.INTERNET" />
//    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    public static final String[] permissions = {
        Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.INTERNET,Manifest.permission.ACCESS_NETWORK_STATE
    };
}
