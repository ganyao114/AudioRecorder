package com.just.AudioRecorder.ui.adapter;

import java.util.List;

import com.just.AudioRecorder.Dao.Bean.UserInfoPublic;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class UserOnlineAdapter extends BaseAdapter {
	
	private List<UserInfoPublic> list;
	private Context mContext;
	
	public UserOnlineAdapter(List<UserInfoPublic> list,Context context) {
		// TODO Auto-generated constructor stub
		this.list = list;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
