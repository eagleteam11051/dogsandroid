package com.tbm.dogs.activities.congviec.dalam

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import com.tbm.dogs.R
import com.tbm.dogs.activities.congviec.dalam.chitiet.ChiTietGiaoHangNhanh
import com.tbm.dogs.adapters.congviec.dalam.AdapterGiaoHangNhanh

class ViecDaLam : AppCompatActivity() {
    var bGiaoHangNhanh: CardView? = null
    var lGiaoHangNhanh: RecyclerView? = null
    private var adapterGiaoHangNhanh: AdapterGiaoHangNhanh? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viec_da_lam)
        init()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    private fun initToolBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Đơn Hàng Đã Làm"
    }

    private fun initView() {
        bGiaoHangNhanh = findViewById(R.id.bGiaoHangNhanh)
        lGiaoHangNhanh = findViewById(R.id.lGiaoHangNhanh)
        adapterGiaoHangNhanh = AdapterGiaoHangNhanh()
    }

    private fun configList() {
        lGiaoHangNhanh?.setHasFixedSize(true)
        lGiaoHangNhanh?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        lGiaoHangNhanh?.adapter = adapterGiaoHangNhanh
    }

    private fun initAction() {
        //init action: onclick....
        bGiaoHangNhanh?.setOnClickListener {
            startActivity(Intent(this@ViecDaLam, ChiTietGiaoHangNhanh::class.java))
        }
    }

    private fun init() {
        initToolBar()
        initView()
        initAction()
        configList()
    }
}
