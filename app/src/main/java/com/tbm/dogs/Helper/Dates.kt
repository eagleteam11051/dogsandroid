package com.tbm.dogs.Helper

import java.text.SimpleDateFormat
import java.util.*

class Dates {
    public fun startDate():String{
        var date = Calendar.getInstance().time
        val format = SimpleDateFormat("dd-MM-yyyy")
        var l = date.time
        l += 30*(24*60*60*1000)
        date = Date(l)
        return format.format(date)
    }
    public fun endDate():String{
        var date = Calendar.getInstance().time
        val format = SimpleDateFormat("dd-MM-yyyy")
        var l = date.time
        l -= 30*(24*60*60*1000)
        date = Date(l)
        return format.format(date)
    }
}