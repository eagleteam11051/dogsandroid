package com.tbm.dogs.adapters.thongbao

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tbm.dogs.R
import com.tbm.dogs.model.obj.ThongBaoObj

class AdapterThongBao(var arrayThongBao:ArrayList<ThongBaoObj>): RecyclerView.Adapter<AdapterThongBao.ViewHolder>() {


    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.tNoiDung.text = arrayThongBao[p1].content
        p0.tThoiGian.text = arrayThongBao[p1].time
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AdapterThongBao.ViewHolder {
        val layoutInflater = LayoutInflater.from(p0.context)
        val view = layoutInflater.inflate(R.layout.layout_item_thongbao,p0,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayThongBao.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tNoiDung:TextView
        var tThoiGian:TextView
        init {
            tNoiDung = itemView.findViewById(R.id.tNoiDung)
            tThoiGian = itemView.findViewById(R.id.tThoiGian)
        }
    }
}