package com.tbm.dogs.activities.congviec.danglam

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.util.Log
import com.google.gson.Gson
import com.tbm.dogs.Helper.Locations
import com.tbm.dogs.Helper.Var
import com.tbm.dogs.Helper.VolleyHelper
import com.tbm.dogs.model.obj.Job
import org.json.JSONArray
import org.json.JSONObject
import java.util.*


class HandlerP(var results: Results, var context: Context) {

    private var mode = 0
    lateinit var job: Job
    private val volley = VolleyHelper(context)
    private var update = false

    @SuppressLint("MissingPermission")
    fun checkinNhanHang(job: Job) {
        if (!results.hasPermis()) {
            results.requestPermis()
        } else {
            //TODO kiem tra thoi gian shiper chon va thoi gian con lai cua don hang
            if (job.create_time_int.toInt() - (System.currentTimeMillis() / 1000) <= 0) {
                results.showDeadLine()
            } else {
                if (Locations.isLocationEnabled(context)) {
                    if(!Var.lat.isEmpty() && !Var.lng.isEmpty()){
                        //handlerP.checkin(this@ViecDangLam.job!!,mode,lat,lng)
                        results.showDialog()
                        checkin(job,1,Var.lat,Var.lng)
                    }else{
                        handlerRun5s()
                        results.requestUpdate(job, 1)
                        results.showDialog()
                        results.update(true)
                        update = true
                        this.mode = 1
                        this.job = job
                    }
                } else {
                    results.showEnableLocation()
                }
            }
        }
    }




    fun checkinGiaoHang(job: Job) {
        if (Locations.isLocationEnabled(context)) {
            if(!Var.lat.isEmpty() && !Var.lng.isEmpty()){
                //handlerP.checkin(this@ViecDangLam.job!!,mode,lat,lng)
                results.showDialog()
                checkin(job,2,Var.lat,Var.lng)
            }else{
                handlerRun5s()
                results.requestUpdate(job, 2)
                results.showDialog()
                results.update(true)
                update = true
                this.mode = 2
                this.job = job
            }
        } else {
            results.showEnableLocation()
        }
    }
    private fun handlerRun5s(){
        Handler().postDelayed({
            // sau 7s neu khong request duoc gps se kiem tra trong Var co lat,lng chua
            // neu co roi thi su dung luon
            //neu chua co se bao loi khong the tim thay gps
            if(update){
                //kiem tra trong var co location chua
                if(!Var.lat.isEmpty() && !Var.lng.isEmpty()){
                    //neu co thi su dung luon
                    checkin(this.job,this.mode,Var.lat,Var.lng)
                }else{
                    //neu khong co location trong var thi thong bao ko tim duoc gps
                    results.showErrorGPS()
                }
            }
        }, 7000)
    }

    fun checkin(job: Job, mode: Int, lat: String, lng: String) {
        //cap nhat lai trang thai update de cac tac vu khac khong the update duoc don hang nua
        //mode = 1 tung ung voi nhan hang, = 2 tuong ung voi giao hang
        update = false
        this.mode = mode
        this.job = job
        if (mode == 1) {
            handlerCheckin(Var.API_WORKING, job.order_id, Var.shiper?.hero_id!!, lat, lng)
        } else {
            handlerCheckin(Var.API_SUCCESS, job.order_id, Var.shiper?.hero_id!!, lat, lng)
        }
    }

    fun getJobsWorking() {
        //api,hero_id,status,start_date,end_date,start
        handlerJobsWorking(Var.API_VINTER_GET_WORKING, Var.shiper?.hero_id!!, "3"/*,Dates().startDate(),Dates().endDate(),"0"*/)
    }

    private fun handlerCheckin(url:String,order_id:String,hero_id:String,lat:String,lng:String){
        fun handlerResults(s: String) {
            Log.e("checkin", s)
            val jsonObject = JSONObject(s)
            val status = jsonObject["status"]
            val response = jsonObject["response"]
            if (status == "success") {
                results.showSuccess(this@HandlerP.job, mode)
            } else {
                results.showError(response.toString())
            }
        }
        volley.requestAPI(
                url,
                HashMap<String,String>().apply {
                    put(Var.HEADER,Var.tokenOther)
                },
                HashMap<String,String>().apply {
                    put("order_id",order_id)
                    put("hero_id",hero_id)
                    put("lat",lat)
                    put("long",lng)
                },
                {response ->
                    results.dismisDialog()
                    handlerResults(response)
                },
                {error ->
                    Log.e("checkin:", error)
                    results.dismisDialog()
                    results.showError("Có lỗi xảy ra!")
                }
        )
    }

    private fun handlerJobsWorking(url:String,hero_id: String,service:String){
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

                        val job: Job = Gson().fromJson(item, Job::class.java)
                        //Log.e("item",job.toString())
                        jobs.add(job)
                    }
                    results.returnJobsWorking(jobs)
                }
            } else {
                results.showErrorJobsWorking()
            }
        }
        volley.requestAPI(
                url,
                HashMap<String,String>().apply {
                    put(Var.HEADER,Var.tokenOther)
                },
                HashMap<String,String>().apply {
                    put("hero_id",hero_id)
                    put("service",service)
                },
                {response ->
                    results.dismisDialog()
                    handlerResults(response) },
                {error ->
                    Log.e("jobWorking:", error)
                    results.dismisDialog()
                    results.showError("Có lỗi xảy ra!")
                }
        )
    }
}