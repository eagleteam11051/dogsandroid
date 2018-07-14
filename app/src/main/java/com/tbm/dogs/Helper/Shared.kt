package com.tbm.dogs.Helper

import android.content.Context
import android.content.SharedPreferences

class Shared(internal var context: Context) {
    internal var shared: SharedPreferences
    val infoShiper: String
        get() = shared.getString("shiper", "")
    val tokenFCM: String
        get() = shared.getString("tokenfcm", "")
    val user:String
        get() = shared.getString("user","")
    val pass:String
        get() = shared.getString("pass","")

    init {
        shared = context.getSharedPreferences("dogs", Context.MODE_PRIVATE)
    }

    fun saveInfoShiper(s: String) {
        shared.edit().putString("shiper", s).apply()
    }

    fun saveUserPass(user:String,pass:String){
        shared.edit().putString("user",user).apply()
        shared.edit().putString("pass",pass).apply()
    }

    fun saveTokenFCM(token: String) {
        shared.edit().putString("tokenfcm", token).apply()
    }
}
