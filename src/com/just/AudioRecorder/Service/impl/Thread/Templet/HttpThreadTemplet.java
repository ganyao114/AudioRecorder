package com.just.AudioRecorder.Service.impl.Thread.Templet;
/**
 * ���߳�ģ��,���η�װRunnable�ӿڣ���װ�쳣����
 */
import java.net.SocketTimeoutException;

import org.apache.http.conn.ConnectTimeoutException;

import com.just.AudioRecorder.Dao.IHttpService;
import com.just.AudioRecorder.Dao.Bean.HttpTheadConfigBean;
import com.just.AudioRecorder.Dao.impl.HttpService;
import com.just.AudioRecorder.Service.impl.ServiceRulesException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public abstract class HttpThreadTemplet implements Runnable {
	
	protected Handler handler;
	protected IHttpService httpService;
	private boolean isLoop = false;
	private int tickTime = 0;
	private HttpTheadConfigBean configBean;
	
	public HttpThreadTemplet(Handler handler) {
		// TODO Auto-generated constructor stub
		this.handler = handler;
		httpService = HttpService.getInstance();
		configBean = SetConfig();
		isLoop = configBean.isLoop;
		tickTime = configBean.tickTime;
	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (isLoop) {
			while(isLoop)
			{	
				doRun();
				try {
						Thread.currentThread().sleep(tickTime);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}else {
			doRun();
		}
		
	}

	private void doRun()
	{
		try {
			OnRun();
		} catch (ServiceRulesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Message msg = new Message();
			Bundle data = new Bundle();
			data.putSerializable("ErroMsg", e.getMessage());
			msg.setData(data);
			handler.sendMessage(msg);
		} catch (ConnectTimeoutException e) {
			e.printStackTrace();
			Message msg = new Message();
			Bundle data = new Bundle();
			data.putSerializable("ErroMsg",
					configBean.ErrorMsgLevel1);
			msg.setData(data);
			handler.sendMessage(msg);
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			Message msg = new Message();
			Bundle data = new Bundle();
			data.putSerializable("ErroMsg",
					configBean.ErrorMsgLevel2);
			msg.setData(data);
			handler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
			Message msg = new Message();
			Bundle data = new Bundle();
			data.putSerializable("ErroMsg", configBean.ErrorMsgLevel3);
			msg.setData(data);
			handler.sendMessage(msg);
		}
	}
	
	public synchronized void StopThread(){
		isLoop = false;
	}
	
	protected abstract void OnRun() throws Exception;
	
	protected abstract HttpTheadConfigBean SetConfig();

}
