package com.example.quanlythuchi;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class GiaoDichAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> danhSach;
    ArrayList<Integer> dsId;
    DatabaseHelper databaseHelper;

    public GiaoDichAdapter(
            Context context,
            ArrayList<String> danhSach,
            ArrayList<Integer> dsId,
            DatabaseHelper databaseHelper
    ) {
        this.context = context;
        this.danhSach = danhSach;
        this.dsId = dsId;
        this.databaseHelper = databaseHelper;
    }

    @Override
    public int getCount() {
        return danhSach.size();
    }

    @Override
    public Object getItem(int position) {
        return danhSach.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position,
                        View convertView,
                        ViewGroup parent) {

        View view = LayoutInflater.from(context)
                .inflate(
                        R.layout.item_giao_dich,
                        parent,
                        false
                );

        TextView txtNgay =
                view.findViewById(R.id.txtNgay);

        TextView txtDanhMuc =
                view.findViewById(R.id.txtDanhMuc);

        TextView txtSoTien =
                view.findViewById(R.id.txtSoTien);

        ImageButton btnSua =
                view.findViewById(R.id.btnSua);

        ImageButton btnXoa =
                view.findViewById(R.id.btnXoa);



        String giaoDich = danhSach.get(position);

        String[] dong = giaoDich.split("\n");

        if(dong.length >= 4){

            txtNgay.setText(dong[0]);

            txtDanhMuc.setText(dong[1]);

            txtSoTien.setText(dong[3]);
        }


        btnXoa.setOnClickListener(v -> {

            new AlertDialog.Builder(context)
                    .setTitle("Xóa giao dịch")
                    .setMessage("Bạn có chắc muốn xóa?")
                    .setPositiveButton("Xóa",
                            (dialog, which) -> {

                                int id =
                                        dsId.get(position);

                                databaseHelper
                                        .xoaGiaoDich(id);

                                danhSach.remove(position);
                                dsId.remove(position);

                                notifyDataSetChanged();
                            })
                    .setNegativeButton(
                            "Hủy",
                            null
                    )
                    .show();
        });

        btnSua.setOnClickListener(v -> {

            android.content.Intent intent =
                    new android.content.Intent(
                            context,
                            SuaGiaoDichActivity.class
                    );

            intent.putExtra(
                    "id",
                    dsId.get(position)
            );

            context.startActivity(intent);
        });


        return view;
    }
}