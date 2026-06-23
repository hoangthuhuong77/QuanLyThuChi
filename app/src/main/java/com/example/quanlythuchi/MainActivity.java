package com.example.quanlythuchi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.text.DecimalFormat;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnBatDau;

    LinearLayout btnLichSuMain;
    LinearLayout btnBaoCaoMain;

    TextView txtSoDu;
    TextView txtTongThu;
    TextView txtTongChi;
    TextView txtGanDay;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        btnBatDau = findViewById(R.id.btnBatDau);

        btnLichSuMain = findViewById(R.id.btnLichSuMain);
        btnBaoCaoMain = findViewById(R.id.btnBaoCaoMain);

        txtSoDu = findViewById(R.id.txtSoDu);
        txtTongThu = findViewById(R.id.txtTongThu);
        txtTongChi = findViewById(R.id.txtTongChi);
        txtGanDay = findViewById(R.id.txtGanDay);

        capNhatDashboard();

        btnBatDau.setOnClickListener(v -> {
            Intent intent =
                    new Intent(MainActivity.this,
                            HomeActivity.class);
            startActivity(intent);
        });

        btnLichSuMain.setOnClickListener(v -> {
            Intent intent =
                    new Intent(MainActivity.this,
                            LichSuActivity.class);
            startActivity(intent);
        });

        btnBaoCaoMain.setOnClickListener(v -> {
            Intent intent =
                    new Intent(MainActivity.this,
                            ThongKeActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        capNhatDashboard();
    }

    private void capNhatDashboard() {

        int tongThu = databaseHelper.tinhTongThu();
        int tongChi = databaseHelper.tinhTongChi();

        int soDu = tongThu - tongChi;

        DecimalFormat format = new DecimalFormat("#,###");

        txtTongThu.setText("Thu: " + format.format(tongThu) + " đ");
        txtTongChi.setText("Chi: " + format.format(tongChi) + " đ");
        txtSoDu.setText(format.format(soDu) + " đ");

        if (databaseHelper.layTatCaGiaoDich().size() > 0) {

            StringBuilder builder = new StringBuilder();

            int soLuong =
                    Math.min(
                            3,
                            databaseHelper.layTatCaGiaoDich().size()
                    );

            for (int i = 0; i < soLuong; i++) {

                builder.append(
                        databaseHelper.layTatCaGiaoDich().get(i)
                );

                builder.append("\n\n");
            }

            txtGanDay.setText(builder.toString());

        } else {

            txtGanDay.setText("Chưa có giao dịch");
        }
    }

}