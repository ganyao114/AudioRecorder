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
	// ������дUI
	private RecognizerDialog mIatDialog;
	// ��HashMap�洢��д���
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
		// ��ղ���
		mIat.setParameter(SpeechConstant.PARAMS, null);

		// ������д����
		mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
		// ���÷��ؽ����ʽ
		mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");

		// ��������
		mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
		// ������������
		mIat.setParameter(SpeechConstant.ACCENT, "mandarin");

		// ��������ǰ�˵�
		mIat.setParameter(SpeechConstant.VAD_BOS, "4000");
		// ����������˵�
		mIat.setParameter(SpeechConstant.VAD_EOS, "1000");
		// ���ñ�����
		mIat.setParameter(SpeechConstant.ASR_PTT, "1");
		// ������Ƶ����·��
		mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH,
				Environment.getExternalStorageDirectory()
						+ "/iflytek/wavaudio.pcm");
		// ������д����Ƿ�����̬������Ϊ��1��������д�����ж�̬�����ط��ؽ��������ֻ����д����֮�󷵻����ս��
		// ע���ò�����ʱֻ��������д��Ч
		mIat.setParameter(SpeechConstant.ASR_DWA, "0");
	}
	
	public void PrintResult(RecognizerResult results,MaterialEditText mResultText)
	{
		String text = JsonParser.parseIatResult(results.getResultString());

		String sn = null;
		// ��ȡjson����е�sn�ֶ�
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
			showTip("��ʼ��ʧ�ܣ������룺" + code);
		}else {
			showTip("���������ʼ���ɹ�");
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
