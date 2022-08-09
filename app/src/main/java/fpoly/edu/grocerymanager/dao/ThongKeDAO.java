package fpoly.edu.grocerymanager.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fpoly.edu.grocerymanager.database.DbHelper;
import fpoly.edu.grocerymanager.model.Hang;
import fpoly.edu.grocerymanager.model.Top;

public class ThongKeDAO {
    private SQLiteDatabase db;
    private Context context;

    public ThongKeDAO(Context context){
        this.context = context;
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // Thống kê top 10
    @SuppressLint("Range")
    public List<Top> getTop(){
        String sqlTop = "select maHang, count(maHang) as soLuong from HoaDon " +
                "group by maHang order by soLuong desc limit 10";
        List<Top> list = new ArrayList<Top>();
        HangDAO hangDAO = new HangDAO(context);
        Cursor c = db.rawQuery(sqlTop, null);
        while (c.moveToNext()){
            Top top = new Top();
            Hang hang = hangDAO.getID(c.getString(0));
            top.setTenHang(hang.getTenHang());
            top.setSoLuong(Integer.parseInt(c.getString(c.getColumnIndex("soLuong"))));

            list.add(top);
        }
        return list;
    }



    // Thống kê doanh thu
    @SuppressLint("Range")
    public int getDoanhThu(String tuNgay, String denNgay){
        String sqlDoanhThu = "select sum(tongTien) as doanhThu from HoaDon where ngayLap between ? and ?";
        List<Integer> list = new ArrayList<Integer>();
        Cursor c = db.rawQuery(sqlDoanhThu, new String[]{tuNgay,denNgay});

        while (c.moveToNext()){
            try {
                list.add(Integer.parseInt(c.getString(c.getColumnIndex("doanhThu"))));
            }catch (Exception e){
                list.add(0);
            }
// doanh thu chỉ in ra 1 dòng
// nếu trong khoảng thời gian mà có thì nó sẽ in ra tổng tiền thuê
// còn nếu không có thì nó sẽ in ra 0
        }
        return list.get(0);

    }
}
