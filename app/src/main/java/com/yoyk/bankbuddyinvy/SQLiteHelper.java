package com.yoyk.bankbuddyinvy;

/**
 * Created by Viki on 10/22/2016.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.yoyk.bankbuddyinvy.Database.Database.DatabaseConstants;

public class SQLiteHelper extends SQLiteOpenHelper implements DatabaseConstants {

    public static final int DB_VERSION = 4;

    public SQLiteHelper(Context context) {
        super(context, "bank.db", null, DB_VERSION);
        getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_BANK + "("
                + COL_BANK_ID+ " INTEGER PRIMARY KEY NOT NULL, " + " "
                + COL_BANK_NAME + " VARCHAR(50) NOT NULL, "
                + COL_BANK_INQUIRY + " VARCHAR(50) NOT NULL, "
                + COL_BANK_CARE + " VARCHAR(50) NOT NULL, "
                + COL_BANK_FAV + " VARCHAR(50) NOT NULL, "
                + COL_BANK_LOGO + " VARCHAR(50) NOT NULL)"
                + ";");
        insertData(db, "SBI", "18000000","911","1","sbi_logo");
        insertData(db, "HDFC", "18000000","911","0","sbi_logo");
        insertData(db, "CITI", "18000000","911","1","sbi_logo");
        insertData(db, "ICICI", "18000000","911","0","sbi_logo");
        insertData(db, "IOB", "18000000","911","0","sbi_logo");
  //      insertData(db, "KOTAK", "18000000","911","0","sbi_logo");
//        insertData(db, "MCC", "18000000","911","0","sbi_logo");

    }

    private static void insertData(SQLiteDatabase db, String name,String inquiry, String care, String fav, String logo) {
        db.execSQL("INSERT INTO " + TABLE_BANK +
                " (" + COL_BANK_NAME+","
                +COL_BANK_INQUIRY+","
                +COL_BANK_CARE+","
                +COL_BANK_FAV+","
                +COL_BANK_LOGO
                + ") VALUES (" +
                "'" + name +"',"+
                "'" + inquiry +"',"+
                "'" + care+"',"+
                "'" + fav+"',"+
                "'" + logo+ "');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BANK);
        onCreate(db);
    }
/*
    public BankList_Model getData(String id)
    {
        return Convert(getDataCursor(id));
    }
    private Cursor getDataCursor(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_BANK+" where "+COL_BANK_ID+"="+id+"", null );
        if (!res.isClosed())
        {
            res.close();
        }
        return res;
    }

    public ArrayList<BankList_Model> getAllBanks(){
        ArrayList<BankList_Model> array_list = new ArrayList<BankList_Model>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_BANK+"", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(Convert(res));
            res.moveToNext();
        }
        if (!res.isClosed())
        {
            res.close();
        }
        return array_list;
    }

    private BankList_Model Convert(Cursor rs)
    {
        String nam = rs.getString(rs.getColumnIndex(DatabaseConstants.COL_BANK_NAME));
        String care = rs.getString(rs.getColumnIndex(DatabaseConstants.COL_BANK_CARE));
        String fav = rs.getString(rs.getColumnIndex(DatabaseConstants.COL_BANK_FAV));
        String inquiry = rs.getString(rs.getColumnIndex(DatabaseConstants.COL_BANK_INQUIRY));
        String bid = rs.getString(rs.getColumnIndex(DatabaseConstants.COL_BANK_ID));
        String logo = rs.getString(rs.getColumnIndex(DatabaseConstants.COL_BANK_LOGO));

        BankList_Model model=new BankList_Model(bid,nam,inquiry,care,fav,logo);
        return model;
    }
*/
}

