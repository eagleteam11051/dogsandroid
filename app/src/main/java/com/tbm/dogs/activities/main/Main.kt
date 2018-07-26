package com.tbm.dogs.activities.main

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.gms.location.*
import com.google.gson.Gson
import com.tbm.dogs.Helper.Shared
import com.tbm.dogs.Helper.Var
import com.tbm.dogs.Helper.Var.shiper
import com.tbm.dogs.R
import com.tbm.dogs.activities.congviec.ViecDangCho
import com.tbm.dogs.activities.congviec.dalam.ViecDaLam
import com.tbm.dogs.activities.congviec.dangco.ViecDangCo
import com.tbm.dogs.activities.congviec.danglam.ViecDangLam
import com.tbm.dogs.activities.thongbao.thongbao.ThongBao
import com.tbm.dogs.model.obj.Job
import com.tbm.dogs.model.obj.Shiper
import kotlinx.android.synthetic.main.main.*

class Main : AppCompatActivity(), View.OnClickListener, Results {
    override fun showErrorJobs() {
        tNumberTimViec.text = "0"
        Var.jobs?.clear()
        Log.e("findJobSize","0")
        //dismis progress when loaded data update
        progressDialog.dismiss()
    }

    override fun showErrorJobsWaiting() {
        tNumberChoDuyet.text = "0"
        Var.jobsWaiting?.clear()
        Log.e("waitingsize","0")
        //dismis progress when loaded data update
        progressDialog.dismiss()
    }

    override fun showErrorJobsWorking() {
        tNumberDangLam.text = "0"
        Var.jobsWorking?.clear()
        Log.e("jobworkingsize","0")
        //dismis progress when loaded data update
        progressDialog.dismiss()
    }

    override fun showErrorJobsDone() {
        tNumberDaLam.text = "0"
        Var.jobsDone?.clear()
        Log.e("jobdonesize","0")
        //dismis progress when loaded data update
        progressDialog.dismiss()
    }

    private lateinit var gson: Gson
    private lateinit var shared: Shared
    private lateinit var handlerP: HandlerP
    private var mHandler: Handler? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest:LocationRequest
    private lateinit var locationCallback: LocationCallback
    var lat = ""
    var lng = ""
    var update = true
    private lateinit var progressDialog:ProgressDialog

    private fun init() {
        supportActionBar?.title = "Dogs"
        progressDialog = ProgressDialog(this).apply {
            setMessage("Vui lòng chờ !")
            setTitle("Đang cập nhật dữ liệu...")
            setCancelable(false)
        }
        initShiper()
        initData()
        layout_viec_dang_lam.setOnClickListener(this)
        layout_viec_dang_co.setOnClickListener(this)
        layout_viec_da_lam.setOnClickListener(this)
        layout_viec_dang_cho.setOnClickListener(this)

    }

    private fun initData() {
        tName.text = shiper!!.fullname
        tId.text = "ID: ${shiper!!.hero_id}"
        tMoney.text = "Tài Khoản: ${shiper!!.balance}đ"
        tAddress.text = "Địa Chỉ: ${shiper!!.address}"
        Log.e("loadAvatar:", shiper!!.image)
        Glide.with(this).load(shiper!!.image).into(imgAvatar)
        //Picasso.get().load(shiper!!.image).into(imgAvatar)
        shared = Shared(this)
        handlerP = HandlerP(this,this)
    }

