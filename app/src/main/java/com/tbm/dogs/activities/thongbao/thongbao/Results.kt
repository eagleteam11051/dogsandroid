package com.tbm.dogs.activities.thongbao.thongbao

import com.tbm.dogs.model.obj.ThongBaoObj

interface Results {
    fun showError()
    fun returnThongBaos(thongBaos: ArrayList<ThongBaoObj>)
    fun dismisProgress()
}