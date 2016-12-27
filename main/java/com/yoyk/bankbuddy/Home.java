package com.yoyk.bankbuddyinvy;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.yoyk.bankbuddyinvy.model.BankList_Model;

import static com.yoyk.bankbuddyinvy.MyBankFragment.EXTRA_MESSAGE;

public class Home extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SearchView mSearchView=null;
    ListView mResultsView=null;
    BankListAdaptor mBankListAdaptor=null;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_scrolling);
        mSearchView = (SearchView) findViewById(R.id.searchView);
        mResultsView = (ListView) findViewById(R.id.results_view);
        mBankListAdaptor=new BankListAdaptor(getApplicationContext(),MyApplication.getBankList());
        mResultsView.setAdapter(mBankListAdaptor);
        mResultsView.setTextFilterEnabled(true);
        setupSearchView();
        final Intent intent = new Intent(this,BankCard.class);

        mResultsView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BankList_Model item=(BankList_Model)mBankListAdaptor.getItem(position);
                Bundle b=new Bundle();
                b.putParcelable(EXTRA_MESSAGE,item);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
    }
    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search Here");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean onQueryTextChange(String newText) {
        View myView=findViewById(R.id.mybank);
        View allView=findViewById(R.id.otherbanks);
        if (TextUtils.isEmpty(newText)) {
            mBankListAdaptor.ResetFilter();
            mResultsView.clearTextFilter();
            mResultsView.setVisibility(View.INVISIBLE);
            myView.setVisibility(View.VISIBLE);
            allView.setVisibility(View.VISIBLE);
        } else {
            mResultsView.setFilterText(newText.toString());
            mResultsView.setVisibility(View.VISIBLE);
            myView.setVisibility(View.INVISIBLE);
            allView.setVisibility(View.INVISIBLE);
        }
        return true;
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }

}
