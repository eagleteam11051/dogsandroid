package com.tbm.dogs.activities.thongbao

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.tbm.dogs.R

class ChiTietThongBao : AppCompatActivity() {

    lateinit var tTitle: TextView
    lateinit var tBody: TextView

    private fun init() {
        tTitle = findViewById(R.id.tTitle)
        tBody = findViewById(R.id.tBody)
    }

    private fun initNotification() {
        val it = intent
        tTitle.text = it.getStringExtra("title")
        tBody.text = it.getStringExtra("body")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chitiet_thongbao)
        init()
        initNotification()
    }
}
