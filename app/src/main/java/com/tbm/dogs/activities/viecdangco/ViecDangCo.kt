package com.tbm.dogs.activities.viecdangco

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.tbm.dogs.Helper.Var
import com.tbm.dogs.R
import com.tbm.dogs.activities.main.Main
import com.tbm.dogs.model.obj.Job


class ViecDangCo : AppCompatActivity(), OnMapReadyCallback, GoogleMap.InfoWindowAdapter, Results, GoogleMap.OnMarkerClickListener {



    lateinit var mMap: GoogleMap
    var jobs: ArrayList<Job>? = null
    lateinit var handlerP: HandlerP
    lateinit var groupJob: ArrayList<Job>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viec_dang_co)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        handlerP = HandlerP(this)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home ->{
                Log.e("home","clicked")
                startActivity(Intent(this,Main::class.java))
                finish()
            }

            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onBackPressed() {
        startActivity(Intent(this,Main::class.java))
        finish()
        super.onBackPressed()
    }
    override fun onNewIntent(intent: Intent) {
        //        Toast.makeText(this, intent.getStringExtra("data"), Toast.LENGTH_SHORT).show();
        //        LatLng sydney = new LatLng(Integer.parseInt(intent.getStringExtra("data").split(",")[0]), Integer.parseInt(intent.getStringExtra("data").split(",")[1]));
        //        mMap.addMarker(new MarkerOptions()
        //                .position(sydney)
        //                .snippet("Population: 4,137,400")
        //                .title("Marker in Sydney")
        //                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_a)))
        //                .setTag(0);
        //        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,15.0f));
        //reload data
        handlerP.getJobs()
        super.onNewIntent(intent)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.isMyLocationEnabled = true
        //        // Add a marker in Sydney, Australia, and move the camera.
        //        LatLng sydney = new LatLng(21, 120);
        //        mMap.addMarker(new MarkerOptions()
        //                .position(sydney)
        //                .snippet("Population: 4,137,400")
        //                .title("Marker in Sydney")
        //                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_a)))
        //                .setTag(0);
        //        sydney = new LatLng(21.585219, 105.806863);
        //        mMap.addMarker(new MarkerOptions()
        //                .position(sydney)
        //                .snippet("Population: 4,137,400")
        //                .title("hihi")
        //                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_shiper)))
        //                .setTag(1);

        mMap.setMaxZoomPreference(50.0f)
        mMap.setMinZoomPreference(5.0f)
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15.0f));
        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,15.0f));
        mMap.setOnMarkerClickListener(this)
        mMap.setInfoWindowAdapter(this)
        this.jobs = Var.jobs
        updateMap()
    }


    override fun onMarkerClick(p0: Marker?): Boolean {
        showInfo(handlerP.getJob(p0?.tag.toString()))
        return true
    }

    private fun showInfo(job: Job?) {
        this.groupJob = handlerP.getGroupJob(job)
        var job = groupJob[0]
        var index = 0

        val builder = AlertDialog.Builder(this)
        var alertDialog:AlertDialog? = null

        val view = layoutInflater.inflate(R.layout.layout_booking,null)

        val tDiemNhan:TextView = view.findViewById(R.id.tDiemNhan)

        val tDiemGiao: TextView = view.findViewById(R.id.tDiemGiao)

        val tKhoangCach: TextView = view.findViewById(R.id.tKhoangCach)

        val sGio: Spinner = view.findViewById(R.id.sGio)
        val sPhut: Spinner = view.findViewById(R.id.sPhut)
        val bNhanViec:Button = view.findViewById(R.id.bNhanViec)
        val layoutNext:RelativeLayout = view.findViewById(R.id.layout_next_job)
        val bSau:Button = view.findViewById(R.id.bSau)
        val bTruoc:Button = view.findViewById(R.id.bTruoc)
        val tIndex:TextView = view.findViewById(R.id.tIndex)

        fun date():String{
            val gioId = sGio.selectedItemPosition

            val phutId = sPhut.selectedItemPosition
            var gio:String = ""
            var phut:String = ""
            when(gioId){
                0 -> gio = "00"
                1 -> gio = "01"
                2 -> gio = "02"
                3 -> gio = "03"
                4 -> gio = "04"
                5 -> gio = "05"
                6 -> gio = "06"
                7 -> gio = "07"
                8 -> gio = "08"
            }
            when(phutId){
                0 -> phut = "05"
                1 -> phut = "10"
                2 -> phut = "15"
                3 -> phut = "20"
                4 -> phut = "25"
                5 -> phut = "30"
                6 -> phut = "40"
                7 -> phut = "50"
            }
            Log.e("time:","$gio:$phut")
            return "$gio:$phut"
        }
        fun update(){
            tDiemNhan.text = "Điểm Nhận: ${job?.pickup!!.address}"
            tDiemGiao.text = "Điểm Giao: ${job?.dropoff.address}"
            tKhoangCach.text =  "Khoảng Cách: ${job.distance}Km"
            tIndex.text = "${index+1}/${groupJob.size}"
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
            alertDialog?.dismiss()
            //date()
            handlerP.AcceptOrder(job.order_id,Var.shiper?.hero_id,date())
        }
        val bTuChoi:Button = view.findViewById(R.id.bTuChoi)
        bTuChoi.setOnClickListener {
            alertDialog?.dismiss()
        }

        val adapterGio = ArrayAdapter.createFromResource(this,
                R.array.gio, android.R.layout.simple_spinner_item)
        adapterGio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val adapterPhut = ArrayAdapter.createFromResource(this,
                R.array.phut, android.R.layout.simple_spinner_item)
        adapterPhut.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sGio.adapter = adapterGio
        sPhut.adapter = adapterPhut
        builder.setView(view)
        builder.setCancelable(false)
        alertDialog = builder.create()
        alertDialog.show()
    }

    override fun getInfoWindow(marker: Marker): View? {
        return null
    }

    override fun getInfoContents(marker: Marker): View? {
        return null
    }

    private fun updateMap() {
        if(jobs!= null){
            for(item in jobs!!){

                // Add a marker in Sydney, Australia, and move the camera.
                val sydney = LatLng(item.pickup.latitude.toDouble(), item.pickup.longitude.toDouble());
                mMap.addMarker(MarkerOptions()
                        .position(sydney)
                        .snippet(item.shipping_type)
                        .title(item.note)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_shiper)))
                        .setTag(item.order_id.toInt())
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,13.0f));
            }
        }

    }

    override fun showError() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override fun showSuccess() {
        Toast.makeText(this,"Đã nhận đơn hàng thành công!, kiểm tra trong mục chờ duyệt",Toast.LENGTH_SHORT).show()
    }

    override fun returnJobs(jobs: ArrayList<Job>) {
        Var.jobs = jobs
        this.jobs = jobs
        updateMap()
    }
}
