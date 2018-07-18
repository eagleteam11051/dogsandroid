package com.tbm.dogs.activities.thongbao.thongbao

import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import com.google.gson.Gson
import com.tbm.dogs.Helper.Var
import com.tbm.dogs.model.obj.ThongBaoObj
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import java.util.*

class HandlerP(var results: Results) {
    fun getThongBao(indexStatus: Int) {
        handlerGetThongBao().execute(Var.API_GET_MESSAGES,Var.shiper?.hero_id,indexStatus.toString())
    }

    inner class handlerGetThongBao: AsyncTask<String, Void, String>(){
        internal var client = OkHttpClient()
        override fun doInBackground(vararg strings: String): String? {
            val uri = Uri.parse(strings[0])
                    .buildUpon()
                    .appendQueryParameter("hero_id", strings[1])
                    .appendQueryParameter("start", strings[2])
                    .build()
            Log.e("getThongBao", uri.toString())
            var url: URL? = null
            try {
                url = URL(uri.toString())
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }

            val builder = Request.Builder()
            builder.url(url!!)
            builder.addHeader(Var.HEADER, Var.tokenOther)
            val request = builder.build()
            try {
                val response = client.newCall(request).execute()
                return response.body()!!.string()
            } catch (e: IOException) {
                Log.e("getThongBao:", e.toString())
                results.showError()
            }

            return null
        }

        override fun onPostExecute(s: String) {
            super.onPostExecute(s)
            results.dismisProgress()
            handlerResults(s)
        }

        private fun handlerResults(s: String) {
            Log.e("getThongBao",s)
            val thongBaos = ArrayList<ThongBaoObj>()
            //parse job
            //TODO
            val jsonObject = JSONObject(s)
            val status = jsonObject["status"]
            if(status == "success"){
                val response = jsonObject["response"] as JSONArray
                if(response.length()>0){
                    for (i in 0..response.length()-1){
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
    }

}