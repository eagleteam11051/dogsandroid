package com.tbm.dogs.activities.login

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.tbm.dogs.Helper.Var
import com.tbm.dogs.Helper.VolleyHelper
import org.json.JSONException
import org.json.JSONObject

class HandlerP(internal var results: Results,val context: Context) {
    private val volley = VolleyHelper(context)

    fun actionLogin(userName:String,passWord:String){
        results.showDialog()
        handlerLogin(Var.API_SIGNIN,userName,passWord, Var.currentTokenFCM,"1")
    }

    fun actionLogin(eUserName: EditText, ePassWord: EditText) {
        var b1 = false
        var b2 = false
        if (eUserName.text.isEmpty()) {
            eUserName.error = "Tên người dùng trống!"
        } else {
            b1 = true
        }
        if (ePassWord.text.isEmpty()) {
            ePassWord.error = "Mật khẩu trống!"
        } else {
            b2 = true
        }
        if (b1 && b2) {
            results.showDialog()
            handlerLogin(Var.API_SIGNIN,eUserName.text.toString(),ePassWord.text.toString(),Var.currentTokenFCM,"1")
        }
    }

    private fun handlerLogin(url:String,mobile:String,password:String,token:String,phone_os:String){
        volley.requestAPI(
                url,
                HashMap<String,String>().apply {
                    put("X-API-KEY",Var.tokenLogin)
                },
                HashMap<String,String>().apply {
                    put("mobile",mobile)
                    put("password",password)
                    put("token",token)
                    put("phone_os",phone_os)
                },
                {response -> success(response) },
                {error ->
                    Log.e("loginError:",error)
                    results.dismisDialog()
                    results.showErrorInternet()
                })
    }

    private fun success(s: String?){
        results.dismisDialog()
        if (s == null) {
            results.showConnectError()
        } else {
            handlerResult(s)
        }
    }

    private fun handlerResult(response: String) {
        Log.e("response", response)
        try {
            val jsonObject = JSONObject(response)
            if (jsonObject.getString("status") == "success") {
                val response1 = jsonObject.getString("response")
                results.saveInfoUser(response1)
                val b = Bundle()
                b.putString("shiper", response1)
                results.startMain(b)
            } else {
                results.showError()
            }
        } catch (e: JSONException) {
            Log.e("whenLogin:",e.toString())
        }

    }
}
