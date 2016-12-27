package com.yoyk.bankbuddyinvy;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
/**
 * Created by Viki on 10/22/2016.
 */

public class MyBanks extends Fragment implements AdapterView.OnItemClickListener {

    private ListView mListView;
    private int mPosition = ListView.INVALID_POSITION;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       // CustomListAdaptor adapter=new CustomListAdaptor(this.getActivity(),0,Repo.BankList.get());
        //ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Banks, android.R.layout.simple_list_item_1);
//        setListAdapter(adapter);
  //      getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }
}
