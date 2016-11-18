package com.yoyk.bankbuddy;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.yoyk.bankbuddy.Database.Database.DatabaseConstants;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.os.Bundle;
import android.view.Menu;
/**
 * Created by Viki on 10/22/2016.
 */

public class SearchableActivity extends ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_list);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }

    private void doMySearch(String query) {
        SQLiteHelper sqLiteHelper = ((MyApplication)getApplication()).getDbHelper();
        Cursor cursor = sqLiteHelper.getReadableDatabase().rawQuery("SELECT " + DatabaseConstants.COL_LANG_ID + ", " +
                DatabaseConstants.COL_LANG_NAME + " FROM " + DatabaseConstants.TABLE_LANG +
                " WHERE upper(" + DatabaseConstants.COL_LANG_NAME + ") like '%" + query.toUpperCase() + "%'", null);
        setListAdapter(new SimpleCursorAdapter(this, R.layout.container_list_item_view, cursor,
                new String[] {DatabaseConstants.COL_LANG_NAME }, new int[]{R.id.list_item}));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

}
