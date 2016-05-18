package com.just.AudioRecorder.Dao.Bean;

import android.os.Parcel;
import android.os.Parcelable;

public class UserInfoPublic implements Parcelable{
	private String LoginName;
	private String Mail;
	public UserInfoPublic(String LoginName,String Mail) {
		// TODO Auto-generated constructor stub
		this.LoginName = LoginName;
		this.Mail = Mail;
	}
	
	public String getLoginName() {
		return LoginName;
	}
	public void setLoginName(String loginName) {
		LoginName = loginName;
	}
	public String getMail() {
		return Mail;
	}
	public void setMail(String mail) {
		Mail = mail;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(LoginName);
		dest.writeString(Mail);
	}
	public static final Parcelable.Creator<UserInfoPublic> CREATOR = new Parcelable.Creator<UserInfoPublic>() 
		     {
		         public UserInfoPublic createFromParcel(Parcel in) 
		         {
		             String LoginName = in.readString();
		             String Mail = in.readString();
		        	 return new UserInfoPublic(LoginName,Mail);
		         }

		         public UserInfoPublic[] newArray(int size) 
		         {
		             return new UserInfoPublic[size];
		         }
		     };
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
