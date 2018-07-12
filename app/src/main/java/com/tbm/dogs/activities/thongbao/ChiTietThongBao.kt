package com.tbm.dogs.activities.thongbao

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.TextView
import com.tbm.dogs.R
import com.tbm.dogs.model.obj.ThongBaoObj

class ChiTietThongBao : AppCompatActivity() {

    lateinit var tTitle: TextView
    lateinit var tBody: TextView
    lateinit var thongBaoObj: ThongBaoObj

    private fun init() {
        tTitle = findViewById(R.id.tTitle)
        tBody = findViewById(R.id.tBody)
    }

    private fun initNotification() {
        tTitle.text = thongBaoObj.created_time
        tBody.text = thongBaoObj.message
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chitiet_thongbao)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        thongBaoObj = intent.getSerializableExtra("thongbao") as ThongBaoObj
        init()
        initNotification()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }
}
