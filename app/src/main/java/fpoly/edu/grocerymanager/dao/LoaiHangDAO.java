package fpoly.edu.grocerymanager.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fpoly.edu.grocerymanager.database.DbHelper;
import fpoly.edu.grocerymanager.model.LoaiHang;

public class LoaiHangDAO {
    private SQLiteDatabase db;

    public LoaiHangDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public long insert(LoaiHang obj){
        ContentValues values = new ContentValues();
        values.put("tenLoai", obj.getTenLoai());
        return db.insert("LoaiHang",null, values);
    }
    public int update(LoaiHang obj){
        ContentValues values = new ContentValues();
        values.put("tenLoai", obj.getTenLoai());
        return db.update("LoaiHang",values,"maLoai=?",new String[]{String.valueOf(obj.getMaLoai())});

    }
    public int delete(String id){
        return db.delete("LoaiHang","maLoai=?", new String[]{id});
    }

    // get data nhiều tham số
    @SuppressLint("Range")
    public List<LoaiHang> getData(String sql, String...selectionArgs){
        List<LoaiHang> list = new ArrayList<LoaiHang>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            LoaiHang obj = new LoaiHang();
            obj.setMaLoai(Integer.valueOf(c.getString(c.getColumnIndex("maLoai"))));
            obj.setTenLoai(c.getString(c.getColumnIndex("tenLoai")));

            list.add(obj);
        }
        return list;
    }
    // get tất cả data
    public List<LoaiHang> getAll(){

        String sql ="SELECT * FROM LoaiHang";

        return getData(sql);
    }
    //get data theo id
    public LoaiHang getID(String id){
        String sql = "SELECT * FROM LoaiHang WHERE maLoai=?";

        List<LoaiHang> list = getData(sql, id);

        return list.get(0);
    }
}
