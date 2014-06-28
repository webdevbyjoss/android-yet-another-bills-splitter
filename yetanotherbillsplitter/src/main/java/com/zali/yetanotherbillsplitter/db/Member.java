package com.zali.yetanotherbillsplitter.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class Member {

    static public final String TABLE = "members";

    static public final String KEY_ID = "_id";
    static public final String KEY_NAME = "name";
    static public final String[] COLUMNS = {KEY_ID, KEY_NAME};

    static private final String CREATE_TABLE = "CREATE TABLE " + TABLE
            + " (" + KEY_ID + " INTEGER primary key autoincrement, " + KEY_NAME + " TEXT)";

    public Member(String name) {
        this.name = name;
    }

    public Member() {}

    static void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
    }

    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContentValues getValues() {
        ContentValues v = new ContentValues();
        v.put(KEY_NAME, name);
        return v;
    }

    public String toString() {
        return name;
    }
}