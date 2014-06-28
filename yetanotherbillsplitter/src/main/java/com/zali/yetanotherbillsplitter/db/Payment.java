package com.zali.yetanotherbillsplitter.db;

import android.database.sqlite.SQLiteDatabase;

public class Payment {

    static public final String TABLE = "payments";

    static public final String KEY_ID = "_id";
    static public final String KEY_INFO = "info";
    static public final String[] COLUMNS = {KEY_ID, KEY_INFO};

    static private final String CREATE_TABLE = "CREATE TABLE " + TABLE
            + " (" + KEY_ID + " INTEGER primary key autoincrement, " + KEY_INFO + " TEXT)";

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