    private fun initShiper() {
        if(Var.shiper == null){
            gson = Gson()
            shiper = gson.fromJson(intent.getStringExtra("shiper"), Shiper::class.java)
            Var.shiper = shiper
            Var.tokenOther = shiper!!.token
        }else{
            shiper = Var.shiper!!
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //supportActionBar!!.hide()
        setContentView(R.layout.main)
        init()
        buildLocationRequest()
        buildLocationCallBack()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        requestLocationUpdate()
        //SMSUtils.sendSMS(this,"01688033930","shiper x se den giao hang trong 7k,, tong gia tien la 199999, moi bajn online de")
    }

    private fun buildLocationCallBack() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                for (location in locationResult!!.locations) {
                    lat = location.latitude.toString()
                    lng = location.longitude.toString()
                    Log.e("lat after update",lat)
                    Log.e("lng after update",lng)
                }
                if(update){
                    handlerP.sendLocation(lat,lng)
                    update = false
                }
                super.onLocationResult(locationResult)
            }
        }
    }



    private fun buildLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 5000
            fastestInterval = 1000
            smallestDisplacement = 10f
        }


    }

    override fun onStart() {
        super.onStart()
        Log.e("onStart","started repeat task reload job")
        initNumberJob()
        //loadJobs()
        if(mHandler == null){
            mHandler = Handler()
        }
        startRepeatingTask()
    }

    private fun startRepeatingTask() {
        mStatusChecker.run()
    }

    private var mStatusChecker: Runnable = object : Runnable {
        override fun run() {
            try {
                update = true
                loadJobs() //this function can change value of mInterval.
            } finally {
                mHandler?.postDelayed(this, /*3000*/Var.delayReloadJob)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        stopRepeatingTask()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }


    private fun stopRepeatingTask() {
        mHandler?.removeCallbacks(mStatusChecker)
        Log.e("destroy:","stoped repeat task reload job")
    }

    private fun loadJobs(){
        progressDialog.show()
        handlerP.getJobs()
        handlerP.getJobsWaiting()
        handlerP.getJobsWorking()
        handlerP.getJobsDone()
        handlerP.postMyLocation()
    }



    private fun initNumberJob() {
        tNumberTimViec.text = if(Var.jobs != null) Var.jobs?.size.toString() else "0"
        tNumberChoDuyet.text = if(Var.jobsWaiting != null) Var.jobsWaiting?.size.toString() else "0"
        tNumberDangLam.text = if(Var.jobsWorking != null) Var.jobsWorking?.size.toString() else "0"
        tNumberDaLam.text = if(Var.jobsDone != null) Var.jobsDone?.size.toString() else "0"
    }

    override fun onBackPressed() {
        handlerP.onBackPressed()
    }

    override fun onNewIntent(intent: Intent) {
        initNumberJob()
        //loadJobs()
        if(mHandler == null){
            mHandler = Handler()
        }
        startRepeatingTask()
        super.onNewIntent(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.mThongBao ->
                startActivity(Intent(this, ThongBao::class.java))
            R.id.mReload ->
                loadJobs()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.layout_viec_da_lam -> {
                startActivity(Intent(this, ViecDaLam::class.java))
            }
            R.id.layout_viec_dang_cho -> {
                startActivity(Intent(this, ViecDangCho::class.java))
            }
            R.id.layout_viec_dang_co -> {
                startActivity(Intent(this, ViecDangCo::class.java))
            }
            R.id.layout_viec_dang_lam -> {
                startActivity(Intent(this, ViecDangLam::class.java))
            }
        }
    }

    override fun tapAgain() {
        Toast.makeText(this, "Bấm back lần nữa để thoát", Toast.LENGTH_SHORT).show()
    }

    override fun finishApp() {
        finish()
    }

    override fun showError() {
        Log.e("error...","hihi")
        //dismis progress when loaded data update
        progressDialog.dismiss()
    }

    override fun returnJobs(jobs: ArrayList<Job>) {
        //dismis progress when loaded data update
        progressDialog.dismiss()
        Var.jobs = jobs
        tNumberTimViec.text = jobs.size.toString()
        Log.e("findJobSize",jobs.size.toString())
    }
    override fun returnJobsWaiting(jobs: ArrayList<Job>) {
        //dismis progress when loaded data update
        progressDialog.dismiss()
        Var.jobsWaiting = jobs
        tNumberChoDuyet.text = jobs.size.toString()
        Log.e("jobsWaitingSize:",jobs.size.toString())
    }
    override fun returnJobsWorking(jobs: ArrayList<Job>) {
        //dismis progress when loaded data update
        progressDialog.dismiss()
        Var.jobsWorking = jobs
        tNumberDangLam.text = jobs.size.toString()
        Log.e("jobsworkingsize:",jobs.size.toString())
    }

    override fun returnJobsDone(jobs: ArrayList<Job>) {
        //dismis progress when loaded data update
        progressDialog.dismiss()
        Var.jobsDone = jobs
        tNumberDaLam.text = jobs.size.toString()
        Log.e("jobsdonesie:",jobs.size.toString())
    }

    override fun showEnableLocation() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Máy của bạn đang tắt chức năng định vị!")
        builder.setMessage("Bạn có muốn bật định vị không?")
        builder.setPositiveButton("OK") {
            dialogInterface, i ->
            dialogInterface.cancel()
            val callIntent = Intent(Settings.ACTION_SETTINGS)
            startActivity(callIntent)
        }
        builder.create().show()

    }

    override fun requestUpdate() {
        requestLocationUpdate()
    }

    @SuppressLint("MissingPermission")
    private fun requestLocationUpdate(){
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper())
    }
}
