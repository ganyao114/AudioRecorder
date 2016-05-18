package com.just.AudioRecorder.Dao.Bean;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;



public class RequestBean implements Parcelable{

	public List<Message> Msgs;
	public getRequest request;
	public String stoped;
	public List<UserInfoPublic> Guests;
	
	public RequestBean(List<Message> Msgs,getRequest request,
			 String stoped,List<UserInfoPublic> Guests) {
		// TODO Auto-generated constructor stub
		this.Msgs = Msgs;
		this.request = request;
		this.stoped = stoped;
		this.Guests = Guests;
	}

	public List<Message> getMsgs() {
		return Msgs;
	}

	public void setMsgs(List<Message> msgs) {
		Msgs = msgs;
	}

	public getRequest getRequest() {
		return request;
	}

	public void setRequest(getRequest request) {
		this.request = request;
	}

	public String getStoped() {
		return stoped;
	}

	public void setStoped(String stoped) {
		this.stoped = stoped;
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

	public static final Parcelable.Creator<RequestBean> CREATOR = new Parcelable.Creator<RequestBean>() {
		@SuppressWarnings("unchecked")
		public RequestBean createFromParcel(Parcel in) {
			List<Message> Msgs = in.readArrayList(Message.class.getClassLoader());
			getRequest request = in.readParcelable(getRequest.class.getClassLoader());
			String stoped = in.readString();
			List<UserInfoPublic> Guests = in.readArrayList(UserInfoPublic.class.getClassLoader());
			return new RequestBean(Msgs, request, stoped, Guests);
		}

		public RequestBean[] newArray(int size) {
			return new RequestBean[size];
		}
	};
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeList(Guests);
		dest.writeList(Msgs);
		dest.writeParcelable(request, flags);
		dest.writeString(stoped);
	}
	
}
