package com.tbm.dogs.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.tbm.dogs.R
import com.tbm.dogs.model.obj.Job
import java.util.*

class AdapterCongViec(internal var context: Context, internal var headerGroup: List<String>, internal var child: HashMap<String, List<Job>>) : BaseExpandableListAdapter() {

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
        if (view == null) {
            val li = LayoutInflater.from(context)
            view = li.inflate(R.layout.item_row_layout, viewGroup, false)
        }

        val tItem = view!!.findViewById<View>(R.id.tItem) as TextView
        val tTime = view.findViewById<View>(R.id.tTime) as TextView
        tItem.text = (getChild(i, i1) as Job).note
        tTime.text = (getChild(i, i1) as Job).create_time
        return view
    }

    override fun isChildSelectable(i: Int, i1: Int): Boolean {
        return true
    }

    companion object {
        private val TAG = "CustomAdapter"
    }
}
