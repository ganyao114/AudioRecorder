package com.just.AudioRecorder.ui.adapter;

import java.util.List;

import com.just.AudioRecorder.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TextFixAdapter extends BaseAdapter {
	
	private List<String> list;
	private Context mContext;
	private LayoutInflater mInflater;
	public TextFixAdapter(Context context,List<String> list) {
		// TODO Auto-generated constructor stub
		this.list = list;
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
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
		ViewHolder holder = null;
		if (convertView == null ) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.auotofixitem, null);
			holder.textView = (TextView) convertView.findViewById(R.id.textView1);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textView.setText(list.get(position));
		return convertView;
	}

	class ViewHolder
	{
		TextView textView;
	}
}
