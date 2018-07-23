package com.tbm.dogs.activities.congviec.dangco

import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import com.google.gson.Gson
import com.tbm.dogs.Helper.Maths
import com.tbm.dogs.Helper.Var
import com.tbm.dogs.model.obj.Job
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

class HandlerP(private val results: Results) {

    lateinit var maths: Maths
    lateinit var gson: Gson
    init {
        gson = Gson()
    }

    fun getJob(tag: String): Job? {
        for(job in Var.jobs!!){
            if(tag == job.order_id){
                return job
            }
        }
        return null
    }

    fun getGroupJob(job: Job?): ArrayList<Job>{
        maths = Maths()
        val jobs = ArrayList<Job>()
        for(item in Var.jobs!!){
            if(maths.distance(job!!.pickup.latitude.toDouble(),job.pickup.longitude.toDouble(),item.pickup.latitude.toDouble(),item.pickup.longitude.toDouble())<100){
                jobs.add(item)
            }
        }
        return jobs
    }


    fun getJobs() {
        handlerJobs().execute(Var.API_GET_NEW_JOBS, Var.shiper?.hero_id)
    }

    fun AcceptOrder(order_id: String, hero_id: String?, date: String) {
        AcceptOrder().execute(Var.API_ACCEPT_ORDER,order_id,hero_id,date)
    }

    inner class AcceptOrder: AsyncTask<String,Void,String>(){
        internal var client = OkHttpClient()
        override fun doInBackground(vararg strings: String): String? {
            val uri = Uri.parse(strings[0])
                    .buildUpon()
                    .appendQueryParameter("order_id", strings[1])
                    .appendQueryParameter("hero_id", strings[2])
                    .appendQueryParameter("res_time", strings[3])
                    .build()
            Log.e("AcceptOrder", uri.toString())
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
                Log.e("AcceptOrder:", e.toString())
                results.showError()
            }

            return null
        }

        override fun onPostExecute(s: String) {
            super.onPostExecute(s)
            handlerResults(s)
        }

        private fun handlerResults(s: String) {
            Log.e("AcceptOrder",s)
            val jobs = java.util.ArrayList<Job>()
            //parse job
            //TODO
            val jsonObject = JSONObject(s)
            val status = jsonObject["status"]
            if(status == "success"){
//                val response = jsonObject["response"] as JSONArray
//                if(response.length()>0){
//                    for (i in 0..response.length()-1){
//                        val item = response.getJSONObject(i).toString()
//
//                        val job: Job = gson.fromJson(item, Job::class.java)
//                        //Log.e("item",job.toString())
//                        jobs.add(job)
//                    }
//                    results.returnJobs(jobs)
//                }
                results.showSuccess(jsonObject["phone_number"] as String)
            }else{
                results.showError()
            }
        }
    }

    inner class handlerJobs : AsyncTask<String, Void, String>() {
        internal var client = OkHttpClient()
        override fun doInBackground(vararg strings: String): String? {
            val uri = Uri.parse(strings[0])
                    .buildUpon()
                    .appendQueryParameter("hero_id", strings[1])
                    .appendQueryParameter("service", "3")
                    .build()
            Log.e("uriViecdangco", uri.toString())
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
                Log.e("getviecdangco:", e.toString())
                results.showError()
            }

            return null
        }

        override fun onPostExecute(s: String) {
            super.onPostExecute(s)
            handlerResults(s)
        }

        private fun handlerResults(s: String) {
            Log.e("getviecdangco",s)
            val jobs = java.util.ArrayList<Job>()
            //parse job
            //TODO
            val jsonObject = JSONObject(s)
            val status = jsonObject["status"]
            if(status == "success"){
                val response = jsonObject["response"] as JSONArray
                if(response.length()>0){
                    for (i in 0..response.length()-1){
                        val item = response.getJSONObject(i).toString()

                        val job: Job = gson.fromJson(item, Job::class.java)
                        //Log.e("item",job.toString())
                        jobs.add(job)
                    }
                    results.returnJobs(jobs)
                }
            }else{
                results.showError()
            }
        }
    }

}
