package com.example.quanlythuchi;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class LichSuActivity extends AppCompatActivity {

    ListView listLichSu;

    DatabaseHelper databaseHelper;

    ArrayList<String> danhSachGiaoDich;
    ArrayList<Integer> dsId;

    GiaoDichAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su);

        listLichSu = findViewById(R.id.listLichSu);

        databaseHelper = new DatabaseHelper(this);

        danhSachGiaoDich =
                databaseHelper.layTatCaGiaoDich();

        dsId =
                databaseHelper.layTatCaId();

        adapter =
                new GiaoDichAdapter(
                        this,
                        danhSachGiaoDich,
                        dsId,
                        databaseHelper
                );

        listLichSu.setAdapter(adapter);

    }
    @Override
    protected void onResume() {
        super.onResume();

        if (databaseHelper != null && adapter != null) {
            danhSachGiaoDich.clear();
            dsId.clear();

            danhSachGiaoDich.addAll(
                    databaseHelper.layTatCaGiaoDich()
            );

            dsId.addAll(
                    databaseHelper.layTatCaId()
            );

            adapter.notifyDataSetChanged();
        }
    }
}