package com.tbm.dogs.activities.congviec.dangco

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.telephony.SmsManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.tbm.dogs.Helper.Locations
import com.tbm.dogs.Helper.SMSUtils
import com.tbm.dogs.Helper.Var
import com.tbm.dogs.R
import com.tbm.dogs.model.obj.Job
import java.util.*


class ViecDangCo : AppCompatActivity(), OnMapReadyCallback, Results, GoogleMap.OnMarkerClickListener {
    private lateinit var mMap: GoogleMap
    private var jobs: ArrayList<Job>? = null
    private lateinit var handlerP: HandlerP
    private lateinit var groupJob: ArrayList<Job>
    private lateinit var currentJob: Job
    private lateinit var currentPickedTime:String
    private var mHandler: Handler? = null
    private var currentTimeDeadLine:Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viec_dang_co)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Những Đơn Hàng Đang Có"
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        handlerP = HandlerP(this)
        handlerP.getJobs()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onNewIntent(intent: Intent) {
        handlerP.getJobs()
        super.onNewIntent(intent)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.isMyLocationEnabled = true
        mMap.setOnMyLocationButtonClickListener {
            if(!Locations.isLocationEnabled(applicationContext)){
                showLocationEnable()
            }
            false
        }
        mMap.setMaxZoomPreference(50.0f)
        mMap.setMinZoomPreference(5.0f)
        val sydney = LatLng(21.5975775, 105.8133112)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,13.0f));
        mMap.setOnMarkerClickListener(this)
        this.jobs = Var.jobs
        updateMap()
    }

    private fun showLocationEnable() {
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

    override fun onMarkerClick(p0: Marker?): Boolean {
        showInfo(handlerP.getJob(p0?.tag.toString()))
        return true
    }

    private fun showInfo(job: Job?) {
        currentTimeDeadLine = job?.create_time_int?.toInt()!!

        this.groupJob = handlerP.getGroupJob(job)
        var job = groupJob[0]
        var index = 0
        var picked = false

        val builder = AlertDialog.Builder(this)
        var alertDialog:AlertDialog? = null

        val view = layoutInflater.inflate(R.layout.layout_booking,null)

        val tDiemNhan:TextView = view.findViewById(R.id.tDiemNhan)

        val tDiemGiao: TextView = view.findViewById(R.id.tDiemGiao)

        val tKhoangCach: TextView = view.findViewById(R.id.tKhoangCach)


        val bNhanViec:Button = view.findViewById(R.id.bNhanViec)
        val layoutNext:RelativeLayout = view.findViewById(R.id.layout_next_job)
        val bSau:Button = view.findViewById(R.id.bSau)
        val bTruoc:Button = view.findViewById(R.id.bTruoc)
        val tIndex:TextView = view.findViewById(R.id.tIndex)
        val tTimeNull:TextView = view.findViewById(R.id.tTimeNull)
        val tKhoiLuong:TextView = view.findViewById(R.id.tKhoiLuong)
        val tGiaTri:TextView = view.findViewById(R.id.tGiaTri)
        val tPhiShip:TextView = view.findViewById(R.id.tPhiShip)
        val tDeadLine:TextView = view.findViewById(R.id.tDeadLine)
        val tMota:TextView = view.findViewById(R.id.tMoTa)
        val tGhiChu:TextView = view.findViewById(R.id.tGhiChu)

        //**************
        fun updateDeadLine() {
            val delta = currentTimeDeadLine - (System.currentTimeMillis()/1000)
            val gio = delta / 60 / 60
            val phut = (delta / 60) % 60
            val s = delta % 60
            if(gio<0 || phut<0 || s<0){
                tDeadLine.setTextColor(Color.RED)
            }else{
                tDeadLine.setTextColor(Color.GREEN)
            }
            tDeadLine.text = "⏰$gio:$phut:$s"
        }

        var mStatusChecker: Runnable = object : Runnable {
            override fun run() {
                try {
                    updateDeadLine()
                } finally {
                    mHandler?.postDelayed(this, 1000)
                }
            }
        }

        fun stopRepeatingTask() {
            mHandler?.removeCallbacks(mStatusChecker)
        }
        fun stopTask() {
            stopRepeatingTask()
        }




        fun startRepeatingTask() {
            mStatusChecker.run()
        }

        fun startTask() {
            if(mHandler == null){
                mHandler = Handler()
            }
            startRepeatingTask()
        }

        startTask()
        //********************

        val bThoiGian:Button = view.findViewById(R.id.bThoiGian)
        bThoiGian.setOnClickListener {
            // TODO Auto-generated method stub
            val mcurrentTime = Calendar.getInstance()
            val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
            val minute = mcurrentTime.get(Calendar.MINUTE)
            val mTimePicker: TimePickerDialog
            mTimePicker = TimePickerDialog(this@ViecDangCo, TimePickerDialog.OnTimeSetListener {
                timePicker, selectedHour, selectedMinute ->
                bThoiGian.setText(selectedHour.toString() + ":" + selectedMinute)
                picked = true
            }, hour, minute, true)//Yes 24 hour time
            mTimePicker.setTitle("Chọn Thời gian đáp ứng")
            mTimePicker.show()
        }

        fun update(){
            tDiemNhan.text = "Điểm Nhận: ${job?.pickup!!.address}"
            tDiemGiao.text = "Điểm Giao: ${job?.dropoff.address}"
            tKhoangCach.text =  "Khoảng Cách: ${job.distance}Km"
            tKhoiLuong.text = "Khối Lượng: ${job.weight}Kg"
            tGiaTri.text = "Giá Trị: ${job.money_first}đ"
            tPhiShip.text = "Phí Ship: ${job.fee}đ"
            tIndex.text = "${index+1}/${groupJob.size}"
            tMota.text = "Mô Tả: ${job.description}"
            tGhiChu.text = "Ghi Chú: ${job.note}"
        }
        update()
        if(groupJob.size>1){
            layoutNext.visibility = View.VISIBLE
            bSau.setOnClickListener {
                index --
                if(index<0){
                    index = groupJob.size-1
                }
                job = groupJob[index]
                update()
            }

            bTruoc.setOnClickListener {
                index ++
                if(index>=groupJob.size){
                    index = 0
                }
                job = groupJob[index]
                update()
            }
        }

        bNhanViec.setOnClickListener {
            if(!picked){
                tTimeNull.visibility = View.VISIBLE
            }else{
                alertDialog?.dismiss()
                stopTask()
                //date()
                currentJob = job
                currentPickedTime = bThoiGian.text.toString()
                handlerP.AcceptOrder(job.order_id,Var.shiper?.hero_id,bThoiGian.text.toString())
            }
        }
        val bTuChoi:Button = view.findViewById(R.id.bTuChoi)
        bTuChoi.setOnClickListener {
            alertDialog?.dismiss()
            stopTask()
        }

        builder.setView(view)
        builder.setCancelable(false)
        alertDialog = builder.create()
        alertDialog.show()
    }


    private fun updateMap() {

        if(jobs!= null){
            for(item in jobs!!){

                // Add a marker in Sydney, Australia, and move the camera.
                val sydney = LatLng(item.pickup.latitude.toDouble(), item.pickup.longitude.toDouble())
                mMap.addMarker(MarkerOptions()
                        .position(sydney)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_shiper)))
                        .setTag(item.order_id.toInt())
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,13.0f));
            }
        }

    }

    override fun showError() {
        Var.jobs?.clear()
        this.jobs?.clear()
        updateMap()
    }
    override fun showSuccess(phone_number:String) {
        Toast.makeText(this,"Đã nhận đơn hàng thành công!, kiểm tra trong mục chờ duyệt",Toast.LENGTH_SHORT).show()
        mMap.clear()
        handlerP.getJobs()
        Log.e("phone",phone_number)
        Log.e("nd:","Shiper ${Var.shiper?.fullname}, số điện thoại ${Var.shiper?.mobile} sẽ đến lấy hàng vào lúc ${currentPickedTime} phút")
        //sendSMS(phone_number, "Shiper ${Var.shiper?.fullname}, số điện thoại ${Var.shiper?.mobile} sẽ đến lấy hàng vào lúc ${currentPickedTime}phút");
        SMSUtils.sendSMS(this,phone_number,"Shiper ${Var.shiper?.fullname}, số điện thoại ${Var.shiper?.mobile} sẽ đến lấy hàng vào lúc ${currentPickedTime} phút")
    }

    //Sends an SMS message to another device

    private fun sendSMS(phoneNumber: String, message: String) {
        val sms = SmsManager.getDefault()
        try{
            sms.sendTextMessage(phoneNumber, null, message, null, null)

        }catch (e:Exception){
            Log.e("sendSMS:",e.toString())
        }
    }

    override fun returnJobs(jobs: ArrayList<Job>) {
        Var.jobs = jobs
        this.jobs = jobs
        updateMap()
    }


}
