package com.yoyk.bankbuddyinvy.Database.Database;

/**
 * Created by Viki on 11/19/2016.
 */
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import android.content.Context;

import static com.yoyk.bankbuddyinvy.Database.Database.DatabaseConstants.DATABASE_VERSION;
import static com.yoyk.bankbuddyinvy.Database.Database.DatabaseConstants.DB_NAME_NEW;

public class DatabaseOpenHelper extends SQLiteAssetHelper {

    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME_NEW, null, DATABASE_VERSION);
    }
}
