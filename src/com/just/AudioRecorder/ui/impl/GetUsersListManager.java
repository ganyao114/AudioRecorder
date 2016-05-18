package com.just.AudioRecorder.ui.impl;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.gy.materialdesign.widgets.ProgressDialog;
import com.gy.widget.meteriadialog.MaterialDialog;
import com.just.AudioRecorder.Dao.Bean.UserInfoPublic;
import com.just.AudioRecorder.Service.IGetUserListCallBack;
import com.just.AudioRecorder.Service.impl.Thread.GetUsersOlineListThread;
import com.just.AudioRecorder.ui.IOnSelectListener;
import com.just.AudioRecorder.ui.adapter.*;
import com.just.AudioRecorder.utils.StaticDataPackage;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class GetUsersListManager{
	
	private GetUsersOlineListThread getUserlistRunnable;
	private Handler handler;
	private Context mContext;
	private ListView listView;
	private Runnable runnable;
	private Thread thread;
	private ProgressDialog progressDialog;
	private IGetUserListCallBack callBack;
	
	public GetUsersListManager(Context context,IGetUserListCallBack callBack) {
		// TODO Auto-generated constructor stub
		this.callBack = callBack;
		mContext = context;
		handler = new MyHandler(this);
		getUserlistRunnable = new GetUsersOlineListThread(handler);
		runnable = new GetUsersOlineListThread(handler);
		thread = new Thread(runnable);
		getUserlist();
	}
	
	private void setAdapter(List<UserInfoPublic> arrayList)
	{	
		callBack.setAdapter(arrayList);
	}
	
	
	
	private void getUserlist()
	{
		progressDialog = new ProgressDialog(mContext, "正在获取在线用户...");
		progressDialog.setCancelable(false);
		progressDialog.show();
		thread.start();
	}
	
	private void ShowTips(String tips)
	{
		Toast.makeText(mContext, tips, Toast.LENGTH_LONG).show();
	}
	
	private class MyHandler extends Handler {
		private final WeakReference<GetUsersListManager> context;

		public MyHandler(GetUsersListManager context) {
			this.context = new WeakReference<GetUsersListManager>(context);
		}

		@Override
		public void handleMessage(Message msg) {
			if (((GetUsersListManager) context.get()).progressDialog != null)
			((GetUsersListManager) context.get()).progressDialog.dismiss();
			String errmsg = (String) msg.getData().getSerializable("ErroMsg");
			if (msg.what == 0) {
				((GetUsersListManager) context.get()).ShowTips(errmsg);
			} else if (msg.what == 1) {
				 List<UserInfoPublic> list = msg.getData().getParcelableArrayList("UserList");
				 
				((GetUsersListManager) context.get()).setAdapter(list);
			}

		}
	}
	
}
