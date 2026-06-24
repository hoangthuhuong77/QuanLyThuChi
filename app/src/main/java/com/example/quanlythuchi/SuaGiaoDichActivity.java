package com.example.quanlythuchi;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

public class SuaGiaoDichActivity extends AppCompatActivity {

    TextView txtNgaySua;
    TextView txtChiTieuSua;
    TextView txtThuNhapSua;

    EditText edtGhiChuSua;
    EditText edtSoTienSua;

    GridLayout gridDanhMucSua;
    Button btnLuuSua;

    DatabaseHelper databaseHelper;

    int idGiaoDich;
    String loaiCu;
    String danhMucCu;
    String ngayCu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_giao_dich);

        txtNgaySua = findViewById(R.id.txtNgaySua);
        txtChiTieuSua = findViewById(R.id.txtChiTieuSua);
        txtThuNhapSua = findViewById(R.id.txtThuNhapSua);

        edtGhiChuSua = findViewById(R.id.edtGhiChuSua);
        edtSoTienSua = findViewById(R.id.edtSoTienSua);

        gridDanhMucSua = findViewById(R.id.gridDanhMucSua);
        btnLuuSua = findViewById(R.id.btnLuuSua);

        databaseHelper = new DatabaseHelper(this);

        idGiaoDich =
                getIntent().getIntExtra(
                        "id",
                        -1
                );
        taiDuLieuCu();

        btnLuuSua.setOnClickListener(v -> {

            String ghiChuMoi =
                    edtGhiChuSua.getText().toString();

            String soTienText =
                    edtSoTienSua.getText().toString();

            if(soTienText.isEmpty()){
                return;
            }

            int soTienMoi =
                    Integer.parseInt(
                            soTienText
                    );

            databaseHelper.suaToanBoGiaoDich(
                    idGiaoDich,
                    ghiChuMoi,
                    soTienMoi,
                    loaiCu,
                    danhMucCu,
                    ngayCu
            );

            finish();
        });
    }


    private void taiDuLieuCu() {

        android.database.Cursor cursor =
                databaseHelper.layGiaoDichTheoId(
                        idGiaoDich
                );

        if(cursor.moveToFirst()){

            String ghiChu =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "ghiChu"
                            )
                    );

            int soTien =
                    cursor.getInt(
                            cursor.getColumnIndexOrThrow(
                                    "soTien"
                            )
                    );

            loaiCu =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "loai"
                            )
                    );

            danhMucCu =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "danhMuc"
                            )
                    );

            ngayCu =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "ngay"
                            )
                    );

            edtGhiChuSua.setText(
                    ghiChu
            );

            edtSoTienSua.setText(
                    String.valueOf(
                            soTien
                    )
            );

            txtNgaySua.setText(
                    "📅 " + ngayCu
            );
        }

        cursor.close();
    }
}