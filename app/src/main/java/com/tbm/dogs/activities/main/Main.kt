package com.tbm.dogs.activities.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.tbm.dogs.Helper.Shared
import com.tbm.dogs.Helper.Var
import com.tbm.dogs.R
import com.tbm.dogs.activities.ViecDaLam
import com.tbm.dogs.activities.ViecDangCho
import com.tbm.dogs.activities.ViecDangLam
import com.tbm.dogs.activities.viecdangco.ViecDangCo
import com.tbm.dogs.model.obj.Job
import com.tbm.dogs.model.obj.Shiper

class Main : AppCompatActivity(), View.OnClickListener, Results {



    private lateinit var layoutViecDangCo: RelativeLayout
    private lateinit var layoutViecDangCho: RelativeLayout
    private lateinit var layoutViecDangLam: RelativeLayout
    lateinit var layoutViecDaLam: RelativeLayout
    lateinit var imgAvatar: ImageView
    lateinit var tName: TextView
    lateinit var tId: TextView
    lateinit var tMoney: TextView
    lateinit var tAddress: TextView
    lateinit var shiper: Shiper
    lateinit var tNumberTimViec:TextView
    lateinit var tNumberChoDuyet:TextView
    lateinit var tNumberDangLam: TextView
    lateinit var tNumberDaLam: TextView
    lateinit var gson: Gson
    lateinit var shared: Shared
    lateinit var handlerP: HandlerP

    internal fun init() {
        layoutViecDaLam = findViewById(R.id.layout_viec_da_lam)
        layoutViecDangCho = findViewById(R.id.layout_viec_dang_cho)
        layoutViecDangCo = findViewById(R.id.layout_viec_dang_co)
        layoutViecDangLam = findViewById(R.id.layout_viec_dang_lam)
        imgAvatar = findViewById(R.id.imgAvatar)
        tName = findViewById(R.id.tName)
        tId = findViewById(R.id.tId)
        tMoney = findViewById(R.id.tMoney)
        tAddress = findViewById(R.id.tAddress)
        tNumberChoDuyet = findViewById(R.id.tNumberChoDuyet)
        tNumberDaLam = findViewById(R.id.tNumberDaLam)
        tNumberDangLam = findViewById(R.id.tNumberDangLam)
        tNumberTimViec = findViewById(R.id.tNumberTimViec)
        initUser()
        initData()
        layoutViecDangLam.setOnClickListener(this)
        layoutViecDangCo.setOnClickListener(this)
        layoutViecDaLam.setOnClickListener(this)
        layoutViecDangCho.setOnClickListener(this)
    }

    private fun initData() {
        tName.text = shiper.fullname
        tId.text = "ID: " + shiper.hero_id
        tMoney.text = "Tài Khoản: " + shiper.balance
        tAddress.text = "Địa Chỉ: " + shiper.address
        Picasso.get().load(shiper.image).into(imgAvatar)
        shared = Shared(this)
        handlerP = HandlerP(this)
    }

    internal fun initUser() {
        if(Var.shiper == null){
            gson = Gson()
            shiper = gson.fromJson(intent.getStringExtra("user"), Shiper::class.java)
            Var.shiper = shiper
            Var.tokenOther = shiper.token
        }else{
            shiper = Var.shiper!!
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.main)
        init()
        handlerP.getJobs()
        handlerP.getJobsWaiting()
    }

    override fun onBackPressed() {
        handlerP.onBackPressed()
    }

    override fun onNewIntent(intent: Intent) {
        handlerP.getJobs()
        super.onNewIntent(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
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
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun returnJobs(jobs: ArrayList<Job>) {
        Var.jobs = jobs
        tNumberTimViec.text = ""+jobs.size
    }
    override fun returnJobsWaiting(jobs: ArrayList<Job>) {
        Var.jobsWaiting = jobs
        tNumberChoDuyet.text = ""+jobs.size
    }
}
