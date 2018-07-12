package com.tbm.dogs.adapters.congviec

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import com.tbm.dogs.R
import com.tbm.dogs.activities.congviec.chitietcongviec.ChiTietCongViec
import com.tbm.dogs.activities.congviec.danglam.HandlerP
import com.tbm.dogs.model.obj.Job
import java.util.*

class AdapterCongViec(internal var context: Context, internal var headerGroup: List<String>, internal var child: HashMap<String, List<Job>>,var mode:Int,var handlerP: HandlerP? = null) : BaseExpandableListAdapter() {

    lateinit var child: HashMap<String, List<Job>>

    override fun getGroupCount(): Int {
        return headerGroup.size
    }

    override fun getChildrenCount(i: Int): Int {
        return child[headerGroup[i]]!!.size
    }

    override fun getGroup(i: Int): Any {
        return headerGroup[i]
    }

    override fun getChild(i: Int, i1: Int): Any {
        return child[headerGroup[i]]!!.get(i1)
    }

    override fun getGroupId(i: Int): Long {
        return i.toLong()
    }

    override fun getChildId(i: Int, i1: Int): Long {
        return i1.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(i: Int, b: Boolean, view: View?, viewGroup: ViewGroup): View {
        var view = view
        if (view == null) {
            val li = LayoutInflater.from(context)
            view = li.inflate(R.layout.group_layout, viewGroup, false)
        }
        val tHeader = view!!.findViewById<View>(R.id.tHeader) as TextView
        tHeader.text = headerGroup[i]
        return view
    }

    override fun getChildView(i: Int, i1: Int, b: Boolean, view: View?, viewGroup: ViewGroup): View {
        var view = view
        if(mode == 0){
            //job

            if (view == null) {
                val li = LayoutInflater.from(context)
                view = li.inflate(R.layout.item_row_layout, viewGroup, false)
            }

            val tItem = view!!.findViewById<TextView>(R.id.tItem)
            val tTime = view.findViewById<TextView>(R.id.tTime)
            tItem.text = (getChild(i, i1) as Job).note
            tTime.text = (getChild(i, i1) as Job).create_time
        }else{
            //job working
            if (view == null) {
                val li = LayoutInflater.from(context)
                view = li.inflate(R.layout.item_row_layout_working, viewGroup, false)
            }

            val tItem = view!!.findViewById<TextView>(R.id.tItem)
            val tTime = view.findViewById<TextView>(R.id.tTime)
            val bNhanHang = view.findViewById<Button>(R.id.bNhanHang)
            bNhanHang.setOnClickListener {
                Log.e("row","nhan hang")
                handlerP?.checkinNhanHang((getChild(i, i1) as Job))
            }
            val bGiaoHang = view.findViewById<Button>(R.id.bGiaoHang)
            bGiaoHang.setOnClickListener {
                Log.e("row","giao hang")
                handlerP?.checkinGiaoHang((getChild(i, i1) as Job))
            }
            val layoutRow = view.findViewById<RelativeLayout>(R.id.layout_row)
            layoutRow.setOnClickListener {
                Log.e("layout_row","clicked")
                context.startActivity(Intent(context, ChiTietCongViec::class.java).putExtra("job",getChild(i, i1) as Job))
            }
            tItem.text = (getChild(i, i1) as Job).note
            tTime.text = (getChild(i, i1) as Job).create_time
        }

        return view
    }

    override fun isChildSelectable(i: Int, i1: Int): Boolean {
        return true
    }

    companion object {
        private val TAG = "CustomAdapter"
    }
}