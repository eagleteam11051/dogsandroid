package com.tbm.dogs.activities.congviec.dalam.chitiet

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import com.tbm.dogs.R
import com.tbm.dogs.adapters.congviec.dalam.AdapterGiaoHangNhanh

class ChiTietGiaoHangNhanh : AppCompatActivity() {
    var lGiaoHangNhanh: RecyclerView? = null
    private var adapterGiaoHangNhanh: AdapterGiaoHangNhanh? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chi_tiet_giao_hang_nhanh)
        initView()
        configList()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    private fun configList() {
        lGiaoHangNhanh?.setHasFixedSize(true)
        lGiaoHangNhanh?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        lGiaoHangNhanh?.adapter = adapterGiaoHangNhanh
    }

    private fun initView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Lịch sử đơn hàng"
        lGiaoHangNhanh = findViewById(R.id.lGiaoHangNhanh)
        adapterGiaoHangNhanh = AdapterGiaoHangNhanh()
    }
}
