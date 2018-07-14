package com.tbm.dogs.activities.login

import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.google.gson.Gson
import com.tbm.dogs.Helper.Var
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

class HandlerP(internal var results: Results) {

    fun actionLogin(userName:String,passWord:String){
        results.showDialog()
        handlerLogin().execute(Var.API_SIGNIN,userName,passWord, Var.currentTokenFCM)
    }

    fun actionLogin(eUserName: EditText, ePassWord: EditText) {
        var b1 = false
        var b2 = false
        if (eUserName.text.length < 1) {
            eUserName.error = "Tên người dùng trống!"
        } else {
            b1 = true
        }
        if (ePassWord.text.length < 1) {
            ePassWord.error = "Mật khẩu trống!"
        } else {
            b2 = true
        }
        if (b1 && b2) {
            results.showDialog()
            handlerLogin().execute(Var.API_SIGNIN, "0945333445"/*eUserName.getText().toString()*/, "123456"/*ePassWord.getText().toString()*/, Var.currentTokenFCM)
        }
    }

    inner class handlerLogin : AsyncTask<String, Void, String>() {
        internal var client = OkHttpClient()
        override fun doInBackground(vararg strings: String): String? {
            val buildURI = Uri.parse(strings[0])
                    .buildUpon()
                    .appendQueryParameter("mobile", strings[1])
                    .appendQueryParameter("password", strings[2])
                    .appendQueryParameter("token", strings[3])
                    .appendQueryParameter("phone_os", "1")
                    .build()
            Log.e("URI", buildURI.toString())
            var url: URL? = null
            try {
                url = URL(buildURI.toString())
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }

            val builder = Request.Builder()
            builder.url(url!!)
            builder.addHeader("X-API-KEY", Var.tokenLogin)
            val request = builder.build()

            try {
                val response = client.newCall(request).execute()
                return response.body()!!.string()
            } catch (e: IOException) {
                Log.e("error_handle_login", e.toString())
            }

            return null
        }

        override fun onPostExecute(s: String?) {
            super.onPostExecute(s)
            results.dismisDialog()
            if (s == null) {
                results.showConnectError()
            } else {
                handlerResult(s)
            }
        }
    }

    private fun handlerResult(response: String) {
        Log.e("response", response)
        try {
            val jsonObject = JSONObject(response)
            if (jsonObject.getString("status") == "success") {
                val gson = Gson()
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
