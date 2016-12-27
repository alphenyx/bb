package com.yoyk.bankbuddyinvy.model;

/**
 * Created by Viki on 11/3/2016.
 */

public class Fragment_Model {
    private String fragment_label;
    private BankList_Model[] bank_list;
    public Fragment_Model(String flabel,BankList_Model[] bList)
    {
        this.fragment_label=flabel;
        this.bank_list=bList;
    }
    public String getFragment_Label(){
        return this.fragment_label;
    }
    public BankList_Model[] getBank_list()
    {
        return this.bank_list;
    }
}
