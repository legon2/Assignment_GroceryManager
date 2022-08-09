package fpoly.edu.grocerymanager.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "QUAN_LY_TAP_HOA";
    private static final int DB_VERSION = 1;

    static final String CREATE_TABLE_NGUOI_DUNG =
            "create table NguoiDung (maND TEXT PRIMARY KEY NOT NULL," +
                    "hoTen   TEXT NOT NULL," +
                    "soDienThoai   INTEGER NOT NULL," +
                    "matKhau TEXT NOT NULL)";
    //
    static final String CREATE_TABLE_LOAI_HANG =
            "create table LoaiHang (" +
                    "maLoai  INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "tenLoai TEXT NOT NULL)";
    //
    static final String CREATE_TABLE_HANG =
            "create table Hang (" +
                    "maHang  INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "tenHang TEXT    NOT NULL,"     +
                    "gia INTEGER NOT NULL,"    +
                    "maLoai  INTEGER REFERENCES LoaiHang (maloai), " +
                    "hinhAnh BLOB)";
    //
    static final String CREATE_TABLE_HOA_DON =
            "create table HoaDon ("  +
                    "maHD   INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "maND   TEXT    REFERENCES NguoiDung (maND), "+
                    "mahang  INTEGER REFERENCES Hang (maHang), "+
                    "tongTien INTEGER NOT NULL,  "+
                    "ngayLap  DATE NOT NULL,"+
                    "trangThai  INTEGER NOT NULL  )";
    //
    static final String CREATE_TABLE_HOA_DON_CHI_TIET =
            "create table HoaDonChiTiet ("  +
                    "maHD   INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "mahang  INTEGER REFERENCES Hang (maHang), "+
                    "soLuong INTEGER NOT NULL,  "+
                    "ghiChu  TEXT NOT NULL,"+
                    "gia  INTEGER NOT NULL  )";
    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Tạo bảng Người dùng
        db.execSQL(CREATE_TABLE_NGUOI_DUNG);
        //Tạo bảng loại hàng
        db.execSQL(CREATE_TABLE_LOAI_HANG);
        //Tạo bảng hàng
        db.execSQL(CREATE_TABLE_HANG);
        //Tạo bảng hoá đơn
        db.execSQL(CREATE_TABLE_HOA_DON);
        //Tạo bảng hoá đơn
        db.execSQL(CREATE_TABLE_HOA_DON_CHI_TIET);

        //Thêm dữ liệu Người dùng
        db.execSQL(Data_SQLite.INSERT_NGUOI_DUNG);
        //Thêm dữ liệu Loại hàng
        db.execSQL(Data_SQLite.INSERT_LOAI_HANG);
        //Thêm dữ liệu Hàng
//        db.execSQL(Data_SQLite.INSERT_HANG);
        //Thêm dữ liệu Hoá đơn
        db.execSQL(Data_SQLite.INSERT_HOA_DON);
        //Thêm dữ liệu Hoá đơn chi tiết
//        db.execSQL(Data_SQLite.INSERT_HOA_DON_CHI_TIET);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String dropTableNguoiDung = "drop table if exists NguoiDung";
        db.execSQL(dropTableNguoiDung);
        String dropTableLoaiHang= "drop table if exists LoaiHang";
        db.execSQL(dropTableLoaiHang);
        String dropTableHang = "drop table if exists Hang";
        db.execSQL(dropTableHang);
        String dropTableHoaDon = "drop table if exists HoaDon";
        db.execSQL(dropTableHoaDon);
        String dropTableHoaDonChiTiet = "drop table if exists HoaDonChiTiet";
        db.execSQL(dropTableHoaDonChiTiet);

        onCreate(db);

    }
    public void TruyVanKhongTraVe(String sql)
    {
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL(sql);
    }

    public Cursor TruyVanTraVe(String sql)
    {
        SQLiteDatabase db=getWritableDatabase();
        return db.rawQuery(sql, null);
    }
}
