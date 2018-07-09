package com.tbm.dogs.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.tbm.dogs.R

class NotificationDetails : AppCompatActivity() {

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
        setContentView(R.layout.activity_notification_details)
        init()
        initNotification()
    }
}
