package fpoly.edu.grocerymanager.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fpoly.edu.grocerymanager.database.DbHelper;
import fpoly.edu.grocerymanager.model.NguoiDung;

public class NguoiDungDAO {
    private SQLiteDatabase db;

    public NguoiDungDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(NguoiDung obj) {
        ContentValues values = new ContentValues();
        values.put("maND", obj.getMaND());
        values.put("hoTen", obj.getHoTen());
        values.put("matKhau", obj.getMatKhau());
        values.put("soDienThoai", obj.getSoDienThoai());

        return db.insert("NguoiDung", null, values);
    }

    public int updatePass(NguoiDung obj) {
        ContentValues values = new ContentValues();
        values.put("hoTen", obj.getHoTen());
        values.put("matKhau", obj.getMatKhau());
        return db.update("NguoiDung", values, "maND=?", new String[]{obj.getMaND()});
    }

    public int delete(String id) {
        return db.delete("NguoiDung", "mdND=?", new String[]{id});
    }

    @SuppressLint("Range")
    public List<NguoiDung> getData(String sql, String... selectionArgs) {
        List<NguoiDung> list = new ArrayList<NguoiDung>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()) {
            NguoiDung obj = new NguoiDung();
            obj.setMaND(c.getString(c.getColumnIndex("maND")));
            obj.setHoTen(c.getString(c.getColumnIndex("hoTen")));
            obj.setMatKhau(c.getString(c.getColumnIndex("matKhau")));
            obj.setSoDienThoai(Integer.parseInt(c.getString(c.getColumnIndex("soDienThoai"))));
            Log.i("//==========",obj.toString());

            list.add(obj);
        }
        return list;

    }

    // get tất cả data
    public List<NguoiDung> getAll() {
        String sql = "SELECT * FROM NguoiDung";
        return getData(sql);

    }
    //get data theo id
    public NguoiDung getID(String id){
        String sql = "SELECT * FROM NguoiDung WHERE maND=?";
        List<NguoiDung > list = getData(sql,id);
        return list.get(0);
    }
    //check login
    public int checkLogin(String id, String password){
        String sql = "SELECT * FROM NguoiDung WHERE maND=? AND matKhau=?";
        List<NguoiDung> list = getData(sql,id,password);
        if (list.size() == 0)
            return -1;
        return 1;
    }
}

