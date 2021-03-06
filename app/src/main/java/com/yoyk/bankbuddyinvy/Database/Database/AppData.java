package com.yoyk.bankbuddyinvy.Database.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yoyk.bankbuddyinvy.model.BankList_Model;

import java.util.ArrayList;

import static com.yoyk.bankbuddyinvy.Database.Database.DatabaseConstants.COL_BANK_FAV;
import static com.yoyk.bankbuddyinvy.Database.Database.DatabaseConstants.COL_BANK_ID;
import static com.yoyk.bankbuddyinvy.Database.Database.DatabaseConstants.TABLE_BANK;

/**
 * Created by Viki on 11/19/2016.
 */

public class AppData {
    SQLiteOpenHelper helper;
    public AppData(Context context) {
        //this.helper = new SQLiteHelper(context);
        this.helper = new DatabaseOpenHelper(context);
    }
    public ArrayList<BankList_Model> getAllBanks() {
        ArrayList<BankList_Model> array_list = new ArrayList<BankList_Model>();
        SQLiteDatabase db = this.helper.getReadableDatabase();
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
    public int Update(BankList_Model model)
    {
        ContentValues cv=new ContentValues();
        cv.put(COL_BANK_FAV,model.getBank_fav());
        SQLiteDatabase db = this.helper.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+TABLE_BANK+" where _id='24'",null);
        int result=db.update(TABLE_BANK,cv,"_id='"+model.getBank_id()+"'",null);

        Cursor res1=db.rawQuery("select * from "+TABLE_BANK+" where _id='24'",null);
        db.close();
        return result;
    }
    public ArrayList<BankList_Model> getMyBanks()
    {
        ArrayList<BankList_Model> array_list = new ArrayList<BankList_Model>();
        SQLiteDatabase db = this.helper.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_BANK+"", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            BankList_Model data=Convert(res);
            if(data.getBank_fav().contentEquals("1"))
                array_list.add(data);
            res.moveToNext();
        }
        if (!res.isClosed())
        {
            res.close();
        }
        return array_list;

    }
    public BankList_Model getData(String id)
    {
        return Convert(getDataCursor(id));
    }
    private BankList_Model Convert(Cursor rs)
    {
        String nam = rs.getString(rs.getColumnIndex(DatabaseConstants.COL_BANK_NAME));
        String care = rs.getString(rs.getColumnIndex(DatabaseConstants.COL_BANK_CARE));
        String fav = rs.getString(rs.getColumnIndex(DatabaseConstants.COL_BANK_FAV));
        String inquiry = rs.getString(rs.getColumnIndex(DatabaseConstants.COL_BANK_INQUIRY));
        String bid = rs.getString(rs.getColumnIndex(COL_BANK_ID));
        String logo = rs.getString(rs.getColumnIndex(DatabaseConstants.COL_BANK_LOGO));

        BankList_Model model=new BankList_Model(bid,nam,inquiry,care,fav,logo);
        return model;
    }
    private Cursor getDataCursor(String id){
        SQLiteDatabase db = this.helper.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_BANK+" where "+COL_BANK_ID+"="+id+"", null );
        if (!res.isClosed())
        {
            res.close();
        }
        return res;
    }
}
