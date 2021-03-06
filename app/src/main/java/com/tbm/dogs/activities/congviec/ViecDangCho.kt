package com.tbm.dogs.activities.congviec

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.widget.ExpandableListView
import com.tbm.dogs.Helper.Var
import com.tbm.dogs.R
import com.tbm.dogs.adapters.congviec.AdapterCongViec
import com.tbm.dogs.model.obj.Job
import java.util.*

class ViecDangCho : AppCompatActivity() {
    private lateinit var expandableListView: ExpandableListView
    lateinit var data: HashMap<String, List<Job>>

    private fun init() {
        expandableListView = findViewById(R.id.expan_cong_viec)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Đơn Hàng Đang Chờ Phê Duyệt"
    }

    private fun initList() {
        //data for header group
        val listHeader = ArrayList<String>()
        listHeader.add("Giao hàng nhanh")
//        listHeader.add("Cứu hộ")
//        listHeader.add("Vận chuyển đồ")


        //data for child
        data = HashMap()
        val listGiaoHang = ArrayList<Job>()
        if(Var.jobsWaiting != null){
            listGiaoHang.addAll(Var.jobsWaiting!!)
        }

//
//        val listCuuHo = ArrayList<String>()
//        listCuuHo.add("Hết xăng")
//        listCuuHo.add("Hỏng xe")
//
//        val listVanChuyenDo = ArrayList<String>()
//        listVanChuyenDo.add("Chuyen phongjdafkjaskdfjasljgjdafkjaskdfjasljkfdljalsdjflkajgjdafkjaskdfjasljkfdljalsdjflkajgjdafkjaskdfjasljkfdljalsdjflkajgjdafkjaskdfjasljkfdljalsdjflkajkfdljalsdjflkajsldkfjaslkjdfljalfjlkasjdklfsdf")
//        listVanChuyenDo.add("Chuyen nha tro")




        data[listHeader[0]] = listGiaoHang
//        data[listHeader[1]] = listCuuHo
//        data[listHeader[2]] = listVanChuyenDo

        //setup adapter for ExpandableListView
        val adapter = AdapterCongViec(this, listHeader, data, 0)
        expandableListView.setAdapter(adapter)
        expandableListView.expandGroup(0)
        expandableListView.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            Log.e(TAG, "onChildClick: " + listHeader[groupPosition] + ", " + data[listHeader[groupPosition]]!!.get(childPosition))
            true
        }

        expandableListView.setOnGroupClickListener { parent, v, groupPosition, id ->
            Log.e(TAG, "onGroupClick: $groupPosition")
            false
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viec_dang_cho)
        init()
        initList()
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private val TAG = "JobWaiting"
    }
}
