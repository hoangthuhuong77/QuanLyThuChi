package com.example.quanlythuchi;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ThongKeActivity extends AppCompatActivity {

    TextView txtTongThu, txtTongChi, tabThang, tabNam, txtBarChartTitle;
    PieChart pieChart;
    BarChart barChart;
    DatabaseHelper db;
    DecimalFormat format = new DecimalFormat("#,###đ");

    // Colors for chart
    int[] chartColors = new int[]{
            Color.parseColor("#4A69FF"), // Blue
            Color.parseColor("#27AE60"), // Green
            Color.parseColor("#F2994A"), // Orange
            Color.parseColor("#EB5757"), // Red
            Color.parseColor("#9B51E0"), // Purple
            Color.parseColor("#2F80ED"), // Sky Blue
            Color.parseColor("#56CCF2"), // Cyan
            Color.parseColor("#F2C94C")  // Yellow
    };

    boolean isMonthly = true;
    int currentMonth, currentYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);

        db = new DatabaseHelper(this);
        Calendar c = Calendar.getInstance();
        currentMonth = c.get(Calendar.MONTH) + 1;
        currentYear = c.get(Calendar.YEAR);

        initViews();
        setupCharts();
        updateUI();

        tabThang.setOnClickListener(v -> {
            isMonthly = true;
            updateTabStyle();
            updateUI();
        });

        tabNam.setOnClickListener(v -> {
            isMonthly = false;
            updateTabStyle();
            updateUI();
        });

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    private void initViews() {
        txtTongThu = findViewById(R.id.txtTongThu);
        txtTongChi = findViewById(R.id.txtTongChi);
        tabThang = findViewById(R.id.tabThang);
        tabNam = findViewById(R.id.tabNam);
        txtBarChartTitle = findViewById(R.id.txtBarChartTitle);
        pieChart = findViewById(R.id.pieChart);
        barChart = findViewById(R.id.barChart);
    }

    private void setupCharts() {
        // Setup PieChart (Donut style)
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setHoleRadius(58f);
        pieChart.getLegend().setEnabled(true);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(10f);

        // Setup BarChart
        barChart.getDescription().setEnabled(false);
        barChart.setDrawGridBackground(false);
        barChart.getAxisRight().setEnabled(false);
        
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setLabelRotationAngle(-45);

        barChart.getAxisLeft().setDrawGridLines(true);
        barChart.getAxisLeft().setGridColor(Color.parseColor("#E0E0E0"));
        barChart.getAxisLeft().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (value >= 1000000) return (value / 1000000) + "M";
                if (value >= 1000) return (value / 1000) + "k";
                return String.valueOf((int) value);
            }
        });
    }

    private void updateTabStyle() {
        if (isMonthly) {
            tabThang.setTextColor(Color.parseColor("#4A69FF"));
            tabThang.setBackgroundResource(R.drawable.card_white);
            tabNam.setTextColor(Color.parseColor("#666666"));
            tabNam.setBackgroundColor(Color.TRANSPARENT);
        } else {
            tabNam.setTextColor(Color.parseColor("#4A69FF"));
            tabNam.setBackgroundResource(R.drawable.card_white);
            tabThang.setTextColor(Color.parseColor("#666666"));
            tabThang.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    private void updateUI() {
        String filter = isMonthly ? "/" + currentMonth + "/" + currentYear : "/" + currentYear;
        
        // Update Totals
        int tongThu = db.layTongThu(filter);
        int tongChi = db.layTongChi(filter);
        txtTongThu.setText("+" + format.format(tongThu));
        txtTongChi.setText("-" + format.format(tongChi));

        // Update Pie Chart
        loadPieChartData(filter, tongChi);

        // Update Bar Chart
        if (isMonthly) {
            txtBarChartTitle.setText("Thu chi theo tuần — Tháng " + currentMonth);
            loadWeeklyBarChartData();
        } else {
            txtBarChartTitle.setText("Thu chi 12 tháng — Năm " + currentYear);
            loadYearlyBarChartData();
        }
    }

    private void loadPieChartData(String filter, int totalChi) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        Cursor cursor = db.layChiTieuTheoDanhMuc(filter);
        
        while (cursor.moveToNext()) {
            String category = cursor.getString(0);
            int amount = cursor.getInt(1);
            if (amount > 0) {
                entries.add(new PieEntry(amount, category));
            }
        }
        cursor.close();

        if (entries.isEmpty()) {
            pieChart.clear();
            pieChart.setCenterText("Không có dữ liệu chi tiêu");
            return;
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(chartColors);
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new com.github.mikephil.charting.formatter.PercentFormatter(pieChart));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);
        pieChart.setCenterText("Chi Tiêu\n" + format.format(totalChi));
        pieChart.animateY(1000);
        pieChart.invalidate();
    }

    private void loadWeeklyBarChartData() {
        ArrayList<BarEntry> entriesThu = new ArrayList<>();
        ArrayList<BarEntry> entriesChi = new ArrayList<>();
        String[] weeks = {"Tuần 1", "Tuần 2", "Tuần 3", "Tuần 4"};

        for (int i = 0; i < 4; i++) {
            ArrayList<String> days = new ArrayList<>();
            for (int d = i * 7 + 1; d <= (i + 1) * 7; d++) {
                days.add(d + "/" + currentMonth + "/" + currentYear);
            }
            int[] values = db.layThuChiTrongKhoang(days);
            entriesThu.add(new BarEntry(i, values[0]));
            entriesChi.add(new BarEntry(i, values[1]));
        }

        displayBarChart(entriesThu, entriesChi, weeks);
    }

    private void loadYearlyBarChartData() {
        ArrayList<BarEntry> entriesThu = new ArrayList<>();
        ArrayList<BarEntry> entriesChi = new ArrayList<>();
        String[] monthsLabels = {"T1", "T2", "T3", "T4", "T5", "T6", "T7", "T8", "T9", "T10", "T11", "T12"};

        for (int m = 1; m <= 12; m++) {
            int thu = db.layTongThu("/" + m + "/" + currentYear);
            int chi = db.layTongChi("/" + m + "/" + currentYear);
            entriesThu.add(new BarEntry(m - 1, thu));
            entriesChi.add(new BarEntry(m - 1, chi));
        }

        displayBarChart(entriesThu, entriesChi, monthsLabels);
    }

    private void displayBarChart(ArrayList<BarEntry> thu, ArrayList<BarEntry> chi, String[] labels) {
        BarDataSet setThu = new BarDataSet(thu, "Thu nhập");
        setThu.setColor(Color.parseColor("#27AE60"));
        setThu.setDrawValues(false);
        
        BarDataSet setChi = new BarDataSet(chi, "Chi tiêu");
        setChi.setColor(Color.parseColor("#EB5757"));
        setChi.setDrawValues(false);

        BarData data = new BarData(setThu, setChi);
        float groupSpace = 0.08f;
        float barSpace = 0.03f;
        float barWidth = 0.43f;
        
        data.setBarWidth(barWidth);
        barChart.setData(data);
        
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setLabelCount(labels.length);
        
        barChart.groupBars(0, groupSpace, barSpace);
        barChart.setFitBars(true);
        barChart.animateY(1000);
        barChart.invalidate();
    }
}
