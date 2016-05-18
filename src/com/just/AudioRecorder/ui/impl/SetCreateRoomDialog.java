package com.just.AudioRecorder.ui.impl;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.gy.materialdesign.widgets.ProgressDialog;
import com.gy.materialedittext.MaterialEditText;
import com.gy.widget.meteriadialog.MaterialDialog;
import com.just.AudioRecorder.R;
import com.just.AudioRecorder.Dao.Bean.CreateRoomBean;
import com.just.AudioRecorder.Dao.Bean.UserInfoPublic;
import com.just.AudioRecorder.Service.IGetUserListCallBack;
import com.just.AudioRecorder.Service.impl.Thread.CreateRoomThread;
import com.just.AudioRecorder.ui.ICreateRoomCallBack;
import com.just.AudioRecorder.ui.IOnSelectListener;
import com.just.AudioRecorder.ui.adapter.SelcetUsersDialogAdapter;
import com.just.AudioRecorder.utils.StaticDataPackage;

public class SetCreateRoomDialog  implements IOnSelectListener,IGetUserListCallBack{
	
	private ListView listView;
	private MaterialEditText materialEditText;
	private Context mContext;
	private MaterialDialog materialDialog;
	private List<UserInfoPublic> selectedUsers;
	private Handler handler;
	private ProgressDialog progressDialog;
	private ICreateRoomCallBack callBack;
	
	public SetCreateRoomDialog(Context context,ICreateRoomCallBack callBack) {
		// TODO Auto-generated constructor stub
		mContext = context;
		handler = new MyHandler(this);
		this.callBack = callBack;
		selectedUsers = new ArrayList<UserInfoPublic>();
		getUserlist();
	}
	
	private void getUserlist()
	{
		new GetUsersListManager(mContext, this);
	}
	
	private void Initview(List<UserInfoPublic> list)
	{	
		View view = View.inflate(mContext, R.layout.createroom_layout, null);
		listView = (ListView) view.findViewById(R.id.userlist);
		listView.setAdapter(new SelcetUsersDialogAdapter(list,mContext,this));
		materialEditText = (MaterialEditText) view.findViewById(R.id.roomname);
		materialDialog = new MaterialDialog(mContext);
		materialDialog.setTitle("创建房间");
		materialDialog.setContentView(view);
		materialDialog.setNegativeButton("取消", new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				materialDialog.dismiss();
			}
		});
		materialDialog.setPositiveButton("创建房间", new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StaticDataPackage.RoomName = materialEditText.getText().toString();
				materialDialog.dismiss();
				doCreateRoom();
			}
		});
		materialDialog.show();
	}

	private void doCreateRoom() {
		// TODO Auto-generated method stub
		progressDialog = new ProgressDialog(mContext, "正在创建房间...");
		progressDialog.setCancelable(false);
		progressDialog.show();
		new Thread(new CreateRoomThread(handler, new CreateRoomBean(materialEditText.getText().toString(), selectedUsers))).start();
	}
	
	private void CreateRoomUI()
	{
		callBack.OnCreateRoom();
	}

	@Override
	public List<UserInfoPublic> ReturnSelected() {
		// TODO Auto-generated method stub
		return this.selectedUsers;
	}

	@Override
	public void setAdapter(List<UserInfoPublic> list) {
		// TODO Auto-generated method stub
		Initview(list);
	}
	
	private class MyHandler extends Handler {
		private final WeakReference<SetCreateRoomDialog> context;

		public MyHandler(SetCreateRoomDialog context) {
			this.context = new WeakReference<SetCreateRoomDialog>(context);
		}

		@Override
		public void handleMessage(Message msg) {
			if (((SetCreateRoomDialog) context.get()).progressDialog != null)
			((SetCreateRoomDialog) context.get()).progressDialog.dismiss();
			String errmsg = (String) msg.getData().getSerializable("ErroMsg");
			if (msg.what == 0) {
				((SetCreateRoomDialog) context.get()).ShowTips(errmsg);
			} else if (msg.what == 1) {
				((SetCreateRoomDialog) context.get()).CreateRoomUI();
			}

		}
	}

	private void ShowTips(String errmsg) {
		// TODO Auto-generated method stub
		Toast.makeText(mContext, errmsg, Toast.LENGTH_LONG).show();
	}
}
