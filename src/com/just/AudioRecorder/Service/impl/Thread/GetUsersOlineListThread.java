package com.just.AudioRecorder.Service.impl.Thread;

import java.util.ArrayList;

import com.just.AudioRecorder.Dao.Bean.HttpTheadConfigBean;
import com.just.AudioRecorder.Dao.Bean.UserInfoPublic;
import com.just.AudioRecorder.Service.impl.Thread.Templet.HttpThreadTemplet;
import com.just.AudioRecorder.utils.ExceptionContent;
import com.just.AudioRecorder.utils.StaticDataPackage;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;

public class GetUsersOlineListThread extends HttpThreadTemplet {
	
	public ArrayList<UserInfoPublic> usersOline;
	
	public GetUsersOlineListThread(Handler handler) {
		super(handler);
		// TODO Auto-generated constructor stub
	}

	

	@Override
	protected void OnRun() throws Exception {
		// TODO Auto-generated method stub
		usersOline = (ArrayList<UserInfoPublic>) httpService.getUsers(StaticDataPackage.UserName);
		Message msg = new Message();
		msg.what = 1;
		Bundle bundle = new Bundle();
		bundle.putParcelableArrayList("UserList", (ArrayList<? extends Parcelable>) usersOline);
		msg.setData(bundle);
		handler.sendMessage(msg);
	}

	@Override
	protected HttpTheadConfigBean SetConfig() {
		// TODO Auto-generated method stub
		HttpTheadConfigBean configBean = new HttpTheadConfigBean
				(false, 0, ExceptionContent.MSG_REGIST_CONNECT_TIMEOUT, ExceptionContent.MSG_REGIST_CONNECT_TIMEOUT, "Î´Öª´íÎó");
		return configBean;
	}
	
}
