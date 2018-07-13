package com.tbm.dogs.activities.congviec.danglam

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.widget.ExpandableListView
import android.widget.Toast
import com.google.android.gms.location.*
import com.tbm.dogs.Helper.Var
import com.tbm.dogs.R
import com.tbm.dogs.activities.congviec.chitietcongviec.ChiTietCongViec
import com.tbm.dogs.adapters.congviec.AdapterCongViec
import com.tbm.dogs.model.obj.Job
import java.util.*
import kotlin.collections.ArrayList

class ViecDangLam : AppCompatActivity(),Results {
    override fun update(b: Boolean) {
        update = b
    }

    override fun returnJobsWorking(jobs: ArrayList<Job>) {
        Var.jobsWorking = jobs
        listGiaoHang.clear()
        if(Var.jobsWorking != null){
            listGiaoHang.addAll(Var.jobsWorking!!)
        }
        data[listHeader[0]] = listGiaoHang
        adapter.mchild = data
        adapter.notifyDataSetChanged()
    }

    override fun showErrorJobsWorking() {
        Var.jobsWorking?.clear()
    }

    override fun dismisDialog() {
        progressDialog.dismiss()
    }

    override fun showDialog() {
        progressDialog.show()
    }

    override fun showError(response: String) {
        Toast.makeText(this,response,Toast.LENGTH_SHORT).show()
    }

    override fun showSuccess(mode: Int) {
        Toast.makeText(this,"Cập nhật trạng thái thành công!",Toast.LENGTH_SHORT).show()
        reload()
    }

    override fun requestUpdate(job: Job, i: Int) {
        this.job = job
        this.mode = i
        requestLocationUpdate()
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

    lateinit var expandableListView: ExpandableListView
    lateinit var data: HashMap<String, List<Job>>
    lateinit var handlerP:HandlerP
    var lat = ""
    var lng = ""
    lateinit var locationRequest:LocationRequest
    lateinit var locationCallback: LocationCallback
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var job: Job? = null
    var mode:Int = 0
    lateinit var progressDialog: ProgressDialog
    val listGiaoHang = ArrayList<Job>()
    val listHeader = ArrayList<String>()
    lateinit var adapter:AdapterCongViec
    var update = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viec_dang_lam)
        init()
        initList()
        buildLocationRequest()
        buildLocationCallBack()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        requestLocationUpdate()
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Đang xử lý...")
    }

    private fun reload(){
        handlerP.getJobsWorking()
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }


    @SuppressLint("MissingPermission")
    public fun requestLocationUpdate(){
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper())
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
                if(this@ViecDangLam.job != null && update){
                    handlerP.checkin(this@ViecDangLam.job!!,mode,lat,lng)
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
            fastestInterval = 3000
            smallestDisplacement = 10f
        }


    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    private fun init() {
        expandableListView = findViewById(R.id.expan_cong_viec)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Đơn Hàng Đang Làm"
        handlerP = HandlerP(this,this)
    }

    private fun initList() {
        //prepare data

        //data for header group

        listHeader.add("Giao hàng nhanh")
//        listHeader.add("Cứu hộ")
//        listHeader.add("Vận chuyển đồ")


        //data for child
        data = HashMap()

        if(Var.jobsWorking != null){
            listGiaoHang.addAll(Var.jobsWorking!!)
        }


//        val listCuuHo = ArrayList<String>()
//        listCuuHo.add("Hết xăng")
//        listCuuHo.add("Hỏng xe")
//
//        val listVanChuyenDo = ArrayList<String>()
//        listVanChuyenDo.add("Chuyen phongjdafkjaskdfjasljgjdafkjaskdfjasljkfdljalsdjflkajgjdafkjaskdfjasljkfdljalsdjflkajgjdafkjaskdfjasljkfdljalsdjflkajgjdafkjaskdfjasljkfdljalsdjflkajkfdljalsdjflkajsldkfjaslkjdfljalfjlkasjdklfsdf")
//        listVanChuyenDo.add("Chuyen nha tro")




        data[listHeader[0]] = listGiaoHang
//        data[listHeader[1]] = listCuuHo
//        data[listHeader[2]] = listVanChuyenDo

        //setup adapter for ExpandableListView
        adapter = AdapterCongViec(this, listHeader, data, 1,handlerP)

        expandableListView.setAdapter(adapter)
        expandableListView.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            Log.e(TAG, "onChildClick: " + listHeader[groupPosition] + ", " + data[listHeader[groupPosition]]!!.get(childPosition))

            startActivity(Intent(this@ViecDangLam, ChiTietCongViec::class.java).putExtra("job",data[listHeader[groupPosition]]!!.get(childPosition)))
            true
        }

        expandableListView.setOnGroupClickListener { parent, v, groupPosition, id ->
            Log.e(TAG, "onGroupClick: $groupPosition")
            false
        }

    }
    companion object {
        private val TAG = "JobWorking"
    }
}
