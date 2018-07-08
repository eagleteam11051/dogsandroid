package com.tbm.dogs.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import com.tbm.dogs.R;
import com.tbm.dogs.adapters.AdapterCongViec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViecDangCho extends AppCompatActivity {
    private static final String TAG = "ViecDangcho";
    private ExpandableListView expandableListView;
    private HashMap<String,List<String>> data;

    private void init(){
        expandableListView = findViewById(R.id.expan_cong_viec);
    }
    private void initList(){
        //prepare data

        //data for header group
        final List<String> listHeader = new ArrayList<>();
        listHeader.add("Giao hàng nhanh");
        listHeader.add("Cứu hộ");
        listHeader.add("Vận chuyển đồ");



        //data for child
        data = new HashMap<>();
        List<String> listGiaoHang = new ArrayList<>();
        listGiaoHang.add("chuyển đồ tới ictu");
        listGiaoHang.add("ship hàng tới xyz");

        List<String> listCuuHo = new ArrayList<>();
        listCuuHo.add("Hết xăng");
        listCuuHo.add("Hỏng xe");

        List<String> listVanChuyenDo = new ArrayList<>();
        listVanChuyenDo.add("Chuyen phongjdafkjaskdfjasljgjdafkjaskdfjasljkfdljalsdjflkajgjdafkjaskdfjasljkfdljalsdjflkajgjdafkjaskdfjasljkfdljalsdjflkajgjdafkjaskdfjasljkfdljalsdjflkajkfdljalsdjflkajsldkfjaslkjdfljalfjlkasjdklfsdf");
        listVanChuyenDo.add("Chuyen nha tro");




        data.put(listHeader.get(0), listGiaoHang);
        data.put(listHeader.get(1), listCuuHo);
        data.put(listHeader.get(2), listVanChuyenDo);

        //setup adapter for ExpandableListView
        AdapterCongViec adapter = new AdapterCongViec(this, listHeader, data);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Log.e(TAG, "onChildClick: " + listHeader.get(groupPosition) + ", " + data.get(listHeader.get(groupPosition)).get(childPosition));
                return true;
            }
        });

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.e(TAG, "onGroupClick: " + groupPosition);
                return false;
            }
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_viec_dang_cho);
        init();
        initList();
    }
}
