package com.yoyk.bankbuddy;

import com.yoyk.bankbuddy.model.BankList_Model;

import java.util.EventObject;

public class FavEvent extends EventObject {
    private BankList_Model _model;
    private boolean _isFav;
    public FavEvent( Object source, BankList_Model mood, boolean isFav ) {
        super( source );
        _model = mood;
        _isFav=isFav;
    }
    public BankList_Model model() {
        return _model;
    }
    public Boolean IsFav(){
        return _isFav;
    }
}
