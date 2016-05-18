package com.just.AudioRecorder.Service.impl.Thread;

import com.just.AudioRecorder.Dao.Bean.CreateRoomBean;
import com.just.AudioRecorder.Dao.Bean.HttpTheadConfigBean;
import com.just.AudioRecorder.Service.impl.Thread.Templet.HttpThreadTemplet;
import com.just.AudioRecorder.utils.ExceptionContent;
import com.just.AudioRecorder.utils.StaticDataPackage;

import android.os.Handler;
import android.os.Message;

public class CreateRoomThread extends HttpThreadTemplet{
	
	private CreateRoomBean createRoomBean;
	
	public CreateRoomThread(Handler handler,CreateRoomBean createRoomBean) {
		// TODO Auto-generated constructor stub
		super(handler);
		this.createRoomBean = createRoomBean;
	}

	@Override
	protected void OnRun() throws Exception {
		// TODO Auto-generated method stub
		httpService.createRoom(StaticDataPackage.UserName,createRoomBean.RoomName,createRoomBean.users);
		Message msg = new Message();
		msg.what = 1;
		handler.sendMessage(msg);
	}
	@Override
	protected HttpTheadConfigBean SetConfig() {
		// TODO Auto-generated method stub
		HttpTheadConfigBean configBean = new HttpTheadConfigBean
				(false, 0, ExceptionContent.MSG_LINK_TIMEOUT, ExceptionContent.MSG_LOGIN_TIMEOUT, ExceptionContent.MEG_CREATE_ERROR);
		return configBean;
	}

}
