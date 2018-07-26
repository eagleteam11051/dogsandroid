package com.tbm.dogs.activities.thongbao.thongbao

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.tbm.dogs.R
import com.tbm.dogs.adapters.thongbao.AdapterThongBao
import com.tbm.dogs.model.obj.ThongBaoObj

class ThongBao : AppCompatActivity(), Results {
    override fun dismisProgress() {
        progressDialog.dismiss()
    }

    override fun showError() {
        Toast.makeText(this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show()
    }

    override fun returnThongBaos(thongBaos: ArrayList<ThongBaoObj>) {
        viewAdapter.arrayThongBao.addAll(thongBaos)
        viewAdapter.notifyDataSetChanged()
        if (thongBaos.size < 10) {
            isLoadMore = false
        }
    }

    /////////////////////

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: AdapterThongBao
    private lateinit var viewManager: LinearLayoutManager
    private lateinit var handlerP: HandlerP
    private var indexStatus = 0
    private var isLoadMore = true
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thong_bao)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        progressDialog = ProgressDialog(this).apply {
            setMessage("Đang tải thông báo, xin đợi!")
            setCancelable(false)
        }

        viewManager = LinearLayoutManager(this)
        val arrayThongBao = ArrayList<ThongBaoObj>()
        viewAdapter = AdapterThongBao(arrayThongBao, this)

        recyclerView = findViewById<RecyclerView>(R.id.rListThongBao).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (!recyclerView.canScrollVertically(1)) {
                        Log.e("last", "...")
                        if (isLoadMore) {
                            indexStatus += 10
                            progressDialog.show()
                            handlerP.getThongBao(indexStatus)
                        }
                    }
                }
            })

        }
        handlerP = HandlerP(this, this)
        progressDialog.show()
        handlerP.getThongBao(indexStatus)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

}
