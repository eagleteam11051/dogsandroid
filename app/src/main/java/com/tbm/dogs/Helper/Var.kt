package com.tbm.dogs.Helper

import android.Manifest
import com.tbm.dogs.model.obj.Job

import com.tbm.dogs.model.obj.Shiper

object Var {
    private const val API = "https://shipx.vn/api/index.php/"//VinterSignin/?token=da&mobile=0945333445&password=123456&phone_os=1";
    const val API_SIGNIN = API + "VinterSignin/?"
    const val API_SIGNOUT = API + "VinterSignout/?"
    const val API_ACCEPT_ORDER = API + "VinterAcceptOrder/?"
    const val API_REMOVE_ORDER = API + "VinterRemoveOrder/?"
    const val API_CANCEL_ORDER = API + "VinterCancelOrder/?"
    const val API_WORKING = API + "VinterWoking/?"
    const val API_DENY_ORDER = API + "CustomerDenyOrder/?"
    const val API_SUCCESS = API + "OrderSuccess/?"
    const val API_GET_ORDERS = API + "VinterGetOrders/?"
    const val API_VINTER_GET_WORKING = API + "VinterGetWorking/?"
    const val API_GET_NEW_JOBS = API + "VinterGetNewJobs/?"
    const val API_GET_ORDER_DETAILS = API + "VinterGetOrderDetails/?"
    const val API_GET_TRANSACTIONS = API + "VinterGetTransactions/?"
    const val API_CHANGE_PASSWORD = API + "VinterChangePassword/?"
    const val API_REQUEST_OTP = API + "VinterRequestOTP/?"
    const val API_CREATE_NEW_PASSWORD = API + "VinterCreateNewPassword/?"
    const val API_SEND_GEO_LOCATION = API + "VinterSendGeoLocation/?"
    const val API_GET_MESSAGES = API + "VinterGetMessages/?"
    const val API_GET_JOBS = API + "VinterGetJobs/?"
    const val API_GET_BANK_INFO = API + "VinterGetBankInfo/?"
    const val HEADER = "X-API-KEY"
    const val delayLaunchScreen = 1000
    const val delayReloadJob:Long = 60000
    var lat = ""
    var lng = ""
    const val tokenLogin = "8s0wswowcgc4owoc0oc8g00cwok8gkw800k8o08w"
    const val MAP_DIRECTION_KEY = "AIzaSyC0vuulZn6ijkilXEGajyWBRA85bB7u3L8"
    const val MAP_DIRECTION_URL = "https://maps.googleapis.com/maps/api/directions/json?"
    var tokenOther = ""
    var currentTokenFCM = ""
    var shiper: Shiper? = null
    const val PermissionAll = 1
    val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.GET_TASKS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_PHONE_STATE)
    var jobs: ArrayList<Job>? = null
    var jobsWaiting: ArrayList<Job>? = null
    var jobsWorking: ArrayList<Job>? = null
    var jobsDone: ArrayList<Job>? = null
}
