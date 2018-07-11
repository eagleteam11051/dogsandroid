package com.tbm.dogs.activities.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.tbm.dogs.Helper.Shared
import com.tbm.dogs.Helper.Var
import com.tbm.dogs.Helper.Var.shiper
import com.tbm.dogs.R
import com.tbm.dogs.activities.congviec.ViecDaLam
import com.tbm.dogs.activities.congviec.ViecDangCho
import com.tbm.dogs.activities.congviec.ViecDangLam
import com.tbm.dogs.activities.congviec.viecdangco.ViecDangCo
import com.tbm.dogs.activities.thongbao.ThongBao
import com.tbm.dogs.model.obj.Job
import com.tbm.dogs.model.obj.Shiper
import kotlinx.android.synthetic.main.main.*

class Main : AppCompatActivity(), View.OnClickListener, Results {

    private lateinit var gson: Gson
    private lateinit var shared: Shared
    private lateinit var handlerP: HandlerP

    internal fun init() {
        initShiper()
        initData()
        layout_viec_dang_lam.setOnClickListener(this)
        layout_viec_dang_co.setOnClickListener(this)
        layout_viec_da_lam.setOnClickListener(this)
        layout_viec_dang_cho.setOnClickListener(this)

    }

    private fun initData() {
        tName.text = shiper!!.fullname
        tId.text = "ID: " + shiper!!.hero_id
        tMoney.text = "Tài Khoản: " + shiper!!.balance
        tAddress.text = "Địa Chỉ: " + shiper!!.address
        Picasso.get().load(shiper!!.image).into(imgAvatar)
        shared = Shared(this)
        handlerP = HandlerP(this)
    }

    internal fun initShiper() {
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
    }

    override fun onStart() {
        super.onStart()
        Log.e("onStart","run")
        initNumberJob()
        handlerP.getJobs()
        handlerP.getJobsWaiting()
        handlerP.getJobsWorking()
        handlerP.getJobsDone()
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
        handlerP.getJobs()
        super.onNewIntent(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        startActivity(Intent(this,ThongBao::class.java))
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
    override fun returnJobsWorking(jobs: ArrayList<Job>) {
        Var.jobsWorking = jobs
        tNumberDangLam.text = ""+jobs.size
    }

    override fun returnJobsDone(jobs: ArrayList<Job>) {
        Var.jobsDone = jobs
        tNumberDaLam.text = ""+jobs.size
    }
}
