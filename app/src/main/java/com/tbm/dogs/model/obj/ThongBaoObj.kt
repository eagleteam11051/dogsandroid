package com.tbm.dogs.model.obj

import java.io.Serializable

/**
 * "id": "439",
"hero_id": "16",
"customer_id": "0",
"message": "Đơn hàng #4B5WTN đã bị khách hủy",
"created_time": "16:29, 11-07-2018",
"status": "2"
 */
class ThongBaoObj(var id:Int,var hero_id:Int,var customer_id:Int,var message:String,var created_time:String,var status:Int): Serializable