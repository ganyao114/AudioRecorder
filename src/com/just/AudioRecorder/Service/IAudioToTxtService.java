package com.just.AudioRecorder.Service;

import android.content.Context;

import com.gy.materialedittext.MaterialEditText;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.ui.RecognizerDialogListener;

public interface IAudioToTxtService {
	public void StartListen(RecognizerDialogListener listener);
	public void StopListen();
	public void PrintResult(RecognizerResult results,MaterialEditText mResultText);
	public void EngineInit(Context context);
}
