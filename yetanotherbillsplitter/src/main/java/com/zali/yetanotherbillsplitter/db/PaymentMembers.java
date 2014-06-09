package com.zali.yetanotherbillsplitter.db;

import android.database.sqlite.SQLiteDatabase;

public class PaymentMembers {

    static private final String TABLE = "payment_members";
    static private final String CREATE_TABLE = "CREATE TABLE " + TABLE +
            "(pid INTEGER, mid INTEGER, amount INTEGER)";

    static void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
    }

    private int pid;
    private int mid;
    private int amount;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
