package com.tbm.dogs.activities.viecdangco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.tbm.dogs.R;
import com.tbm.dogs.model.obj.Job;

import java.util.ArrayList;

public class ViecDangCo extends AppCompatActivity  implements OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.InfoWindowAdapter
        ,Results{

    private GoogleMap mMap;
    private ArrayList<Job> jobs;
    private HandlerP handlerP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viec_dang_co);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        handlerP = new HandlerP(this);
        handlerP.getJobs();
    }

    protected void onNewIntent(Intent intent) {
//        Toast.makeText(this, intent.getStringExtra("data"), Toast.LENGTH_SHORT).show();
//        LatLng sydney = new LatLng(Integer.parseInt(intent.getStringExtra("data").split(",")[0]), Integer.parseInt(intent.getStringExtra("data").split(",")[1]));
//        mMap.addMarker(new MarkerOptions()
//                .position(sydney)
//                .snippet("Population: 4,137,400")
//                .title("Marker in Sydney")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_a)))
//                .setTag(0);
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,15.0f));
        //reload data

        super.onNewIntent(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//
//        // Add a marker in Sydney, Australia, and move the camera.
//        LatLng sydney = new LatLng(21, 120);
//        mMap.addMarker(new MarkerOptions()
//                .position(sydney)
//                .snippet("Population: 4,137,400")
//                .title("Marker in Sydney")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_a)))
//                .setTag(0);
//        sydney = new LatLng(21.585219, 105.806863);
//        mMap.addMarker(new MarkerOptions()
//                .position(sydney)
//                .snippet("Population: 4,137,400")
//                .title("hihi")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_shiper)))
//                .setTag(1);

        mMap.setMaxZoomPreference(50.0f);
        mMap.setMinZoomPreference(5.0f);
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15.0f));
        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,15.0f));
        mMap.setOnInfoWindowClickListener(this);
        mMap.setInfoWindowAdapter(this);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        switch (marker.getTag().toString()){
            case "0":{
                Toast.makeText(this, marker.getTitle() + "0", Toast.LENGTH_SHORT).show();
                break;
            }
            case "1":{
                Toast.makeText(this, marker.getTitle() + "1", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
