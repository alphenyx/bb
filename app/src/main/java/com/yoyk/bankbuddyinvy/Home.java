package com.yoyk.bankbuddyinvy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.yoyk.bankbuddyinvy.model.BankList_Model;

import static android.R.attr.name;
import static com.yoyk.bankbuddyinvy.MyBankFragment.EXTRA_MESSAGE;

public class Home extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SearchView mSearchView=null;
    ListView mResultsView=null;
    BankListAdaptor mBankListAdaptor=null;
    ImageButton mSearchButton;
    private AdView mAdView;
    LinearLayout mTitlebar;
    private Tracker mTracker;
    String name="HomeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_scrolling);
        mSearchView = (SearchView) findViewById(R.id.searchView);
        mResultsView = (ListView) findViewById(R.id.results_view);
        mSearchButton=(ImageButton) findViewById(R.id.searchbutton);
        mTitlebar=(LinearLayout) findViewById(R.id.titlebar);
        mBankListAdaptor=new BankListAdaptor(getApplicationContext(),MyApplication.getBankList());
        mResultsView.setAdapter(mBankListAdaptor);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTracker!=null) {
                    mTracker.setScreenName(name+"-"+"search button clicked");
                    mTracker.send(new HitBuilders.ScreenViewBuilder().build());
                }
                mSearchView.setVisibility(View.VISIBLE);
                mTitlebar.setVisibility(View.INVISIBLE);
                mSearchView.setFocusable(true);
            }
        });
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

        MobileAds.initialize(getApplicationContext(),"ca-app-pub-5060285330387799~4750723039");

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);


        MyApplication application = (MyApplication) getApplication();
        mTracker = application.getDefaultTracker();

    }
    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search here");
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
            mSearchView.setVisibility(View.INVISIBLE);
            mTitlebar.setVisibility(View.VISIBLE);
            myView.setVisibility(View.VISIBLE);
            allView.setVisibility(View.VISIBLE);
        } else {
            mResultsView.setFilterText(newText.toString());
            mResultsView.setVisibility(View.VISIBLE);
            mTitlebar.setVisibility(View.INVISIBLE);
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
        if(mTracker!=null) {
            mTracker.setScreenName(name+"  email:"+UserEmailFetcher.getEmail(getApplicationContext()));
            mTracker.send(new HitBuilders.ScreenViewBuilder().build());
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
