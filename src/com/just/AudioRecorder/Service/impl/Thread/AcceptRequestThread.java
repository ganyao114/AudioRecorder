package com.just.AudioRecorder.Service.impl.Thread;

import com.just.AudioRecorder.Dao.Bean.AcceptReturnBean;
import com.just.AudioRecorder.Dao.Bean.HttpTheadConfigBean;
import com.just.AudioRecorder.Service.impl.Thread.Templet.HttpThreadTemplet;
import com.just.AudioRecorder.utils.ExceptionContent;
import com.just.AudioRecorder.utils.StaticDataPackage;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class AcceptRequestThread extends HttpThreadTemplet{
	

	private String RoomName;
	
	public AcceptRequestThread(Handler handler,String RoomName) {
		// TODO Auto-generated constructor stub
		super(handler);
		this.RoomName = RoomName;
	}
	
	@Override
	protected void OnRun() throws Exception {
		// TODO Auto-generated method stub
		AcceptReturnBean acceptReturnBean =	httpService.AcceptRequest(StaticDataPackage.UserName, RoomName);
		Message msg = new Message();
		msg.what = 1;
		Bundle bundle = new Bundle();
		bundle.putParcelable("AcceptReturnBean",acceptReturnBean);
		msg.setData(bundle);
		handler.sendMessage(msg);
	}
	@Override
	protected HttpTheadConfigBean SetConfig() {
		// TODO Auto-generated method stub
		HttpTheadConfigBean configBean = new HttpTheadConfigBean
				(false, 0,ExceptionContent.MSG_LINK_TIMEOUT, ExceptionContent.MSG_LINK_TIMEOUT, ExceptionContent.UNKOWN_ERROR);
		return configBean;
	}

}
