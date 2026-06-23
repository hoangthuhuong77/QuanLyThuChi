package com.example.quanlythuchi;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

    TextView txtChiTieu, txtThuNhap, txtNgay;
    EditText edtGhiChu, edtSoTien;
    LinearLayout btnNhapKhoan, btnLichSu, btnThongKe;
    GridLayout gridDanhMuc;

    DatabaseHelper databaseHelper;

    String loaiGiaoDich = "Chi tiêu";
    String ngayDuocChon = "";
    String danhMucDuocChon = "";

    String[] danhMucChi = {
            "🍽\nĂn uống",
            "🚌\nĐi lại",
            "👕\nQuần áo",
            "💄\nMỹ phẩm",
            "🎉\nPhí giao lưu",
            "💊\nY tế",
            "📚\nGiáo dục",
            "💡\nTiền điện",
            "🏠\nTiền nhà",
            "📱\nPhí liên lạc",
            "➕\nKhác"
    };

    String[] danhMucThu = {
            "💼\nTiền lương",
            "🐷\nTiền phụ cấp",
            "🎁\nTiền thưởng",
            "💰\nĐầu tư",
            "🤝\nThu nhập thêm",
            "➕\nKhác"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        databaseHelper = new DatabaseHelper(this);

        txtChiTieu = findViewById(R.id.txtChiTieu);
        txtThuNhap = findViewById(R.id.txtThuNhap);
        txtNgay = findViewById(R.id.txtNgay);

        edtGhiChu = findViewById(R.id.edtGhiChu);
        edtSoTien = findViewById(R.id.edtSoTien);

        btnNhapKhoan = findViewById(R.id.btnNhapKhoan);
        btnLichSu = findViewById(R.id.btnLichSu);
        btnThongKe = findViewById(R.id.btnThongKe);

        gridDanhMuc = findViewById(R.id.gridDanhMuc);

        Calendar calendar = Calendar.getInstance();

        ngayDuocChon =
                calendar.get(Calendar.DAY_OF_MONTH) + "/"
                        + (calendar.get(Calendar.MONTH) + 1) + "/"
                        + calendar.get(Calendar.YEAR);

        txtNgay.setText("📅 " + ngayDuocChon);

        dinhDangSoTien();

        chonChiTieu();

        txtNgay.setOnClickListener(v -> chonNgay());

        txtChiTieu.setOnClickListener(v -> chonChiTieu());

        txtThuNhap.setOnClickListener(v -> chonThuNhap());

        btnNhapKhoan.setOnClickListener(v -> luuGiaoDich());

        btnLichSu.setOnClickListener(v -> {
            Intent intent =
                    new Intent(HomeActivity.this,
                            LichSuActivity.class);
            startActivity(intent);
        });

        btnThongKe.setOnClickListener(v -> {
            Intent intent =
                    new Intent(HomeActivity.this,
                            ThongKeActivity.class);
            startActivity(intent);
        });
    }

    private void chonChiTieu() {

        loaiGiaoDich = "Chi tiêu";

        txtChiTieu.setTextColor(
                getColor(android.R.color.holo_red_light));

        txtThuNhap.setTextColor(
                getColor(android.R.color.darker_gray));

        hienDanhMuc(danhMucChi);
    }

    private void chonThuNhap() {

        loaiGiaoDich = "Thu nhập";

        txtThuNhap.setTextColor(
                getColor(android.R.color.holo_red_light));

        txtChiTieu.setTextColor(
                getColor(android.R.color.darker_gray));

        hienDanhMuc(danhMucThu);
    }

    private void hienDanhMuc(String[] danhSach) {

        gridDanhMuc.removeAllViews();

        for (String item : danhSach) {

            TextView textView = new TextView(this);

            GridLayout.LayoutParams params =
                    new GridLayout.LayoutParams();

            params.width = 300;
            params.height = 220;

            params.setMargins(
                    10,
                    10,
                    10,
                    10
            );

            textView.setLayoutParams(params);

            textView.setText(item);

            textView.setGravity(Gravity.CENTER);

            textView.setTextSize(16);

            textView.setBackgroundResource(
                    R.drawable.card_white
            );

            textView.setOnClickListener(v -> {

                String tenDanhMuc =
                        item.substring(item.indexOf("\n") + 1);

                if (tenDanhMuc.equals("Khác")) {

                    Intent intent =
                            new Intent(
                                    HomeActivity.this,
                                    DanhMucActivity.class
                            );

                    intent.putExtra(
                            "loai",
                            loaiGiaoDich
                    );

                    startActivityForResult(
                            intent,
                            100
                    );

                } else {

                    danhMucDuocChon =
                            tenDanhMuc;

                    Toast.makeText(
                            this,
                            "Đã chọn: " + danhMucDuocChon,
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });

            gridDanhMuc.addView(textView);
        }
    }

    private void dinhDangSoTien() {

        edtSoTien.addTextChangedListener(
                new TextWatcher() {

                    boolean dangSua = false;

                    @Override
                    public void beforeTextChanged(
                            CharSequence s,
                            int start,
                            int count,
                            int after) {
                    }

                    @Override
                    public void onTextChanged(
                            CharSequence s,
                            int start,
                            int before,
                            int count) {
                    }

                    @Override
                    public void afterTextChanged(
                            Editable s) {

                        if (dangSua) return;

                        dangSua = true;

                        String chuoi =
                                s.toString()
                                        .replaceAll(
                                                "[^0-9]",
                                                ""
                                        );

                        if (!chuoi.isEmpty()) {

                            long soTien =
                                    Long.parseLong(chuoi);

                            DecimalFormat format =
                                    new DecimalFormat("#,###");

                            edtSoTien.setText(
                                    format.format(soTien)
                            );

                            edtSoTien.setSelection(
                                    edtSoTien.getText().length()
                            );
                        }

                        dangSua = false;
                    }
                });
    }

    private void chonNgay() {

        Calendar calendar =
                Calendar.getInstance();

        DatePickerDialog dialog =
                new DatePickerDialog(
                        this,
                        (view, year, month, dayOfMonth) -> {

                            ngayDuocChon =
                                    dayOfMonth + "/"
                                            + (month + 1) + "/"
                                            + year;

                            txtNgay.setText(
                                    "📅 " + ngayDuocChon
                            );
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );

        dialog.show();
    }

    private void luuGiaoDich() {

        String ghiChu =
                edtGhiChu.getText().toString();

        String soTien =
                edtSoTien.getText()
                        .toString()
                        .replaceAll("[^0-9]", "");

        if (soTien.isEmpty()) {

            Toast.makeText(
                    this,
                    "Vui lòng nhập số tiền",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        if (danhMucDuocChon.isEmpty()) {

            Toast.makeText(
                    this,
                    "Vui lòng chọn danh mục",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        databaseHelper.themGiaoDich(
                ghiChu,
                Integer.parseInt(soTien),
                loaiGiaoDich,
                danhMucDuocChon,
                ngayDuocChon
        );

        Toast.makeText(
                this,
                "Đã lưu giao dịch",
                Toast.LENGTH_SHORT
        ).show();

        edtGhiChu.setText("");
        edtSoTien.setText("");
        danhMucDuocChon = "";
    }

    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data) {

        super.onActivityResult(
                requestCode,
                resultCode,
                data
        );

        if (requestCode == 100
                && resultCode == RESULT_OK
                && data != null) {

            danhMucDuocChon =
                    data.getStringExtra("danhMuc");
        }
    }
}