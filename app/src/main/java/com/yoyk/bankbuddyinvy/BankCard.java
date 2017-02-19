package com.yoyk.bankbuddyinvy;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.yoyk.bankbuddyinvy.model.BankList_Model;


/**
 * Created by Viki on 11/3/2016.
 */

public class BankCard extends Activity  {

    private AdView mAdView;
    BankList_Model bankList_model;
    private Context mContext;
    private Tracker mTracker;
    String name="BankCardActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bank_card);
        Intent intent = getIntent();
        Bundle b=getIntent().getExtras();
        bankList_model = (BankList_Model)b.getParcelable("com.yoyk.bankbuddy.MESSAGE");
        ValidateModel(bankList_model);
        FormatView(bankList_model);
        mContext=getApplicationContext();
        //Button btn = (Button)findViewById(R.id.button);
        TextView txtView =(TextView)findViewById(R.id.textView);
        TextView txtViewCustCare =(TextView)findViewById(R.id.textViewCustomerCare);
        TextView txtRetire=(TextView)findViewById(R.id.txtRetire);

        ImageView imgViewBalance = (ImageView)findViewById(R.id.imageView);
        ToggleButton favButtonView = (ToggleButton) findViewById(R.id.fav);

        this.FormatView(bankList_model);

        txtView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                OnCallBalance(v);
            }
        });
        imgViewBalance.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                OnCallBalance(v);
            }
        });

        txtView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                OnCallBalance(v);
            }
        });
        txtViewCustCare.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                OnCallCare(v);
            }
        });
        imgViewBalance.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                OnCallCare(v);
            }
        });

        if(bankList_model.getBank_id().equalsIgnoreCase("100")) {
            txtRetire.setVisibility(View.VISIBLE);

            txtRetire.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OnShowRetirement(v);
                }
            });
        }

        favButtonView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MyApplication.SetFavourite(bankList_model,isChecked);
                if (isChecked) {
                    if(mTracker!=null) {
                        mTracker.setScreenName(name+"-"+bankList_model.getBank_name()+"-added to favourites");
                        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
                    }
                    //_fireFavEvent(bankList_model, isChecked);
                    Toast.makeText(mContext, "clicked " + bankList_model.getBank_name(), Toast.LENGTH_SHORT).show();
                } else {
                    if(mTracker!=null) {
                        mTracker.setScreenName(name+"-"+bankList_model.getBank_name()+"-removed from favourites");
                        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
                    }
                    //_fireFavEvent(bankList_model, isChecked);
                    Toast.makeText(mContext, "unchecked " + bankList_model.getBank_name(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // share it
        ImageButton btn_share=(ImageButton)findViewById(R.id.shareit);
        btn_share.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                shareIt();
            }
        });

        mAdView = (AdView) findViewById(R.id.adView_BankCard);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
        MyApplication application = (MyApplication) getApplication();
        mTracker = application.getDefaultTracker();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mTracker!=null) {
            mTracker.setScreenName(name+"-"+bankList_model.getBank_name()+"  email:"+UserEmailFetcher.getEmail(getApplicationContext()));
            mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        }
    }

    private void ValidateModel(BankList_Model bankList_model)
    {
        if(bankList_model.getBank_bank_logo() == null || bankList_model.getBank_bank_logo() == "")
            bankList_model.setBank_bank_logo("sbi_logo");
        if(bankList_model.getBank_care() == null || bankList_model.getBank_care() == "")
            bankList_model.setBank_care("1800300300");
        if(bankList_model.getBank_inquiry() == null || bankList_model.getBank_inquiry() == "")
            bankList_model.setBank_care("02230256767");
        if(bankList_model.getBank_name() == null || bankList_model.getBank_name() == "")
            bankList_model.setBank_name("State Bank of India");
    }

    private void FormatView(BankList_Model bankList_model)
    {
        int drawableID = this.getBaseContext().getResources().getIdentifier(bankList_model.getBank_bank_logo(), "drawable", this.getBaseContext().getPackageName());
        ImageView imgViewHeader = (ImageView)findViewById(R.id.imageViewHeader);
        imgViewHeader.setImageResource(drawableID);

        drawableID = this.getBaseContext().getResources().getIdentifier("phone", "drawable", this.getBaseContext().getPackageName());
        ImageView imgViewDial = (ImageView)findViewById(R.id.imageView);
        ImageView imgViewCustomerCare = (ImageView)findViewById(R.id.imageViewCustomerCare);
        imgViewDial.setImageResource(drawableID);
        drawableID = this.getBaseContext().getResources().getIdentifier("phone", "drawable", this.getBaseContext().getPackageName());
        imgViewCustomerCare.setImageResource(drawableID);

        TextView textViewDial = (TextView)findViewById(R.id.textView);
        TextView textViewCare = (TextView)findViewById(R.id.textViewCustomerCare);
        TextView textViewHeader = (TextView)findViewById(R.id.textViewHeader);
        ToggleButton favButtonView = (ToggleButton) findViewById(R.id.fav);


        textViewDial.setText("Check Balance ("+bankList_model.getBank_inquiry()+")");
        textViewCare.setText("Customer Care ("+bankList_model.getBank_care()+")");
        textViewHeader.setText(bankList_model.getBank_name());
        favButtonView.setChecked(bankList_model.getBank_fav().contentEquals("1"));

        int height_in_pixels = textViewHeader.getLineCount() * textViewHeader.getLineHeight(); //approx height text
        textViewHeader.setHeight(height_in_pixels);

    }

    private void OnCallBalance(View arg0)
    {
        if(mTracker!=null) {
            mTracker.setScreenName(name+"-"+bankList_model.getBank_name()+"-balance clicked");
            mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        }
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:"+bankList_model.getBank_inquiry()));
        if(checkPermission(1))
            startActivity(callIntent);
        else
        {
            BankCard.super.requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 20);
        }
    }
    private void OnFavClicked(View arg0)
    {

    }
    private void OnShowRetirement(View arg0)
    {
        final Intent intent = new Intent(this,PFPredict.class);
        startActivity(intent);
    }
    private void OnCallCare(View arg0)
    {
        if(mTracker!=null) {
            mTracker.setScreenName(name+"-"+bankList_model.getBank_name()+"-customer care clicked");
            mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        }
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:"+bankList_model.getBank_care()));
        if(checkPermission(1))
            startActivity(callIntent);
        else
        {
            BankCard.super.requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 20);
        }
    }

    @Override
    public void onRequestPermissionsResult (int requestCode,
                                            String[] permissions,
                                            int[] grantResults)
    {
        switch (requestCode)
        {}
    }

    private Boolean checkPermission(int i)
    {
        if(i == 1)
        {
            Context context = this.getApplicationContext();
            PackageManager pm = context.getPackageManager();
            int hasPerm = pm.checkPermission(
                    Manifest.permission.CALL_PHONE,
                    context.getPackageName());
            return true;//(hasPerm == PackageManager.PERMISSION_GRANTED);

        }
        return false;
    }

    private void shareIt() {
//sharing implementation here
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "BankBuddy");
        if(!bankList_model.getBank_id().equalsIgnoreCase("100")) {
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Now check your Bank balance on a tap, click here to know more https://play.google.com/store/apps/details?id=com.yoyk.bankbuddyinvy ");
        }
        else
        {
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Check your PF balance and see how much you save for your retirement, click here to know more https://play.google.com/store/apps/details?id=com.yoyk.bankbuddyinvy ");
        }
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

}
