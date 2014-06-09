package com.zali.yetanotherbillsplitter.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private final static int VERSION = 1;
    private final static String DB_NAME = "billsplitter";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Members.onCreate(db);
        Payments.onCreate(db);
        PaymentMembers.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Members.onUpgrade(db, oldVersion, newVersion);
        Payments.onUpgrade(db, oldVersion, newVersion);
        PaymentMembers.onUpgrade(db, oldVersion, newVersion);
        this.onCreate(db);
    }
}
