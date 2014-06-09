package com.zali.yetanotherbillsplitter.db;

import android.database.sqlite.SQLiteDatabase;

public class Payments {

    static private final String TABLE = "payments";
    static private final String CREATE_TABLE = "CREATE TABLE " + TABLE
            + " (_id INTEGER primary key autoincrement, info TEXT)";

    static void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
    }

    private int id;
    private String info;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
