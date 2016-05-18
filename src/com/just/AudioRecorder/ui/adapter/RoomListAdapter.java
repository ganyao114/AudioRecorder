package com.just.AudioRecorder.ui.adapter;

import java.util.List;

import com.just.AudioRecorder.R;
import com.just.AudioRecorder.Dao.Bean.Room;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RoomListAdapter extends BaseAdapter implements OnClickListener{
	private List<Room> roomsListItemInfo;
	private Context mContext;
	public static int ctr = -1;
	public RoomListAdapter(Context context,List<Room> bookListItemInfo) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.roomsListItemInfo = bookListItemInfo;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return roomsListItemInfo.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return roomsListItemInfo.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("ViewHolder") @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LinearLayout view = (LinearLayout) View.inflate(mContext, R.layout.roomlistitem_layout, null);
		TextView textView = (TextView) view.findViewById(R.id.bookname);
		textView.setText(roomsListItemInfo.get(position).getName());
		if (position == ctr)
		{	
			view.findViewById(R.id.circleImageView1).setOnClickListener(this);
			Animation animation=AnimationUtils.loadAnimation(mContext, R.anim.item_animation);
			view.startAnimation(animation);
			view.addView(addsubview(position));
			
		}
		return view;
	}
	
	private View addsubview(int position)
	{	
		RelativeLayout view = (RelativeLayout) View.inflate(mContext, R.layout.addview_layout, null);
		return 	view;
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		ctr = -1;
		notifyDataSetChanged();
	}

}
