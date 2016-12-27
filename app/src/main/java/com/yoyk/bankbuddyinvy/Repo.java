package com.yoyk.bankbuddyinvy;

import com.yoyk.bankbuddyinvy.model.BankList_Model;

/**
 * Created by Viki on 10/23/2016.
 */

public class Repo {
    public static class BankList
    {
        public static BankList_Model[] get()
        {
            BankList_Model[] list=new BankList_Model[]{
                    new BankList_Model("1","SBI","adf","asdf","sdf","dfd"),
                    new BankList_Model("2","ICICI","adf","asdf","sdf","dfd"),
                    new BankList_Model("3","HDFC","adf","asdf","sdf","dfd")
            };
            return list;
        }
    }
}
