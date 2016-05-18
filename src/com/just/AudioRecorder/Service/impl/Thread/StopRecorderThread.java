package com.just.AudioRecorder.Service.impl.Thread;

import android.os.Handler;
import android.os.Message;

import com.just.AudioRecorder.Dao.Bean.HttpTheadConfigBean;
import com.just.AudioRecorder.Service.impl.Thread.Templet.HttpThreadTemplet;
import com.just.AudioRecorder.utils.ExceptionContent;
import com.just.AudioRecorder.utils.StaticDataPackage;

public class StopRecorderThread extends HttpThreadTemplet {

	public StopRecorderThread(Handler handler) {
		super(handler);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void OnRun() throws Exception {
		// TODO Auto-generated method stub
		httpService.StopRecorder(StaticDataPackage.UserName, StaticDataPackage.RoomName);
		Message msg = new Message();
		msg.what = 1;
		handler.sendMessage(msg);
	}

	@Override
	protected HttpTheadConfigBean SetConfig() {
		// TODO Auto-generated method stub
		HttpTheadConfigBean configBean = new HttpTheadConfigBean
				(false, 0, ExceptionContent.MSG_LINK_TIMEOUT, ExceptionContent.MSG_LINK_TIMEOUT, ExceptionContent.MSG_STOP_FAILED);
		return configBean;
	}

	

}
