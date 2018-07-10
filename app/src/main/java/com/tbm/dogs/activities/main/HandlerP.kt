package com.tbm.dogs.activities.main

import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import com.google.gson.Gson
import com.tbm.dogs.Helper.Dates
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

class HandlerP(private val results: Results) {
    private var l = 0
    lateinit var gson: Gson
    lateinit var dates: Dates
    init {
        gson = Gson()
        dates = Dates()
    }

    fun onBackPressed() {
        if (l > 0) {
            results.finishApp()
        } else {
            results.tapAgain()
            l++
        }
    }
    fun getJobs() {
        handlerJobs().execute(Var.API_GET_NEW_JOBS, Var.shiper?.hero_id)
    }

    fun getJobsWaiting() {
        //api,hero_id,status,start_date,end_date,start
        handlerJobsWaiting().execute(Var.API_GET_ORDERS, Var.shiper?.hero_id,"9",dates.startDate(),dates.endDate(),"0")
    }


    inner class handlerJobsWaiting: AsyncTask<String,Void,String>(){
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
            Log.e("uriViecdangcho", uri.toString())
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
                Log.e("getviecdangcho:", e.toString())
                results.showError()
            }

            return null
        }

        override fun onPostExecute(s: String) {
            super.onPostExecute(s)
            handlerResults(s)
        }

        private fun handlerResults(s: String) {
            Log.e("getviecdangcho",s)
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

                        val job: Job = gson.fromJson(item, Job::class.java)
                        //Log.e("item",job.toString())
                        jobs.add(job)
                    }
                    results.returnJobsWaiting(jobs)
                }
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
