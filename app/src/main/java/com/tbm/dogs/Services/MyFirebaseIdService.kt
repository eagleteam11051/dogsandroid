package com.tbm.dogs.Services

import android.util.Log

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import com.tbm.dogs.Helper.Shared
import com.tbm.dogs.Helper.Var

class MyFirebaseIdService : FirebaseInstanceIdService() {
    override fun onTokenRefresh() {
        super.onTokenRefresh()
        val shared = Shared(this)
        shared.saveTokenFCM(FirebaseInstanceId.getInstance().token!!)
        Var.currentTokenFCM = shared.tokenFCM
        Log.e("currentToken:", Var.currentTokenFCM)
    }
}
