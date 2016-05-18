package com.just.AudioRecorder.Service.impl.Thread;

import com.just.AudioRecorder.Dao.IHttpService;
import com.just.AudioRecorder.Dao.Bean.HttpTheadConfigBean;
import com.just.AudioRecorder.Dao.Bean.LoginBean;
import com.just.AudioRecorder.Service.impl.Thread.Templet.HttpThreadTemplet;
import com.just.AudioRecorder.utils.ExceptionContent;

import android.os.Handler;

public class LoginThread extends HttpThreadTemplet {

	private LoginBean loginBean;
	public LoginThread(Handler handler,LoginBean loginBean,IHttpService httpService) {
		// TODO Auto-generated constructor stub
		super(handler);
		this.loginBean = loginBean;

	}

	@Override
	protected void OnRun() throws Exception {
		// TODO Auto-generated method stub
		httpService.login(loginBean);
		handler.sendEmptyMessage(ExceptionContent.FLAG_LOGIN_SUCCESS);
	}

	@Override
	protected HttpTheadConfigBean SetConfig() {
		// TODO Auto-generated method stub
		HttpTheadConfigBean configBean = new HttpTheadConfigBean
				(false, 0, ExceptionContent.MSG_LOGIN_CONNECT_TIMEOUT, ExceptionContent.MSG_LOGIN_TIMEOUT,ExceptionContent.MEG_LOGIN_ERROR);
		return configBean;
	}

}
