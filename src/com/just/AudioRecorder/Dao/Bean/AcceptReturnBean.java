package com.just.AudioRecorder.Dao.Bean;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class AcceptReturnBean implements Parcelable{

	
	public String RoomName;
	public String RoomOwner;
	public List<Message> MsgList;
	public List<UserInfoPublic> Guests;
	
	public AcceptReturnBean(String RoomName,String RoomOwner,
			List<Message> MsgList,List<UserInfoPublic> Guests) {
		// TODO Auto-generated constructor stub
		this.RoomName = RoomName;
		this.RoomOwner = RoomOwner;
		this.MsgList = MsgList;
		this.Guests = Guests;
	}

	public String getRoomName() {
		return RoomName;
	}

	public void setRoomName(String roomName) {
		RoomName = roomName;
	}

	

	public String getRoomOwner() {
		return RoomOwner;
	}

	public void setRoomOwner(String roomOwner) {
		RoomOwner = roomOwner;
	}

	

	public List<Message> getMsgList() {
		return MsgList;
	}

	public void setMsgList(List<Message> msgList) {
		MsgList = msgList;
	}

	public List<UserInfoPublic> getGuests() {
		return Guests;
	}

	public void setGuests(List<UserInfoPublic> guests) {
		Guests = guests;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public static final Parcelable.Creator<AcceptReturnBean> CREATOR = new Parcelable.Creator<AcceptReturnBean>() {
		@SuppressWarnings("unchecked")
		public AcceptReturnBean createFromParcel(Parcel in) {
			List<Message> Msgs = in.readArrayList(Message.class.getClassLoader());
			String RoomName = in.readString();
			String RoomOwner = in.readString();
			List<UserInfoPublic> Guests = in.readArrayList(UserInfoPublic.class.getClassLoader());
			return new AcceptReturnBean(RoomName, RoomOwner, Msgs, Guests);
		}

		public AcceptReturnBean[] newArray(int size) {
			return new AcceptReturnBean[size];
		}
	};

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeList(Guests);
		dest.writeList(MsgList);
		dest.writeString(RoomName);
		dest.writeString(RoomOwner);
	}



}
