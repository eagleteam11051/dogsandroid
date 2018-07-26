package com.tbm.dogs.adapters.thongbao

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tbm.dogs.R
import com.tbm.dogs.activities.thongbao.ChiTietThongBao
import com.tbm.dogs.model.obj.ThongBaoObj

class AdapterThongBao(var arrayThongBao:ArrayList<ThongBaoObj>,var context : Context): RecyclerView.Adapter<AdapterThongBao.ViewHolder>() {


    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.tNoiDung.text = arrayThongBao[p1].message
        p0.tThoiGian.text = arrayThongBao[p1].created_time
        p0.setOnClick(arrayThongBao[p1])
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AdapterThongBao.ViewHolder {
        val layoutInflater = LayoutInflater.from(p0.context)
        val view = layoutInflater.inflate(R.layout.layout_item_thongbao,p0,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayThongBao.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setOnClick(thongBaoObj: ThongBaoObj) {
            itemView.setOnClickListener {
                context.startActivity(Intent(context,ChiTietThongBao::class.java).putExtra("thongbao",thongBaoObj))
            }
        }

        var tNoiDung:TextView = itemView.findViewById(R.id.tNoiDung)
        var tThoiGian:TextView = itemView.findViewById(R.id.tThoiGian)
    }
}