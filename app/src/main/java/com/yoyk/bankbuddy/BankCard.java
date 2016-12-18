package com.yoyk.bankbuddy;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.samples.quickstart.analytics.AnalyticsApplication;
import com.yoyk.bankbuddy.model.BankList_Model;

import org.w3c.dom.Text;

import static android.R.attr.name;
import static com.google.android.gms.internal.zzs.TAG;


/**
 * Created by Viki on 11/3/2016.
 */

public class BankCard extends Activity {

    private AdView mAdView;
    BankList_Model bankList_model;
    private Context mContext;

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

        favButtonView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MyApplication.SetFavourite(bankList_model,isChecked);
                if (isChecked) {
                    //_fireFavEvent(bankList_model, isChecked);
                    Toast.makeText(mContext, "clicked " + bankList_model.getBank_name(), Toast.LENGTH_SHORT).show();
                } else {
                    //_fireFavEvent(bankList_model, isChecked);
                    Toast.makeText(mContext, "unchecked " + bankList_model.getBank_name(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        //mAdView = (AdView) findViewById(R.id.adView);
        //AdRequest adRequest = new AdRequest.Builder()
        //        .build();
        //mAdView.loadAd(adRequest);


        try {
            AnalyticsApplication application = (AnalyticsApplication) getApplication();
            Tracker mTracker = application.getDefaultTracker();

            Log.i(TAG, "Setting screen name: " + name);
            mTracker.setScreenName("Image~" + name);
            mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        }catch (Exception e)
        {Toast.makeText(this, "Analytics Exception", Toast.LENGTH_LONG).show();}
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

    private void OnCallCare(View arg0)
    {

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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

}
