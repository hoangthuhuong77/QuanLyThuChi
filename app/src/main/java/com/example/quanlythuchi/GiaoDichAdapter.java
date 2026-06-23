package com.example.quanlythuchi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GiaoDichAdapter extends ArrayAdapter<String> {

    Context context;
    ArrayList<String> ds;

    public GiaoDichAdapter(Context context, ArrayList<String> ds) {
        super(context, 0, ds);
        this.context = context;
        this.ds = ds;
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_giao_dich,
                            parent,
                            false);
        }

        TextView txtItem =
                convertView.findViewById(R.id.txtItem);

        txtItem.setText(ds.get(position));

        return convertView;
    }
}