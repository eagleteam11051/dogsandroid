package com.tbm.dogs.activities.congviec.chitietcongviec

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.tbm.dogs.Helper.Locations
import com.tbm.dogs.R
import com.tbm.dogs.model.obj.Job
import kotlinx.android.synthetic.main.activity_chitiet_congviec.*
import kotlinx.android.synthetic.main.fragment_job_details.view.*
import java.text.DecimalFormat





class ChiTietCongViec : AppCompatActivity() {


    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    lateinit var job:Job


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chitiet_congviec)
        job = intent.getSerializableExtra("job") as Job
        Log.e("job_id",job.order_id)
        setSupportActionBar(toolbar)
        val arrayFragment = ArrayList<Fragment>()
        val detail = DetailFragment.newInstance(job)
        val mapF = MapFragment.newInstance(job)
        arrayFragment.add(detail)
        arrayFragment.add(mapF)
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager,arrayFragment)

        container.adapter = mSectionsPagerAdapter
        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }


    }



//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_job_details, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        val id = item.itemId
//
//        if (id == R.id.action_settings) {
//            return true
//        }
//
//        return super.onOptionsItemSelected(item)
//    }

    inner class SectionsPagerAdapter(fm: FragmentManager, array:ArrayList<Fragment>) : FragmentPagerAdapter(fm) {



        lateinit var arrayFragment: ArrayList<Fragment>

        init {
            arrayFragment = array
        }

        override fun getItem(position: Int): Fragment {
            return arrayFragment[position]
        }

        override fun getCount(): Int {
            return 2
        }
    }

    class DetailFragment() : Fragment() {

        lateinit var job:Job

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            this.job = arguments?.getSerializable("job") as Job
            Log.e("job:",job.order_id)
            val rootView = inflater.inflate(R.layout.fragment_job_details, container, false)
            rootView.tDiemNhan.text = "Điểm Nhận: ${job.pickup.address}"
            rootView.tDiemGiao.text = "Điểm Giao: ${job.dropoff.address}"
            rootView.tKhoangCach.text = "Khoảng Cách: ${job.distance}Km"
            rootView.tSDT.text = "Số Điện Thoại: ${job.pickup.mobile}"
            rootView.tYeuCau.text = "Yêu Cầu: ${job.note}"
            val df2 = DecimalFormat("#,###,###,###")
            rootView.tThuHo.text = "Tiền Thu Hộ: ${df2.format(job.money_first.toDouble())}đ"
            return rootView
        }
        companion object {
            fun newInstance(job:Job): DetailFragment {
                val fragment = DetailFragment()
                val args = Bundle()
                args.putSerializable("job",job)
                fragment.arguments = args
                return fragment
            }
        }

    }
    class MapFragment: Fragment(), OnMapReadyCallback, Results {


        lateinit var handlerP: HandlerP
        lateinit var job:Job
        lateinit var mMap: GoogleMap
        private var originMarkers = ArrayList<Marker>()
        private var destinationMarkers = ArrayList<Marker>()
        private var polylinePaths = ArrayList<Polyline>()

        @SuppressLint("MissingPermission")
        override fun onMapReady(googleMap: GoogleMap) {
            handlerP = HandlerP(this)
            handlerP.getDirection(this.job)
            mMap = googleMap
            mMap.isMyLocationEnabled = true
            mMap.setOnMyLocationButtonClickListener {
                if(!Locations.isLocationEnabled(context!!)){
                    showLocationEnable()
                }
                false
            }



            mMap.setMaxZoomPreference(50.0f)
            mMap.setMinZoomPreference(5.0f)
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15.0f));
//            mMap.setOnMarkerClickListener(this)
//            mMap.setInfoWindowAdapter(this)

        }
        private fun showLocationEnable() {
            val builder = AlertDialog.Builder(context)
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

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            this.job = arguments?.getSerializable("job") as Job
            val rootView = inflater.inflate(R.layout.fragment_map_details, container, false)
            try{
                val mapFragment = childFragmentManager!!.findFragmentById(R.id.mapDetail) as SupportMapFragment?
                mapFragment!!.getMapAsync(this)
            }catch (e:Exception){
                Log.e("exception:",e.toString())
            }



//            rootView.section_label.text = getString(R.string.section_format, arguments?.getInt(ARG_SECTION_NUMBER))
            return rootView
        }

        companion object {
            fun newInstance(job:Job): MapFragment {
                val fragment = MapFragment()
                val args = Bundle()
                args.putSerializable("job",job)
                fragment.arguments = args
                return fragment
            }
        }

        override fun onDirectionFinderSuccess(routes: ArrayList<Route>) {

            for (route in routes) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 15f))
//                (findViewById(R.id.tvDuration) as TextView).text = route.duration!!.text
//                (findViewById(R.id.tvDistance) as TextView).text = route.distance!!.text

                originMarkers.add(mMap.addMarker(MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                        .title(route.startAddress)
                        .position(route.startLocation!!)))
                destinationMarkers.add(mMap.addMarker(MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                        .title(route.endAddress)
                        .position(route.endLocation!!)))

                val polylineOptions = PolylineOptions().geodesic(true).color(Color.BLUE).width(10f)

                for (i in route.points!!.indices)
                    polylineOptions.add(route.points!![i])

                polylinePaths.add(mMap.addPolyline(polylineOptions))
            }
        }
    }
}
