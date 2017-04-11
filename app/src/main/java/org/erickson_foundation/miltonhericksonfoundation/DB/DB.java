package org.erickson_foundation.miltonhericksonfoundation.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 4/11/2017.
 */

public class DB extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;

    private static final String DEFAULT_DATABASE_NAME = "Test";

    private String mDB_name = "";

    public DB(Context ctx){
        super(ctx, DEFAULT_DATABASE_NAME, null, DATABASE_VERSION);
        mDB_name = DEFAULT_DATABASE_NAME;
    }
    public DB(Context ctx, String db_name){
        super(ctx, db_name, null, DATABASE_VERSION);
        mDB_name = db_name;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = " CREATTE TABLE " + mDB_name + "(";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
