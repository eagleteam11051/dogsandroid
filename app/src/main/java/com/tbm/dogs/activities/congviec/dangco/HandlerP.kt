package com.tbm.dogs.activities.congviec.dangco

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.tbm.dogs.Helper.Maths
import com.tbm.dogs.Helper.Var
import com.tbm.dogs.Helper.VolleyHelper
import com.tbm.dogs.model.obj.Job
import org.json.JSONArray
import org.json.JSONObject

class HandlerP(private val results: Results, val context: Context) {

    private lateinit var maths: Maths
    private var gson: Gson = Gson()
    val volley = VolleyHelper(context)

    fun getJob(tag: String): Job? {
        for (job in Var.jobs!!) {
            if (tag == job.order_id) {
                return job
            }
        }
        return null
    }

    fun getGroupJob(job: Job?): ArrayList<Job> {
        maths = Maths()
        val jobs = ArrayList<Job>()
        for (item in Var.jobs!!) {
            if (maths.distance(job!!.pickup.latitude.toDouble(), job.pickup.longitude.toDouble(), item.pickup.latitude.toDouble(), item.pickup.longitude.toDouble()) < 100) {
                jobs.add(item)
            }
        }
        return jobs
    }

    fun getJobs() {
        handlerJobs(Var.API_GET_NEW_JOBS, Var.shiper?.hero_id!!, "3")
    }

    fun acceptOrder(order_id: String, hero_id: String?, date: String) {
        handlerAcceptOrder(Var.API_ACCEPT_ORDER, order_id, hero_id!!, date)
    }

    private fun handlerAcceptOrder(url: String, order_id: String, hero_id: String, date: String) {
        fun handlerResults(s: String) {
            Log.e("acceptOrder", s)
            val jsonObject = JSONObject(s)
            val status = jsonObject["status"]
            if (status == "success") {
                results.showSuccess(jsonObject["phone_number"] as String)
            } else {
                results.showError()
            }
        }
        volley.requestAPI(
                url,
                HashMap<String, String>().apply {
                    put(Var.HEADER, Var.tokenOther)
                },
                HashMap<String, String>().apply {
                    put("order_id", order_id)
                    put("hero_id", hero_id)
                    put("res_time", date)
                },
                { response -> handlerResults(response) },
                { error ->
                    Log.e("acceptOrder:", error)
                    results.showError()
                }
        )
    }

    private fun handlerJobs(url: String, hero_id: String, service: String) {
        fun handlerResults(s: String) {
            Log.e("getviecdangco", s)
            val jobs = java.util.ArrayList<Job>()
            val jsonObject = JSONObject(s)
            val status = jsonObject["status"]
            if (status == "success") {
                val response = jsonObject["response"] as JSONArray
                if (response.length() > 0) {
                    for (i in 0 until response.length()) {
                        val item = response.getJSONObject(i).toString()
                        val job: Job = gson.fromJson(item, Job::class.java)
                        jobs.add(job)
                    }
                    results.returnJobs(jobs)
                }
            } else {
                results.showError()
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
                    Log.e("getviecdangco:", error)
                    results.showError()
                }
        )
    }
}
