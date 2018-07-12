package com.tbm.dogs.activities.congviec.chitietcongviec

import com.google.android.gms.maps.model.LatLng


class Route(var distance: Distance?, var duration: Duration?, var endAddress:String?, var endLocation:LatLng?, var startAddress:String?, var startLocation:LatLng?, var points:List<LatLng>?) {
    constructor() : this(null,null,null,null,null,null,null)
}