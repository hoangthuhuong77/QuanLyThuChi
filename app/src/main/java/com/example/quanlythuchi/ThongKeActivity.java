package com.example.quanlythuchi;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class ThongKeActivity extends AppCompatActivity {

    TextView txtTongThu;
    TextView txtTongChi;
    TextView txtSoDu;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);

        txtTongThu = findViewById(R.id.txtTongThu);
        txtTongChi = findViewById(R.id.txtTongChi);
        txtSoDu = findViewById(R.id.txtSoDu);

        databaseHelper = new DatabaseHelper(this);

        int tongThu = databaseHelper.tinhTongThu();
        int tongChi = databaseHelper.tinhTongChi();

        int soDu = tongThu - tongChi;

        DecimalFormat format = new DecimalFormat("#,###");

        txtTongThu.setText("Tổng thu: " + format.format(tongThu) + "đ");
        txtTongChi.setText("Tổng chi: " + format.format(tongChi) + "đ");
        txtSoDu.setText("Số dư: " + format.format(soDu) + "đ");
    }
}