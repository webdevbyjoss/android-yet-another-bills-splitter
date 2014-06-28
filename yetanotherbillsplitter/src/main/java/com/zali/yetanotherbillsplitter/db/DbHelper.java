package com.zali.yetanotherbillsplitter.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private final static int VERSION = 1;
    private final static String DB_NAME = "billsplitter";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Member.onCreate(db);
        Payment.onCreate(db);
        PaymentMembers.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Member.onUpgrade(db, oldVersion, newVersion);
        Payment.onUpgrade(db, oldVersion, newVersion);
        PaymentMembers.onUpgrade(db, oldVersion, newVersion);
        this.onCreate(db);
    }

    public long addMember(Member m) {
        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.insert(Member.TABLE, null, m.getValues());
        db.close();
        return id;
    }

    public Member getMember(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        // build query
        Cursor cursor = db.query(Member.TABLE, // a. table
                        Member.COLUMNS, // b. column names
                        Member.KEY_ID + " = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // if we got results get the first one
        if (cursor == null) {
            return null;
        }
        cursor.moveToFirst();

        Member m = new Member();
        m.setId(cursor.getInt(cursor.getColumnIndex(Member.KEY_ID)));
        m.setName(cursor.getString(cursor.getColumnIndex(Member.KEY_NAME)));

        return m;
    }

    public List<Member> getAllMembersList() {
        List<Member> members = new ArrayList<>();

        Cursor cursor = getAllMembersCursor();
        if (!cursor.moveToFirst()) {
            return members;
        }

        Member m = null;
        boolean avail = true;
        while (avail) {
            m = new Member();
            m.setId(cursor.getInt(cursor.getColumnIndex(Member.KEY_ID)));
            m.setName(cursor.getString(cursor.getColumnIndex(Member.KEY_NAME)));
            members.add(m);
            avail = cursor.moveToNext();
        }

        return members;
    }

    public Cursor getAllMembersCursor() {
        String query = "SELECT  * FROM " + Member.TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(query, null);
    }

    public void deleteMember(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Member.TABLE, Member.KEY_ID + " = ?", new String[] {String.valueOf(id)});
        db.close();
    }

    public List<Payment> getPaymentsList() {
        List<Payment> items = new ArrayList<>();

        Cursor cursor = getPaymentsCursor();
        if (!cursor.moveToFirst()) {
            return items;
        }

        Payment p = null;
        boolean avail = true;
        while (avail) {
            p = new Payment();
            p.setId(cursor.getInt(cursor.getColumnIndex(Payment.KEY_ID)));
            p.setInfo(cursor.getString(cursor.getColumnIndex(Payment.KEY_INFO)));
            items.add(p);
            avail = cursor.moveToNext();
        }

        return items;
    }

    public Cursor getPaymentsCursor() {
        String query = "SELECT * FROM " + Payment.TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(query, null);
    }
}
