package com.just.AudioRecorder.Service;

import com.just.AudioRecorder.ui.IUpdateUI;

import android.content.Context;

public interface IGetRequestService {
	public void startRequest(Context context,IUpdateUI i);
	public void stopRequest();
}
