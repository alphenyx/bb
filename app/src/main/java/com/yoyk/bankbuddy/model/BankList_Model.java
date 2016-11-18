package com.yoyk.bankbuddy.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BankList_Model implements Parcelable {
   private String bank_bank_logo;
   private String bank_care;
   private String bank_fav;
   private String bank_id;
   private String bank_inquiry;
   private String bank_name;

   public BankList_Model() {
   }

   public BankList_Model(String var1, String var2, String var3, String var4, String var5, String var6) {
      this.bank_id = var1;
      this.bank_name = var2;
      this.bank_inquiry = var3;
      this.bank_care = var4;
      this.bank_fav = var5;
      this.bank_bank_logo = var6;
   }

   public String getBank_bank_logo() {
      return this.bank_bank_logo;
   }

   public String getBank_care() {
      return this.bank_care;
   }

   public String getBank_fav() {
      return this.bank_fav;
   }

   public String getBank_id() {
      return this.bank_id;
   }

   public String getBank_inquiry() {
      return this.bank_inquiry;
   }

   public String getBank_name() {
      return this.bank_name;
   }

   public void setBank_bank_logo(String var1) {
      this.bank_bank_logo = var1;
   }

   public void setBank_care(String var1) {
      this.bank_care = var1;
   }

   public void setBank_fav(String var1) {
      this.bank_fav = var1;
   }

   public void setBank_id(String var1) {
      this.bank_id = var1;
   }

   public void setBank_inquiry(String var1) {
      this.bank_inquiry = var1;
   }

   public void setBank_name(String var1) {
      this.bank_name = var1;
   }

   // Parcelling part
   public BankList_Model(Parcel in){
      String[] data = new String[6];

      in.readStringArray(data);
      this.bank_id = data[0];
      this.bank_name = data[1];
      this.bank_inquiry = data[2];
      this.bank_care = data[3];
      this.bank_fav = data[4];
      this.bank_bank_logo = data[5];

   }
   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
      dest.writeStringArray(new String[]{this.bank_id,this.bank_name,this.bank_inquiry,this.bank_care, this.bank_fav, this.bank_bank_logo});
   }
   public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
      public BankList_Model createFromParcel(Parcel in) {
         return new BankList_Model(in);
      }

      public BankList_Model[] newArray(int size) {
         return new BankList_Model[size];
      }
   };
}
