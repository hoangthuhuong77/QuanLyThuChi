package com.example.quanlythuchi;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class LichSuActivity extends AppCompatActivity {

    ListView listLichSu;

    DatabaseHelper databaseHelper;

    ArrayList<String> danhSachGiaoDich;
    ArrayList<Integer> dsId;

    ArrayAdapter<String> adapter;

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
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        danhSachGiaoDich
                );

        listLichSu.setAdapter(adapter);

        listLichSu.setOnItemLongClickListener((parent, view, position, id) -> {

            String[] luaChon = {
                    "Sửa giao dịch",
                    "Xóa giao dịch"
            };

            new AlertDialog.Builder(this)
                    .setTitle("Chọn thao tác")
                    .setItems(luaChon, (dialog, which) -> {

                        if (which == 0) {

                            suaGiaoDich(position);

                        } else {

                            xoaGiaoDich(position);

                        }

                    })
                    .show();

            return true;
        });
    }
    private void xoaGiaoDich(int position){

        int idGiaoDich = dsId.get(position);

        databaseHelper.xoaGiaoDich(idGiaoDich);

        danhSachGiaoDich.remove(position);
        dsId.remove(position);

        adapter.notifyDataSetChanged();
    }
    private void suaGiaoDich(int position){

        android.widget.EditText edt =
                new android.widget.EditText(this);

        edt.setHint("Nhập số tiền mới");

        new AlertDialog.Builder(this)
                .setTitle("Sửa giao dịch")
                .setView(edt)
                .setPositiveButton("Lưu",
                        (dialog, which) -> {

                            String soTienMoi =
                                    edt.getText().toString();

                            if(!soTienMoi.isEmpty()){

                                int idGiaoDich =
                                        dsId.get(position);

                                databaseHelper.suaGiaoDich(
                                        idGiaoDich,
                                        "Đã chỉnh sửa",
                                        Integer.parseInt(soTienMoi)
                                );

                                recreate();
                            }
                        })
                .setNegativeButton("Hủy", null)
                .show();
    }
}