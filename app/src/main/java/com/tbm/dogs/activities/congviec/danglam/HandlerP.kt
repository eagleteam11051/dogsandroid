package com.tbm.dogs.activities.congviec.danglam

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import com.google.gson.Gson
import com.tbm.dogs.Helper.Dates
import com.tbm.dogs.Helper.Locations
import com.tbm.dogs.Helper.Var
import com.tbm.dogs.model.obj.Job
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import java.util.*


class HandlerP(var results: Results,var context: Context) {

    var mode = 0

    @SuppressLint("MissingPermission")
    fun checkinNhanHang(job: Job) {
        if(Locations.isLocationEnabled(context)){
            results.requestUpdate(job,1)
            results.showDialog()
        }else{
            results.showEnableLocation()
        }
    }



    fun checkinGiaoHang(job: Job) {
        if(Locations.isLocationEnabled(context)){
            results.requestUpdate(job,2)
            results.showDialog()
        }else{
            results.showEnableLocation()
        }
    }

    fun checkin(job: Job, mode: Int, lat: String, lng: String) {
        this.mode = mode
        if(mode == 1){
            Checkin().execute(Var.API_WORKING,job.order_id, Var.shiper?.hero_id,lat,lng)
        }else{
            Checkin().execute(Var.API_SUCCESS,job.order_id,Var.shiper?.hero_id,lat,lng)
        }
    }

    fun getJobsWorking(){
        //api,hero_id,status,start_date,end_date,start
        handlerJobsWorking().execute(Var.API_GET_ORDERS, Var.shiper?.hero_id,"2",Dates().startDate(),Dates().endDate(),"0")
    }

    inner class Checkin: AsyncTask<String, Void, String>(){
        internal var client = OkHttpClient()
        override fun doInBackground(vararg strings: String): String? {
            val uri = Uri.parse(strings[0])
                    .buildUpon()
                    .appendQueryParameter("order_id", strings[1])
                    .appendQueryParameter("hero_id", strings[2])
                    .appendQueryParameter("lat", strings[3])
                    .appendQueryParameter("long",strings[4])
                    .build()
            Log.e("checkin", uri.toString())
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
                Log.e("checkin:", e.toString())
                results.showError("Có lỗi xảy ra!")
            }

            return null
        }

        override fun onPostExecute(s: String) {
            super.onPostExecute(s)
            results.dismisDialog()
            handlerResults(s)
        }

        private fun handlerResults(s: String) {
            Log.e("checkin",s)
            val jsonObject = JSONObject(s)
            val status = jsonObject["status"]
            val response = jsonObject["response"]
            if(status == "success"){
                results.showSuccess(mode)
            }else{
                results.showError(response.toString())
            }
        }
    }
    inner class handlerJobsWorking: AsyncTask<String,Void,String>(){
        internal var client = OkHttpClient()
        override fun doInBackground(vararg strings: String): String? {
            val uri = Uri.parse(strings[0])
                    .buildUpon()
                    .appendQueryParameter("hero_id", strings[1])
                    .appendQueryParameter("status", strings[2])
                    .appendQueryParameter("start_date",strings[3])
                    .appendQueryParameter("end_date",strings[4])
                    .appendQueryParameter("start",strings[5])
                    .build()
            Log.e("jobWorking", uri.toString())
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
                Log.e("jobWorking:", e.toString())
                results.showError("Có lỗi xảy ra!")
            }

            return null
        }

        override fun onPostExecute(s: String) {
            super.onPostExecute(s)
            handlerResults(s)
        }

        private fun handlerResults(s: String) {
            Log.e("jobWorking",s)
            val jobs = ArrayList<Job>()
            //parse job
            //TODO
            val jsonObject = JSONObject(s)
            val status = jsonObject["status"]
            if(status == "success"){
                val response = jsonObject["response"] as JSONArray
                if(response.length()>0){
                    for (i in 0..response.length()-1){
                        val item = response.getJSONObject(i).toString()

                        val job: Job = Gson().fromJson(item, Job::class.java)
                        //Log.e("item",job.toString())
                        jobs.add(job)
                    }
                    results.returnJobsWorking(jobs)
                }
            }else{
                results.showErrorJobsWorking()
            }
        }
    }
}