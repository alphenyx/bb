package com.yoyk.bankbuddyinvy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.yoyk.bankbuddyinvy.model.BankList_Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Viki on 10/26/2016.
 */

public class BankListAdaptor extends BaseAdapter implements Filterable{

    public BankListAdaptor(Context context, BankList_Model[] bankList_models)
    {
        mBankList_models_All=mBankList_models=bankList_models;
        mContext=context;
    }
    private Context mContext;
    private BankList_Model[] mBankList_models;
    private BankList_Model[] mBankList_models_All;
    @Override
    public int getCount() {
        return mBankList_models.length;
    }

    @Override
    public Object getItem(int position) {
        return mBankList_models[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.listitemview, parent, false);
       // ViewHolder viewHolder = (ViewHolder) view.getTag();
        //viewHolder.iconView.setImageResource(R.drawable.ic_logo);
        if(view!=null) {
            ViewHolder viewHolder = new ViewHolder(view);
            String bankLogo=mBankList_models[position].getBank_bank_logo();
            if(bankLogo==null)
                bankLogo="STATEBANKOFINDIA";
            int drawableID = mContext.getResources().getIdentifier(bankLogo.toLowerCase(), "drawable", mContext.getPackageName());
            viewHolder.iconView.setImageResource(drawableID);
            viewHolder.nameView.setText(mBankList_models[position].getBank_name());
            viewHolder.favButtonView.setChecked(mBankList_models[position].getBank_fav().contentEquals("1"));
            viewHolder.favButtonView.setTag(mBankList_models[position]);
            viewHolder.favButtonView.setVisibility(View.INVISIBLE); // favorite button is hidden here
            viewHolder.favButtonView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    BankList_Model m = (BankList_Model) buttonView.getTag();
                    if (isChecked) {
                        _fireFavEvent(m, isChecked);
                        Toast.makeText(mContext, m.getBank_name() + " added to favorites", Toast.LENGTH_SHORT).show();
                    } else {
                        _fireFavEvent(m, isChecked);
                        Toast.makeText(mContext, m.getBank_name() + " removed from favorites", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            view.setTag(viewHolder);
        }
        return view;
    }
    private List _listeners = new ArrayList();
    public synchronized void addFavouriteListener( IFavouriteChange l ) {
        _listeners.add( l );
    }

    public synchronized void removeFavouriteListener( IFavouriteChange l ) {
        _listeners.remove( l );
    }
    private synchronized void _fireFavEvent(BankList_Model model,boolean isFav)
    {
        FavEvent eve=new FavEvent(this,model,isFav);
        Iterator listeners=_listeners.iterator();
        while( listeners.hasNext() ) {
            ( (IFavouriteChange) listeners.next() ).OnFavUpdate( eve );
        }
    }
    public void ResetFilter()
    {
        mBankList_models =mBankList_models_All;
    }
    BankFilter mBfilter=new BankFilter();
    @Override
    public Filter getFilter() {
        return mBfilter;
    }

    public static class ViewHolder{
        public final ImageView iconView;
        public final TextView nameView;
        public final ToggleButton favButtonView;
        public ViewHolder(View view){
            iconView = (ImageView) view.findViewById(R.id.list_item_icon);
            nameView = (TextView) view.findViewById(R.id.list_item_textview);
            favButtonView=(ToggleButton) view.findViewById(R.id.fav);
        }
    }
    private static final int VIEW_TYPE_BANK = 1;
    public class BankFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results=new FilterResults();
            String filterString=constraint.toString().toLowerCase();
            final BankList_Model[] list=mBankList_models_All;
            int count=list.length;
            final ArrayList<BankList_Model> nlist = new ArrayList<BankList_Model>();
            String filterableString ;
            for (int i = 0; i < count; i++) {
                filterableString=list[i].getBank_name();
                if (filterableString.toLowerCase().contains(filterString)) {
                    nlist.add(list[i]);
                }
            }
            Object[] oarray=nlist.toArray();
            results.values= Arrays.copyOf(oarray, oarray.length,BankList_Model[].class);
            results.count= nlist.size();
                //  if (constraint == null || constraint.length() == 0) {
// No filter implemented we return all the list
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if(results.count==0)
                notifyDataSetInvalidated();
            else
            {
                mBankList_models = (BankList_Model[]) results.values;
                notifyDataSetChanged();
            }
        }
    }
}

