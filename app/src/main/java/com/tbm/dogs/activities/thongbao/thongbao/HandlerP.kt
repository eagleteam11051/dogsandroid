package com.tbm.dogs.activities.thongbao.thongbao

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.tbm.dogs.Helper.Var
import com.tbm.dogs.Helper.VolleyHelper
import com.tbm.dogs.model.obj.ThongBaoObj
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class HandlerP(var results: Results,val context:Context) {
    private val volley = VolleyHelper(context)
    fun getThongBao(indexStatus: Int) {
        handlerGetThongBao(Var.API_GET_MESSAGES, Var.shiper?.hero_id!!,indexStatus.toString())
    }

    private fun handlerGetThongBao(url:String,hero_id:String,start:String){
        fun handlerResults(s: String) {
            Log.e("getThongBao",s)
            val thongBaos = ArrayList<ThongBaoObj>()
            //parse job
            //TODO
            val jsonObject = JSONObject(s)
            val status = jsonObject["status"]
            if(status == "success"){
                val response = jsonObject["response"] as JSONArray
                if(response.length()>0){
                    for (i in 0 until response.length()){
                        val item = response.getJSONObject(i).toString()

                        val thongBaoObj: ThongBaoObj = Gson().fromJson(item, ThongBaoObj::class.java)
                        //Log.e("item",job.toString())
                        thongBaos.add(thongBaoObj)
                    }
                    results.returnThongBaos(thongBaos)
                }
            }else{
                results.showError()
            }
        }
        volley.requestAPI(
                url,
                HashMap<String,String>().apply {
                    put(Var.HEADER,Var.tokenOther)
                },
                HashMap<String,String>().apply {
                    put("hero_id",hero_id)
                    put("start",start)
                },
                {response ->
                    results.dismisProgress()
                    handlerResults(response)
                },
                {error ->
                    Log.e("getThongBao:", error)
                    results.showError()
                }
        )
    }
}