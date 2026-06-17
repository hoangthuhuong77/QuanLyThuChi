package com.example.quanlythuchi;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class DanhMucActivity extends AppCompatActivity {

    TextView txtQuayLai, txtThem, txtTabChi, txtTabThu;
    ListView listDanhMuc;

    String loai = "Chi tiêu";
    ArrayList<String> danhMucDangHien;
    ArrayAdapter<String> adapter;

    ArrayList<String> danhMucChi = new ArrayList<>(Arrays.asList(
            "🍽   Ăn uống",
            "🧴   Chi tiêu hàng ngày",
            "👕   Quần áo",
            "💄   Mỹ phẩm",
            "🎉   Phí giao lưu",
            "💊   Y tế",
            "📚   Giáo dục",
            "💡   Tiền điện",
            "🚌   Đi lại",
            "📱   Phí liên lạc"
    ));

    ArrayList<String> danhMucThu = new ArrayList<>(Arrays.asList(
            "💼   Tiền lương",
            "🐷   Tiền phụ cấp",
            "🎁   Tiền thưởng",
            "💰   Đầu tư",
            "🤝   Thu nhập thêm"
    ));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_muc);

        txtQuayLai = findViewById(R.id.txtQuayLai);
        txtThem = findViewById(R.id.txtThem);
        txtTabChi = findViewById(R.id.txtTabChi);
        txtTabThu = findViewById(R.id.txtTabThu);
        listDanhMuc = findViewById(R.id.listDanhMuc);

        loai = getIntent().getStringExtra("loai");
        if (loai == null) loai = "Chi tiêu";

        if (loai.equals("Thu nhập")) {
            hienDanhMucThu();
        } else {
            hienDanhMucChi();
        }

        txtQuayLai.setOnClickListener(v -> finish());

        txtTabChi.setOnClickListener(v -> hienDanhMucChi());

        txtTabThu.setOnClickListener(v -> hienDanhMucThu());

        txtThem.setOnClickListener(v -> themDanhMuc());

        listDanhMuc.setOnItemClickListener((parent, view, position, id) -> {
            String danhMuc = danhMucDangHien.get(position);
            danhMuc = xoaIcon(danhMuc);

            Intent intent = new Intent();
            intent.putExtra("danhMuc", danhMuc);
            intent.putExtra("loai", loai);
            setResult(RESULT_OK, intent);
            finish();
        });

        listDanhMuc.setOnItemLongClickListener((parent, view, position, id) -> {
            hienHopThoaiSuaXoa(position);
            return true;
        });
    }

    private void hienDanhMucChi() {
        loai = "Chi tiêu";
        txtTabChi.setTextColor(getColor(android.R.color.holo_red_light));
        txtTabThu.setTextColor(getColor(android.R.color.darker_gray));

        danhMucDangHien = danhMucChi;
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, danhMucDangHien);
        listDanhMuc.setAdapter(adapter);
    }

    private void hienDanhMucThu() {
        loai = "Thu nhập";
        txtTabThu.setTextColor(getColor(android.R.color.holo_red_light));
        txtTabChi.setTextColor(getColor(android.R.color.darker_gray));

        danhMucDangHien = danhMucThu;
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, danhMucDangHien);
        listDanhMuc.setAdapter(adapter);
    }

    private void themDanhMuc() {
        EditText edtNhap = new EditText(this);
        edtNhap.setHint("Nhập tên danh mục");

        new AlertDialog.Builder(this)
                .setTitle("Thêm danh mục")
                .setView(edtNhap)
                .setPositiveButton("Thêm", (dialog, which) -> {
                    String ten = edtNhap.getText().toString();

                    if (!ten.isEmpty()) {
                        danhMucDangHien.add("➕   " + ten);
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void hienHopThoaiSuaXoa(int position) {
        String[] luaChon = {"Sửa", "Xóa"};

        new AlertDialog.Builder(this)
                .setTitle("Chọn thao tác")
                .setItems(luaChon, (dialog, which) -> {
                    if (which == 0) {
                        suaDanhMuc(position);
                    } else {
                        xoaDanhMuc(position);
                    }
                })
                .show();
    }

    private void suaDanhMuc(int position) {
        EditText edtNhap = new EditText(this);
        edtNhap.setText(xoaIcon(danhMucDangHien.get(position)));

        new AlertDialog.Builder(this)
                .setTitle("Sửa danh mục")
                .setView(edtNhap)
                .setPositiveButton("Lưu", (dialog, which) -> {
                    String tenMoi = edtNhap.getText().toString();

                    if (!tenMoi.isEmpty()) {
                        danhMucDangHien.set(position, "➕   " + tenMoi);
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void xoaDanhMuc(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa danh mục")
                .setMessage("Bạn có chắc muốn xóa danh mục này không?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    danhMucDangHien.remove(position);
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private String xoaIcon(String text) {
        return text.replaceAll("^[^a-zA-ZÀ-ỹ]+", "").trim();
    }
}