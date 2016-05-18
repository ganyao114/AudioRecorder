package com.just.AudioRecorder.Service.impl.Thread;

import com.just.AudioRecorder.Dao.Bean.HttpTheadConfigBean;
import com.just.AudioRecorder.Dao.Bean.Message;
import com.just.AudioRecorder.Service.impl.Thread.Templet.HttpThreadTemplet;
import com.just.AudioRecorder.utils.ExceptionContent;
import com.just.AudioRecorder.utils.StaticDataPackage;

import android.os.Handler;

public class SendMessageThread extends HttpThreadTemplet{
		
	private Message msg;
	
	public SendMessageThread(Handler handler,Message msg) {
		// TODO Auto-generated constructor stub
		super(handler);
		this.msg = msg;
	}

	@Override
	protected void OnRun() throws Exception {
		// TODO Auto-generated method stub
		httpService.sendmsg(StaticDataPackage.UserName, msg);
		android.os.Message handlerMessage = new android.os.Message();
		handlerMessage.what = 1;
		handler.sendMessage(handlerMessage);
	}

	@Override
	protected HttpTheadConfigBean SetConfig() {
		// TODO Auto-generated method stub
		HttpTheadConfigBean configBean = new HttpTheadConfigBean
				(false, 0, ExceptionContent.MSG_REGIST_CONNECT_TIMEOUT, ExceptionContent.MSG_LINK_ERROR,ExceptionContent.UNKOWN_ERROR);
		return configBean;
	}
	
	

}
