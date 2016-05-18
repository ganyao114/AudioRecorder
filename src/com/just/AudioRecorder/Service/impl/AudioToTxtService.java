package com.just.AudioRecorder.Service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.gy.materialedittext.MaterialEditText;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.just.AudioRecorder.Service.IAudioToTxtService;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

public class AudioToTxtService extends Service implements IAudioToTxtService,InitListener{
	private IBinder mBinder;
	private SpeechRecognizer mIat;
	// 语音听写UI
	private RecognizerDialog mIatDialog;
	// 用HashMap存储听写结果
	private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mBinder;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		mBinder = new MyBinder();
		super.onCreate();
	}

	@Override
	public void StartListen(RecognizerDialogListener listener) {
		// TODO Auto-generated method stub
		mIatDialog.setListener(listener);
		mIatDialog.show();
	}
	
	@Override
	public void StopListen() {
		// TODO Auto-generated method stub
		mIat.stopListening();
	}

	public class MyBinder extends Binder
	{
		public IAudioToTxtService getService()
		{
			return AudioToTxtService.this;
		}
	}
	
	private void showTip(String str)
	{
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
	
	
	private void setParam() {
		// 清空参数
		mIat.setParameter(SpeechConstant.PARAMS, null);

		// 设置听写引擎
		mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
		// 设置返回结果格式
		mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");

		// 设置语言
		mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
		// 设置语言区域
		mIat.setParameter(SpeechConstant.ACCENT, "mandarin");

		// 设置语音前端点
		mIat.setParameter(SpeechConstant.VAD_BOS, "4000");
		// 设置语音后端点
		mIat.setParameter(SpeechConstant.VAD_EOS, "1000");
		// 设置标点符号
		mIat.setParameter(SpeechConstant.ASR_PTT, "1");
		// 设置音频保存路径
		mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH,
				Environment.getExternalStorageDirectory()
						+ "/iflytek/wavaudio.pcm");
		// 设置听写结果是否结果动态修正，为“1”则在听写过程中动态递增地返回结果，否则只在听写结束之后返回最终结果
		// 注：该参数暂时只对在线听写有效
		mIat.setParameter(SpeechConstant.ASR_DWA, "0");
	}
	
	public void PrintResult(RecognizerResult results,MaterialEditText mResultText)
	{
		String text = JsonParser.parseIatResult(results.getResultString());

		String sn = null;
		// 读取json结果中的sn字段
		try {
			JSONObject resultJson = new JSONObject(results.getResultString());
			sn = resultJson.optString("sn");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		mIatResults.put(sn, text);

		StringBuffer resultBuffer = new StringBuffer();
		for (String key : mIatResults.keySet()) {
			resultBuffer.append(mIatResults.get(key));
		}

		mResultText.setText(resultBuffer.toString());
		mResultText.setSelection(mResultText.length());
	}
	
	@Override
	public void onInit(int code) {
		// TODO Auto-generated method stub
		if (code != ErrorCode.SUCCESS) {
			showTip("初始化失败，错误码：" + code);
		}else {
			showTip("语音引擎初始化成功");
		}
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mIat.cancel();
		mIat.destroy();
	}
	@Override
	public void EngineInit(Context context) {
		// TODO Auto-generated method stub
		mIat = SpeechRecognizer.createRecognizer(context, this);
		mIatDialog = new RecognizerDialog(context, this);
		setParam();
	}
	
}
