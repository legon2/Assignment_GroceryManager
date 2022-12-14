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
        //T???o b???ng Ng?????i d??ng
        db.execSQL(CREATE_TABLE_NGUOI_DUNG);
        //T???o b???ng lo???i h??ng
        db.execSQL(CREATE_TABLE_LOAI_HANG);
        //T???o b???ng h??ng
        db.execSQL(CREATE_TABLE_HANG);
        //T???o b???ng ho?? ????n
        db.execSQL(CREATE_TABLE_HOA_DON);
        //T???o b???ng ho?? ????n
        db.execSQL(CREATE_TABLE_HOA_DON_CHI_TIET);

        //Th??m d??? li???u Ng?????i d??ng
        db.execSQL(Data_SQLite.INSERT_NGUOI_DUNG);
        //Th??m d??? li???u Lo???i h??ng
        db.execSQL(Data_SQLite.INSERT_LOAI_HANG);
        //Th??m d??? li???u H??ng
//        db.execSQL(Data_SQLite.INSERT_HANG);
        //Th??m d??? li???u Ho?? ????n
        db.execSQL(Data_SQLite.INSERT_HOA_DON);
        //Th??m d??? li???u Ho?? ????n chi ti???t
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
