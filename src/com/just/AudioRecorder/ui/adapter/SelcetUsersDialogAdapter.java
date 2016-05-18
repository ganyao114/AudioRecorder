package com.just.AudioRecorder.ui.adapter;

import java.util.List;

import com.gy.materialdesign.views.CheckBox;
import com.gy.materialdesign.views.CheckBox.OnCheckListener;
import com.just.AudioRecorder.R;
import com.just.AudioRecorder.Dao.Bean.UserInfoPublic;
import com.just.AudioRecorder.ui.IOnSelectListener;
import com.just.AudioRecorder.utils.StaticDataPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SelcetUsersDialogAdapter extends BaseAdapter {

	
	private List<UserInfoPublic> list;
	private Context mContext;
	private LayoutInflater inflater;
	private IOnSelectListener selectListener;
	private boolean state[];
	
	public SelcetUsersDialogAdapter(List<UserInfoPublic> list,Context context
			,IOnSelectListener selectListener) {
		// TODO Auto-generated constructor stub
		this.list = list;
		this.mContext = context;
		this.selectListener = selectListener;
		inflater = LayoutInflater.from(context);
		state = new boolean[list.size()];
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
		
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.selectusersdialog_layout, null);
				holder.text = (TextView) convertView.findViewById(R.id.text);
				holder.icon = (ImageView) convertView.findViewById(R.id.icon);
				holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox1);
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			
			holder.text.setText(list.get(position).getLoginName());
			holder.icon.setImageDrawable(StaticDataPackage.faceDrawable);
			if (state[position])
				holder.checkBox.setChecked(true);
			else 
				holder.checkBox.setChecked(false);
			setcheckboxlistener(holder, position);
		return convertView;
	}

	private void setcheckboxlistener(final ViewHolder holder, final int position) {
		holder.checkBox.setOncheckListener(new OnCheckListener() {

			@Override
			public void onCheck(boolean check) {
				// TODO Auto-generated method stub
				if (check) {
					addlist(selectListener.ReturnSelected(), list.get(position));
					state[position] = true;
				} else {
					deletelist(selectListener.ReturnSelected(),
							list.get(position));
					state[position] = false;
				}
			}
		});
	}

	private void addlist(List<UserInfoPublic> list,UserInfoPublic userInfoPublic) {
		int x = 0;
		for (UserInfoPublic i: list) {
			if (i.equals(userInfoPublic)) {
				x++;
			}
		}
		if (x == 0) {
			list.add(userInfoPublic);
		}
	}
	
	private void deletelist(List<UserInfoPublic> selectedUsers,UserInfoPublic userInfoPublic) {
		selectedUsers.remove(userInfoPublic);
	}
	
	private class ViewHolder {
		TextView text;
		ImageView icon;
		CheckBox checkBox;
	}
	
}
