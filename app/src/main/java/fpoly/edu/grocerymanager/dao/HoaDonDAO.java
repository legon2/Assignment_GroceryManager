package fpoly.edu.grocerymanager.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import fpoly.edu.grocerymanager.database.DbHelper;
import fpoly.edu.grocerymanager.model.HoaDon;

public class HoaDonDAO {
    private SQLiteDatabase db;
    private Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    public HoaDonDAO(Context context) {
        this.context = context;
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();

    }

    public long insert(HoaDon obj){
        ContentValues values = new ContentValues();
        values.put("maND",obj.getMaND());
        values.put("mahang",obj.getMaHang());
        values.put("ngayLap", sdf.format(obj.getNgayLap()));
        values.put("tongTien",obj.getTongTien());
        values.put("trangThai",obj.getTrangThai());

        return db.insert("HoaDon",null, values);
    }
    public int update(HoaDon obj){
        ContentValues values = new ContentValues();
        values.put("maND",obj.getMaND());
        values.put("mahang",obj.getMaHang());
        values.put("ngayLap", sdf.format(obj.getNgayLap()));
        values.put("tongTien",obj.getTongTien());
        values.put("trangThai",obj.getTrangThai());

        return db.update("HoaDon",values,"maHD=?",new String[]{String.valueOf(obj.getMaHD())});

    }
    public int delete(String id){
        return db.delete("HoaDon","maHD=?", new String[]{id});
    }

    //get data nhiều tham số
    @SuppressLint("Range")
    public List<HoaDon> getData(String sql, String...selectionArgs){
        List<HoaDon> list = new ArrayList<HoaDon>();
        Cursor c = db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            HoaDon obj = new HoaDon();
            obj.setMaHD(Integer.parseInt(c.getString(c.getColumnIndex("maHD"))));
            obj.setMaND(c.getString(c.getColumnIndex("maND")));
            obj.setMaHang(Integer.parseInt(c.getString(c.getColumnIndex("mahang"))));
            obj.setTongTien(Integer.parseInt(c.getString(c.getColumnIndex("tongTien"))));
            try {
                obj.setNgayLap(sdf.parse(c.getString(c.getColumnIndex("ngayLap"))));
            }catch (ParseException e){
                e.printStackTrace();
            }
            obj.setTrangThai(Integer.parseInt(c.getString(c.getColumnIndex("trangThai"))));
            list.add(obj);
        }
        return list;
    }
    // get tất cả data
    public List<HoaDon> getAll(){
        String sql ="SELECT * FROM HoaDon";
        return getData(sql);
    }
    //get data theo id
    public HoaDon getID(String id){
        String sql = "SELECT * FROM HoaDon WHERE maHD=?";
        List<HoaDon> list = getData(sql,id);
        return list.get(0);
    }
}
