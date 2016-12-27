package com.yoyk.bankbuddyinvy;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yoyk.bankbuddyinvy.model.BankList_Model;
import com.yoyk.bankbuddyinvy.model.Fragment_Model;

/**
 * Created by Viki on 10/22/2016.
 */

public class MyBankFragment extends Fragment  {

    public static final String EXTRA_MESSAGE = "com.yoyk.bankbuddy.MESSAGE";
    private ListView mListView;
    private TextView mTextView;
    private int mPosition = ListView.INVALID_POSITION;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //CustomListAdaptor adapter=new CustomListAdaptor(this.getActivity(),0,Repo.BankList.get());
     //   ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Banks, android.R.layout.simple_list_item_1);
   //     setListAdapter(adapter);
  //      getListView().setOnItemClickListener(this);
    }
    private BankListAdaptor mBankListAdaptor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        //MyApplication myApp=new MyApplication();
        Fragment_Model model=MyApplication.getModel(this.getTag());
        View rootView = inflater.inflate(R.layout.grid_tile, container, false);

        mTextView=(TextView)rootView.findViewById(R.id.fragname);
        mTextView.setText(model.getFragment_Label());

        //ArrayAdapter mBankListAdaptor = ArrayAdapter.createFromResource(getActivity(), R.array.Banks, android.R.layout.simple_list_item_1);
        mBankListAdaptor=new BankListAdaptor(getActivity(),model.getBank_list());
        mListView = (ListView) rootView.findViewById(R.id.listbank);
        mListView.setAdapter(mBankListAdaptor);
        setListViewHeightBasedOnChildren(mListView);

        final Intent intent = new Intent(this.getActivity(),BankCard.class);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BankList_Model item=(BankList_Model)mBankListAdaptor.getItem(position);
                Bundle b=new Bundle();
                b.putParcelable(EXTRA_MESSAGE,item);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        return rootView;
    }
    public static void setListViewHeightBasedOnChildren(ListView listView)
    {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight=0;
        View view = null;

        for (int i = 0; i < listAdapter.getCount(); i++)
        {
            view = listAdapter.getView(i, view, listView);

            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,
                        ViewGroup.LayoutParams.MATCH_PARENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + ((listView.getDividerHeight()) * (listAdapter.getCount()));

        listView.setLayoutParams(params);
        listView.requestLayout();

    }

}
