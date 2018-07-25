package com.tbm.dogs.activities.congviec.chitietcongviec

import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.model.LatLng
import com.tbm.dogs.Helper.Var
import com.tbm.dogs.model.obj.Job
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL


class HandlerP(var results: Results,val context: Context) {
    var request:RequestQueue

    init {
        request = Volley.newRequestQueue(context)
    }



    fun getDirection(job: Job) {
        getJsonDirection().execute(Var.MAP_DIRECTION_URL,"${job.pickup.latitude},${job.pickup.longitude}","${job.dropoff.latitude},${job.dropoff.longitude}",Var.MAP_DIRECTION_KEY)
    }

//    fun getJsonDirection(url:String,latlngA:String,latlngB:String,directionKey:String){
//        val stringRequest = object: StringRequest(Request.Method.GET,url,
//                Response.Listener { response ->  parseJSon(response)},
//                Response.ErrorListener { error ->  Log.e("getJsondirection",error.toString())}
//        ){
//            override fun getParams(): MutableMap<String, String> {
//                //create map with key and value of parameters
//                val param = HashMap<String,String>()
//                param.put("origin",latlngA)
//                param.put("destination",latlngB)
//                param.put("key",directionKey)
//                return param
//            }
//        }
//        request.add(stringRequest)
//    }


    inner class getJsonDirection : AsyncTask<String, Void, String>(){
        internal var client = OkHttpClient()
        override fun doInBackground(vararg strings: String): String? {
            val uri = Uri.parse(strings[0])
                    .buildUpon()
                    .appendQueryParameter("origin", strings[1])
                    .appendQueryParameter("destination", strings[2])
                    .appendQueryParameter("key",strings[3])
                    .build()
            Log.e("uridirection", uri.toString())
            var url: URL? = null
            try {
                url = URL(uri.toString())
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }

            val builder = Request.Builder()
            builder.url(url!!)
            //builder.addHeader(Var.HEADER, Var.tokenOther)
            val request = builder.build()
            try {
                val response = client.newCall(request).execute()
                return response.body()!!.string()
            } catch (e: IOException) {
                Log.e("getdirection:", e.toString())
//                results.showError()
            }

            return null
        }

        override fun onPostExecute(s: String) {
            super.onPostExecute(s)
            parseJSon(s)
        }

    }

    fun parseJSon(data: String?) {
        if (data == null)
            return
        Log.e("direction:",data)

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