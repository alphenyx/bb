package com.yoyk.bankbuddyinvy;

import android.app.Application;
import android.util.Log;
import com.yoyk.bankbuddyinvy.Database.Database.AppData;
import com.yoyk.bankbuddyinvy.model.BankList_Model;
import com.yoyk.bankbuddyinvy.model.Fragment_Model;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.Arrays;

public class MyApplication extends Application {
	private static final String TAG = MyApplication.class.getSimpleName(); 
	private static SQLiteHelper _dbHelper;
	private static AppData _data;
	private Tracker mTracker;

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate my app");
	    _dbHelper = new SQLiteHelper(this);
		_data=new AppData(this.getApplicationContext());
	}
	synchronized public Tracker getDefaultTracker() {
		if (mTracker == null) {
			GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
			// To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
			mTracker = analytics.newTracker(R.xml.global_tracker);
		}
		return mTracker;
	}
	public static BankList_Model[] getBankList()
	{
		BankList_Model[] list=new BankList_Model[]{
				new BankList_Model("1","SBI","adf","asdf","sdf","dfd"),
				new BankList_Model("2","ICICI","adf","asdf","sdf","dfd"),
				new BankList_Model("3","HDFC","adf","asdf","sdf","dfd")
		};
		Object[] oarray=_data.getAllBanks().toArray();
		list=Arrays.copyOf(oarray,oarray.length,BankList_Model[].class);
		return list;
		//return list;
	}
	public static BankList_Model[] getOtherBankList()
	{
		BankList_Model[] list=new BankList_Model[]{
				new BankList_Model("1","IOB","adf","asdf","sdf","dfd"),
				new BankList_Model("2","CITI","adf","asdf","sdf","dfd"),
				new BankList_Model("3","KOTAK","adf","asdf","sdf","dfd")
		};
		return list;
	}

	public static void SetFavourite(BankList_Model model,boolean isFavourite)
	{
		BankList_Model localmodel=model;
		if(isFavourite)
			localmodel.setBank_fav("1");
		else
			localmodel.setBank_fav("0");
		_data.Update(localmodel);
	}
	private static Fragment_Model getMyBanks()
	{
		//Fragment_Model model=new Fragment_Model("My Banks",getBankList());
		Object[] oarray=_data.getMyBanks().toArray();
		Fragment_Model model=new Fragment_Model("My Banks", Arrays.copyOf(oarray,oarray.length,BankList_Model[].class) );
		return model;
	}

	public static Fragment_Model getModel(String tag)
	{
		Fragment_Model model;
		if(tag.equals("mybank"))
		{
			model=getMyBanks();
		}
		else
		{
			model=getOtherBanks();
		}
		return model;
	}
	public static String getBankName(String id)
	{
		return "amoeba";
	}
	private static Fragment_Model getOtherBanks()
	{
	//	Fragment_Model model=new Fragment_Model("Other Banks",getOtherBankList());
		Object[] oarray=_data.getAllBanks().toArray();
		Fragment_Model model=new Fragment_Model("Other Banks", Arrays.copyOf(oarray,oarray.length,BankList_Model[].class) );
		return model;
	}
	public SQLiteHelper getDbHelper() {
		return _dbHelper;
	}	
}
