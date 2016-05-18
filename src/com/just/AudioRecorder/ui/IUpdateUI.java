package com.just.AudioRecorder.ui;

import java.util.List;




import com.just.AudioRecorder.Dao.Bean.Message;
import com.just.AudioRecorder.Dao.Bean.UserInfoPublic;
import com.just.AudioRecorder.Dao.Bean.getRequest;

public interface IUpdateUI {
	public void RefreshGuestList(List<UserInfoPublic> list);
	public void RefreshMsgs(List<Message> list);
	public void OnReceiveRequest(getRequest request);
	public void OnStopRecorder();
}
