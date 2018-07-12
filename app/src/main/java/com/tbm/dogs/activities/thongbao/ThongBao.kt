package com.tbm.dogs.activities.thongbao

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.tbm.dogs.R
import com.tbm.dogs.adapters.thongbao.AdapterThongBao
import com.tbm.dogs.model.obj.ThongBaoObj

class ThongBao : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: AdapterThongBao
    private lateinit var viewManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thong_bao)

        viewManager = LinearLayoutManager(this)
        val arrayThongBao = ArrayList<ThongBaoObj>()
        arrayThongBao.add(ThongBaoObj("content1","1/1/1"))
        arrayThongBao.add(ThongBaoObj("content2","1/1/133"))
        arrayThongBao.add(ThongBaoObj("content1","1/1/1"))
        arrayThongBao.add(ThongBaoObj("content2","1/1/133"))
        arrayThongBao.add(ThongBaoObj("content1","1/1/1"))
        arrayThongBao.add(ThongBaoObj("content2","1/1/133"))
        arrayThongBao.add(ThongBaoObj("content1","1/1/1"))
        arrayThongBao.add(ThongBaoObj("content2","1/1/133"))
        arrayThongBao.add(ThongBaoObj("content1","1/1/1"))
        arrayThongBao.add(ThongBaoObj("content2","1/1/133"))
        viewAdapter = AdapterThongBao(arrayThongBao)

        recyclerView = findViewById<RecyclerView>(R.id.rListThongBao).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }
    }
}
