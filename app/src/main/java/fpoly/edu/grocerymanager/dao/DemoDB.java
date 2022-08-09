package fpoly.edu.grocerymanager.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import fpoly.edu.grocerymanager.database.DbHelper;

public class DemoDB {
    private SQLiteDatabase database;

    public DemoDB(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
    }
}
