package com.zali.yetanotherbillsplitter.db;

import android.database.sqlite.SQLiteDatabase;

public class Members {

    static private final String TABLE = "members";
    static private final String CREATE_TABLE = "CREATE TABLE " + TABLE
            + " (_id INTEGER primary key autoincrement, name TEXT)";

    static void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
    }

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
