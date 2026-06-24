package com.example.quanlythuchi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "QuanLyThuChi.db";
    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE GiaoDich (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ghiChu TEXT," +
                "soTien INTEGER," +
                "loai TEXT," +
                "danhMuc TEXT," +
                "ngay TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS GiaoDich");
        onCreate(db);
    }

    public void themGiaoDich(String ghiChu, int soTien, String loai, String danhMuc, String ngay) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ghiChu", ghiChu);
        values.put("soTien", soTien);
        values.put("loai", loai);
        values.put("danhMuc", danhMuc);
        values.put("ngay", ngay);
        db.insert("GiaoDich", null, values);
        db.close();
    }

    public ArrayList<String> layTatCaGiaoDich() {
        ArrayList<String> danhSach = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM GiaoDich ORDER BY id DESC", null);
        if (cursor.moveToFirst()) {
            do {
                String ghiChu = cursor.getString(cursor.getColumnIndexOrThrow("ghiChu"));
                int soTien = cursor.getInt(cursor.getColumnIndexOrThrow("soTien"));
                String loai = cursor.getString(cursor.getColumnIndexOrThrow("loai"));
                String danhMuc = cursor.getString(cursor.getColumnIndexOrThrow("danhMuc"));
                String ngay = cursor.getString(cursor.getColumnIndexOrThrow("ngay"));
                String giaoDich = "📅 " + ngay + "\n💰 " + danhMuc + "\n📝 " + ghiChu +
                        "\n💵 " + new java.text.DecimalFormat("#,###").format(soTien) + " đ";
                danhSach.add(giaoDich);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return danhSach;
    }

    public void xoaGiaoDich(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("GiaoDich", "id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public ArrayList<Integer> layTatCaId() {
        ArrayList<Integer> ids = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM GiaoDich ORDER BY id DESC", null);
        if (cursor.moveToFirst()) {
            do {
                ids.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return ids;
    }

    public void suaGiaoDich(int id, String ghiChu, int soTien) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ghiChu", ghiChu);
        values.put("soTien", soTien);
        db.update("GiaoDich", values, "id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public int tinhTongThu() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(soTien) FROM GiaoDich WHERE loai='Thu nhập'", null);
        int tong = 0;
        if (cursor.moveToFirst()) tong = cursor.getInt(0);
        cursor.close();
        return tong;
    }

    public int tinhTongChi() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(soTien) FROM GiaoDich WHERE loai='Chi tiêu'", null);
        int tong = 0;
        if (cursor.moveToFirst()) tong = cursor.getInt(0);
        cursor.close();
        return tong;
    }

    public int layTongThu(String filter) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT SUM(soTien) FROM GiaoDich WHERE loai='Thu nhập' AND ngay LIKE ?";
        Cursor cursor = db.rawQuery(sql, new String[]{"%" + filter});
        int tong = 0;
        if (cursor.moveToFirst()) tong = cursor.getInt(0);
        cursor.close();
        return tong;
    }

    public int layTongChi(String filter) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT SUM(soTien) FROM GiaoDich WHERE loai='Chi tiêu' AND ngay LIKE ?";
        Cursor cursor = db.rawQuery(sql, new String[]{"%" + filter});
        int tong = 0;
        if (cursor.moveToFirst()) tong = cursor.getInt(0);
        cursor.close();
        return tong;
    }

    public Cursor layChiTieuTheoDanhMuc(String filter) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT danhMuc, SUM(soTien) FROM GiaoDich WHERE loai='Chi tiêu' AND ngay LIKE ? GROUP BY danhMuc";
        return db.rawQuery(sql, new String[]{"%" + filter});
    }

    public int[] layThuChiTrongKhoang(ArrayList<String> danhSachNgay) {
        SQLiteDatabase db = this.getReadableDatabase();
        int tongThu = 0;
        int tongChi = 0;
        for (String ngay : danhSachNgay) {
            Cursor cThu = db.rawQuery("SELECT SUM(soTien) FROM GiaoDich WHERE loai='Thu nhập' AND ngay = ?", new String[]{ngay});
            if (cThu.moveToFirst()) tongThu += cThu.getInt(0);
            cThu.close();
            Cursor cChi = db.rawQuery("SELECT SUM(soTien) FROM GiaoDich WHERE loai='Chi tiêu' AND ngay = ?", new String[]{ngay});
            if (cChi.moveToFirst()) tongChi += cChi.getInt(0);
            cChi.close();
        }
        return new int[]{tongThu, tongChi};
    }
    public Cursor layTatCaGiaoDichCursor() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM GiaoDich ORDER BY id DESC",
                null
        );
    }

    public Cursor layGiaoDichTheoId(int id) {

        SQLiteDatabase db =
                this.getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM GiaoDich WHERE id=?",
                new String[]{
                        String.valueOf(id)
                }
        );
    }

    public void suaToanBoGiaoDich(
            int id,
            String ghiChu,
            int soTien,
            String loai,
            String danhMuc,
            String ngay
    ) {

        SQLiteDatabase db =
                this.getWritableDatabase();

        ContentValues values =
                new ContentValues();

        values.put("ghiChu", ghiChu);
        values.put("soTien", soTien);
        values.put("loai", loai);
        values.put("danhMuc", danhMuc);
        values.put("ngay", ngay);

        db.update(
                "GiaoDich",
                values,
                "id=?",
                new String[]{
                        String.valueOf(id)
                }
        );

        db.close();
    }
}
