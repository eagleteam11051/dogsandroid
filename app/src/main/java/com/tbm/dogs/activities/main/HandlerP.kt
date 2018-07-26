package com.tbm.dogs.activities.main

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.tbm.dogs.Helper.Dates
import com.tbm.dogs.Helper.Locations
import com.tbm.dogs.Helper.Var
import com.tbm.dogs.Helper.VolleyHelper
import com.tbm.dogs.model.obj.Job
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class HandlerP(private val results: Results, var context: Context) {
    private var l = 0
    private val gson: Gson = Gson()
    private val dates: Dates = Dates()
    private val volley = VolleyHelper(context)

    fun onBackPressed() {
        if (l > 0) {
            results.finishApp()
        } else {
            results.tapAgain()
            l++
        }
    }

    fun getJobs() {
        handlerFindJobs(Var.API_GET_NEW_JOBS, Var.shiper?.hero_id!!, "3")
    }

    fun getJobsWaiting() {
        //api,hero_id,status,start_date,end_date,start
        handlerJobsWaiting(Var.API_GET_ORDERS, Var.shiper?.hero_id!!, "9", dates.startDate(), dates.endDate(), "0")
    }

    fun getJobsWorking() {
        //api,hero_id,status,start_date,end_date,start
        handlerJobsWorking(Var.API_VINTER_GET_WORKING, Var.shiper?.hero_id!!, "3"/*,dates.startDate(),dates.endDate(),"0"*/)
    }

    fun getJobsDone() {
        //api,hero_id,status,start_date,end_date,start
        handlerJobsDone(Var.API_GET_ORDERS, Var.shiper?.hero_id!!, "8", dates.startDate(), dates.endDate(), "0")
    }

    fun postMyLocation() {
        if (Locations.isLocationEnabled(context)) {
            results.requestUpdate()
            //results.update(true)
        } else {
            results.showEnableLocation()
        }
    }

    fun sendLocation(lat: String, lng: String) {
        Log.e("sendLocation:", "$lat-$lng")
        handlerSendLocation(Var.API_SEND_GEO_LOCATION, Var.shiper?.hero_id!!, lat, lng)
    }


    private fun handlerSendLocation(url: String, hero_id: String, latitude: String, longitude: String) {
        fun handlerResults(s: String) {
            Log.e("sendLocation", s)
            val jsonObject = JSONObject(s)
            val status = jsonObject["status"]
            if (status == "success") {
                Log.e("sendLocation:", jsonObject["response"].toString())
            } else {
                //results.showErrorJobsDone()
                Log.e("sendLocation:", jsonObject["response"].toString())
            }
        }
        volley.requestAPI(
                url,
                HashMap<String, String>().apply {
                    put(Var.HEADER, Var.tokenOther)
                },
                HashMap<String, String>().apply {
                    put("hero_id", hero_id)
                    put("latitude", latitude)
                    put("longitude", longitude)
                },
                { response -> handlerResults(response) },
                { error ->
                    Log.e("sendLocation:", error)
                    results.showError()
                }
        )
    }

    private fun handlerJobsDone(url: String, hero_id: String, status: String, start_date: String, end_date: String, start: String) {
        fun handlerResults(s: String) {
            Log.e("jobdone", s)
            val jobs = ArrayList<Job>()
            val jsonObject = JSONObject(s)
            val status = jsonObject["status"]
            if (status == "success") {
                val response = jsonObject["response"] as JSONArray
                if (response.length() > 0) {
                    for (i in 0 until response.length()) {
                        val item = response.getJSONObject(i).toString()
                        val job: Job = gson.fromJson(item, Job::class.java)
                        //Log.e("item",job.toString())
                        jobs.add(job)
                    }
                    results.returnJobsDone(jobs)
                } else {
                    results.showErrorJobsDone()
                }
            } else {
                results.showErrorJobsDone()
            }
        }
        volley.requestAPI(
                url,
                HashMap<String, String>().apply {
                    put(Var.HEADER, Var.tokenOther)
                },
                HashMap<String, String>().apply {
                    put("hero_id", hero_id)
                    put("status", status)
                    put("start_date", start_date)
                    put("end_date", end_date)
                    put("start", start)
                },
                { response -> handlerResults(response) },
                { error ->
                    Log.e("jobdone:", error)
                    results.showError()
                    results.showErrorJobsDone()
                }
        )
    }

    private fun handlerJobsWorking(url: String, hero_id: String, service: String) {
        fun handlerResults(s: String) {
            Log.e("jobWorking", s)
            val jobs = ArrayList<Job>()
            val jsonObject = JSONObject(s)
            val status = jsonObject["status"]
            if (status == "success") {
                val response = jsonObject["response"] as JSONArray
                if (response.length() > 0) {
                    for (i in 0 until response.length()) {
                        val item = response.getJSONObject(i).toString()
                        val job: Job = gson.fromJson(item, Job::class.java)
                        //Log.e("item",job.toString())
                        jobs.add(job)
                    }
                    results.returnJobsWorking(jobs)
                } else {
                    results.showErrorJobsWorking()
                }
            } else {
                results.showErrorJobsWorking()
            }
        }
        volley.requestAPI(
                url,
                HashMap<String, String>().apply {
                    put(Var.HEADER, Var.tokenOther)
                },
                HashMap<String, String>().apply {
                    put("hero_id", hero_id)
                    put("service", service)
                },
                { response -> handlerResults(response) },
                { error ->
                    Log.e("jobWorking:", error)
                    results.showError()
                    results.showErrorJobsWorking()
                }
        )
    }

    private fun handlerJobsWaiting(url: String, hero_id: String, status: String, start_date: String, end_date: String, start: String) {
        fun handlerResults(s: String) {
            Log.e("jobWaiting", s)
            val jobs = ArrayList<Job>()
            val jsonObject = JSONObject(s)
            val status = jsonObject["status"]
            if (status == "success") {
                val response = jsonObject["response"] as JSONArray
                if (response.length() > 0) {
                    for (i in 0 until response.length()) {
                        val item = response.getJSONObject(i).toString()
                        val job: Job = gson.fromJson(item, Job::class.java)
                        //Log.e("item",job.toString())
                        jobs.add(job)
                    }
                    results.returnJobsWaiting(jobs)
                } else {
                    results.showErrorJobsWaiting()
                }
            } else {
                results.showErrorJobsWaiting()
            }
        }
        volley.requestAPI(
                url,
                HashMap<String, String>().apply {
                    put(Var.HEADER, Var.tokenOther)
                },
                HashMap<String, String>().apply {
                    put("hero_id", hero_id)
                    put("status", status)
                    put("start_date", start_date)
                    put("end_date", end_date)
                    put("start", start)
                },
                { response -> handlerResults(response) },
                { error ->
                    Log.e("jobWaiting:", error)
                    results.showError()
                    results.showErrorJobsWaiting()
                }
        )
    }

    private fun handlerFindJobs(url: String, hero_id: String, service: String) {
        fun handlerResults(s: String) {
            Log.e("findJob", s)
            val jobs = ArrayList<Job>()
            val jsonObject = JSONObject(s)
            val status = jsonObject["status"]
            if (status == "success") {
                val response = jsonObject["response"] as JSONArray
                if (response.length() > 0) {
                    for (i in 0 until response.length()) {
                        val item = response.getJSONObject(i).toString()
                        val job: Job = gson.fromJson(item, Job::class.java)
                        //Log.e("item",job.toString())
                        jobs.add(job)
                    }
                    results.returnJobs(jobs)
                } else {
                    results.showErrorJobs()
                }
            } else {
                results.showErrorJobs()
            }
        }
        volley.requestAPI(
                url,
                HashMap<String, String>().apply {
                    put(Var.HEADER, Var.tokenOther)
                },
                HashMap<String, String>().apply {
                    put("hero_id", hero_id)
                    put("service", service)
                },
                { response -> handlerResults(response) },
                { error ->
                    Log.e("jobWorking:", error)
                    results.showError()
                    results.showErrorJobs()
                }
        )
    }
}
