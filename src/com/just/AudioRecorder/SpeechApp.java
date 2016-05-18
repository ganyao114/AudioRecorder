package com.just.AudioRecorder;

import android.app.Application;

import com.iflytek.cloud.SpeechUtility;
import com.just.AudioRecorder.utils.AppConfig;

public class SpeechApp extends Application {

	@Override
	public void onCreate() {
		
		SpeechUtility.createUtility(SpeechApp.this, "appid=" + AppConfig.Appid);
		super.onCreate();
	}
}
