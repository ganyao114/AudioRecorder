package com.just.AudioRecorder.Dao.Bean;

import android.os.Parcel;
import android.os.Parcelable;

public class getRequest implements Parcelable{
	public String Sender;
	public String RoomName;
	
	public getRequest(String Sender,String RoomName) {
		// TODO Auto-generated constructor stub
		this.Sender = Sender;
		this.RoomName = RoomName;
	}

	public String getSender() {
		return Sender;
	}

	public void setSender(String sender) {
		Sender = sender;
	}

	public String getRoomName() {
		return RoomName;
	}

	public void setRoomName(String roomName) {
		RoomName = roomName;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public static final Parcelable.Creator<getRequest> CREATOR = new Parcelable.Creator<getRequest>() 
		     {
		         public getRequest createFromParcel(Parcel in) 
		         {
		             String Sender = in.readString();
		             String RoomName = in.readString();
		        	 return new getRequest(Sender,RoomName);
		         }

		         public getRequest[] newArray(int size) 
		         {
		             return new getRequest[size];
		         }
		     };

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(Sender);
		dest.writeString(RoomName);
	}

}
