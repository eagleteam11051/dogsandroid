package com.tbm.dogs.activities.congviec.chitietcongviec

import android.content.Context
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.tbm.dogs.Helper.Var
import com.tbm.dogs.Helper.VolleyHelper
import com.tbm.dogs.model.obj.Job
import org.json.JSONObject


class HandlerP(var results: Results, val context: Context) {
    private val volley = VolleyHelper(context)

    fun getDirection(job: Job) {
        volley.requestAPI(Var.MAP_DIRECTION_URL,
                HashMap<String, String>(),
                HashMap<String, String>().apply {
                    put("origin", "${job.pickup.latitude},${job.pickup.longitude}")
                    put("destination", "${job.dropoff.latitude},${job.dropoff.longitude}")
                    put("key", Var.MAP_DIRECTION_KEY)
                },
                { response -> parseJSon(response) },
                { error -> Log.e("getdirection:", error) }
        )
    }

    private fun parseJSon(data: String?) {
        if (data == null)
            return
        Log.e("direction:", data)

        val routes = ArrayList<Route>()
        val jsonData = JSONObject(data)
        val jsonRoutes = jsonData.getJSONArray("routes")
        for (i in 0 until jsonRoutes.length()) {
            val jsonRoute = jsonRoutes.getJSONObject(i)
            val route = Route()

            val overview_polylineJson = jsonRoute.getJSONObject("overview_polyline")
            val jsonLegs = jsonRoute.getJSONArray("legs")
            val jsonLeg = jsonLegs.getJSONObject(0)
            val jsonDistance = jsonLeg.getJSONObject("distance")
            val jsonDuration = jsonLeg.getJSONObject("duration")
            val jsonEndLocation = jsonLeg.getJSONObject("end_location")
            val jsonStartLocation = jsonLeg.getJSONObject("start_location")

            route.distance = Distance(jsonDistance.getString("text"), jsonDistance.getInt("value"))
            route.duration = Duration(jsonDuration.getString("text"), jsonDuration.getInt("value"))
            route.endAddress = jsonLeg.getString("end_address")
            route.startAddress = jsonLeg.getString("start_address")
            route.startLocation = LatLng(jsonStartLocation.getDouble("lat"), jsonStartLocation.getDouble("lng"))
            route.endLocation = LatLng(jsonEndLocation.getDouble("lat"), jsonEndLocation.getDouble("lng"))
            route.points = decodePolyLine(overview_polylineJson.getString("points"))

            routes.add(route)
        }
        results.onDirectionFinderSuccess(routes)
    }

    private fun decodePolyLine(poly: String): List<LatLng> {
        val len = poly.length
        var index = 0
        val decoded = ArrayList<LatLng>()
        var lat = 0
        var lng = 0

        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = poly[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat

            shift = 0
            result = 0
            do {
                b = poly[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            decoded.add(LatLng(
                    lat / 100000.0, lng / 100000.0
            ))
        }

        return decoded
    }
}