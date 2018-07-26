package com.tbm.dogs.Helper

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class VolleyHelper(val context: Context) {
    private var request: RequestQueue = Volley.newRequestQueue(context)

    fun requestAPI(url:String, header:HashMap<String,String>,params: HashMap<String, String> ,success:(response:String)->Unit,error:(message:String)->Unit){
        val stringRequest = object : StringRequest(Request.Method.GET,getURL(url,params),
                Response.Listener { response ->
                    success(response.toString())
                },
                Response.ErrorListener { error ->
                    error(error.toString())
                }
        ){
            override fun getHeaders(): MutableMap<String, String> {
                return header
            }
        }
        request.add(stringRequest)
    }

    fun getURL(url:String,params:HashMap<String,String>):String{
        var url = url
        for (param in params){
            url = "${url}${param.key}=${param.value}&"
        }
        return url.substring(0 until url.length-1)
    }
}