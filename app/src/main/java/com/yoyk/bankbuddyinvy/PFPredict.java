package com.yoyk.bankbuddyinvy;

/**
 * Created by Vigneswaran_Sugumaar on 2/18/2017.
 */
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.DecimalFormat;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.yoyk.bankbuddyinvy.model.BankList_Model;

import static android.R.attr.y;


public class PFPredict extends Activity {
    private AdView mAdView;
    private Context mContext;
    private Tracker mTracker;
    String name="PFPredictActivity";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pf_predict);
        mContext=getApplicationContext();
        mAdView = (AdView) findViewById(R.id.adView_BankCard);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
        MyApplication application = (MyApplication) getApplication();
        mTracker = application.getDefaultTracker();

        Button calculateButton = (Button) findViewById(R.id.btnCalculate);

        calculateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                OnCalculateRetirementFund(v);
            }
        });
    }

    private void OnCalculateRetirementFund(View v) {
        TextView outputLabel = (TextView) findViewById(R.id.outputLabel);
        TextView retirementFund = (TextView) findViewById(R.id.outputRetire);

        EditText inputAge = (EditText) findViewById(R.id.inputAge);
        EditText inputPFBalance = (EditText) findViewById(R.id.inputCurrentPF);
        EditText inputBasic = (EditText) findViewById(R.id.inputBasic);

        String strAge=inputAge.getText().toString();
        String strPFBalance=inputPFBalance.getText().toString();
        String strBasic=inputBasic.getText().toString();

        Double funds=CalculateRetirementFund(Integer.parseInt(strAge),Double.parseDouble(strPFBalance),Double.parseDouble(strBasic));
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String numberAsString = decimalFormat.format(funds);
        retirementFund.setText("Rs "+numberAsString);
        outputLabel.setVisibility(View.VISIBLE);

        if(mTracker!=null) {
            mTracker.setScreenName(name +" calculate clicked. age: "+strAge+" PF Balance:"+strPFBalance+" Basic:"+strBasic+"  email:"+UserEmailFetcher.getEmail(mContext));
            mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mTracker!=null) {
            mTracker.setScreenName(name);
            mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    //constant
    int exp_retirement_age=58;
    Double _exp_annual_raise_perc=8.0;
    Double user_EPF_contri_perc=12.0;
    Double employeer_EPF_contri_perc=3.68;
    Double EPF_interest_rate_perc=8.65;
    private Double CalculateRetirementFund(int user_age,Double past_EPF_balanace,Double user_basic_sal)
    {
        int rem_work_years = exp_retirement_age - user_age;
        Double user_basic_sal_annual = user_basic_sal * 12;			// Basic salary

        Double balance_funds=0.0;
        Double last_balance_funds=0.0;

        for(int y=1; y<=rem_work_years; y++)
        {
            // NO raise or hike in 1st year
            Double exp_annual_raise_perc = 0.0;
            if(y ==1 )
            {
                exp_annual_raise_perc = 0.0;
            }
            else
            {
                exp_annual_raise_perc = _exp_annual_raise_perc;
            }

            Double inc_user_basic_sal_annual = (user_basic_sal_annual / 100) * exp_annual_raise_perc;		// annual increment
            Double inc_user_basic_sal_annual_total = user_basic_sal_annual + inc_user_basic_sal_annual;	// annualy increment salary

            Double annual_user_EPF_contri = (inc_user_basic_sal_annual_total * user_EPF_contri_perc) / 100;			// your ePF for the current year
            Double annual_employeer_EPF_contri = (inc_user_basic_sal_annual_total * employeer_EPF_contri_perc) / 100;	// employers ePF contribution for the current year

            Double annual_EPF_total = annual_user_EPF_contri + annual_employeer_EPF_contri;				// annual ePF collection
            if(y == 1)
            {
                // balance funds for 1st year (if no, past ePF balance)
                balance_funds = annual_EPF_total + ((annual_EPF_total/100) * EPF_interest_rate_perc);
                if(past_EPF_balanace > 0)
                    // balance funds for 1st year (if it has past ePF balance)
                    balance_funds = annual_EPF_total + past_EPF_balanace + (((annual_EPF_total + balance_funds) / 100) * EPF_interest_rate_perc);
            }
            else
            {
                // balance funds for rest of the years
                balance_funds = annual_EPF_total + last_balance_funds + (((annual_EPF_total + balance_funds) / 100) * EPF_interest_rate_perc);
            }

            last_balance_funds = balance_funds;
            Double annual_EPF_total_interest = (annual_EPF_total / 100) * EPF_interest_rate_perc;			// ePF Doubleerest
            user_basic_sal_annual = inc_user_basic_sal_annual_total;
        }
        return balance_funds;
    }
}
