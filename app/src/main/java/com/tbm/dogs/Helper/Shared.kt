package com.tbm.dogs.Helper

import android.content.Context
import android.content.SharedPreferences

class Shared(internal var context: Context) {
    internal var shared: SharedPreferences
    val infoShiper: String
        get() = shared.getString("shiper", "")
    val tokenFCM: String
        get() = shared.getString("tokenfcm", "")

    init {
        shared = context.getSharedPreferences("dogs", Context.MODE_PRIVATE)
    }

    fun saveInfoShiper(s: String) {
        shared.edit().putString("shiper", s).apply()
    }

    fun saveTokenFCM(token: String) {
        shared.edit().putString("tokenfcm", token).apply()
    }
}
