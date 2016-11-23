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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.yoyk.bankbuddy.model.BankList_Model;


/**
 * Created by Viki on 11/3/2016.
 */

public class BankCard extends Activity  {

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bank_card);
        Intent intent = getIntent();
        Bundle b=getIntent().getExtras();
        //Button btn = (Button)findViewById(R.id.button);
        TextView txtView =(TextView)findViewById(R.id.textView);
        ImageView imgViewBalance = (ImageView)findViewById(R.id.imageView);
        this.FormatView("State Bank of India");

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

        if(b!=null) {
        }

        mAdView = (AdView) findViewById(R.id.adView_BankCard);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("AC98C820A50B4AD8A2106EDE96FB87D4").build();
        mAdView.loadAd(adRequest);

    }

    private void FormatView(String title)
    {
        int drawableID = this.getBaseContext().getResources().getIdentifier("sbi_logo", "drawable", this.getBaseContext().getPackageName());
        ImageView imgViewHeader = (ImageView)findViewById(R.id.imageViewHeader);
        imgViewHeader.setImageResource(drawableID);

        drawableID = this.getBaseContext().getResources().getIdentifier("phone", "drawable", this.getBaseContext().getPackageName());
        ImageView imgViewDial = (ImageView)findViewById(R.id.imageView);
        ImageView imgViewCustomerCare = (ImageView)findViewById(R.id.imageViewCustomerCare);
        imgViewDial.setImageResource(drawableID);
        drawableID = this.getBaseContext().getResources().getIdentifier("phone1", "drawable", this.getBaseContext().getPackageName());
        imgViewCustomerCare.setImageResource(drawableID);
    }
    private void OnCallBalance(View arg0)
    {

        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:02230256767"));
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
