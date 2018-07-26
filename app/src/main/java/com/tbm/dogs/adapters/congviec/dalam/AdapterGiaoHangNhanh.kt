package com.tbm.dogs.adapters.congviec.dalam

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tbm.dogs.Helper.Var
import com.tbm.dogs.R

class AdapterGiaoHangNhanh: RecyclerView.Adapter<AdapterGiaoHangNhanh.ViewHolderDaLam>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolderDaLam {
        val layoutInflater = LayoutInflater.from(p0.context)
        return ViewHolderDaLam(layoutInflater.inflate(R.layout.item_giaohang_nhanh,p0,false))
    }

    override fun getItemCount(): Int {
        return Var.jobsDone?.size ?: 0
    }

    override fun onBindViewHolder(p0: ViewHolderDaLam, p1: Int) {
        val job = Var.jobsDone?.get(p1)
        p0.tMaDon.text = "Mã đơn: ${job?.order_code}"
        p0.tDescription.text = "Mô tả: ${job?.note}"
        p0.tTime.text = "${job?.create_time}"
        p0.tKhoiLuong.text = "Khối lượng: ${job?.weight}Kg"
        p0.tKhoangCach.text = "Khoảng cách: ${job?.distance}Km"
        p0.tGiaTri.text = "Giá trị hàng: ${job?.money_first}đ"
        p0.tTienNhan.text = "Tiền được nhận: ${job?.fee}đ"
    }


    class ViewHolderDaLam(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tMaDon:TextView = itemView.findViewById(R.id.tMaDon)
        val tDescription:TextView = itemView.findViewById(R.id.tDescription)
        val tTime:TextView = itemView.findViewById(R.id.tTime)
        val tKhoiLuong:TextView = itemView.findViewById(R.id.tKhoiLuong)
        val tKhoangCach:TextView = itemView.findViewById(R.id.tKhoangCach)
        val tGiaTri:TextView = itemView.findViewById(R.id.tGiaTri)
        val tTienNhan:TextView = itemView.findViewById(R.id.tTienNhan)
    }
}