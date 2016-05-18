package com.just.AudioRecorder.ui.impl;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.gy.materialedittext.MaterialEditText;
import com.gy.widget.Image.CircleImageView;
import com.gy.widget.floatactionmenu.FloatingActionButton;
import com.gy.widget.meteriadialog.MaterialDialog;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.just.AudioRecorder.R;
import com.just.AudioRecorder.Dao.Bean.AcceptReturnBean;
import com.just.AudioRecorder.Dao.Bean.Message;
import com.just.AudioRecorder.Dao.Bean.Room;
import com.just.AudioRecorder.Dao.Bean.UserInfoPublic;
import com.just.AudioRecorder.Dao.Bean.getRequest;
import com.just.AudioRecorder.Dao.impl.TextFixDao;
import com.just.AudioRecorder.Service.IAudioToTxtService;
import com.just.AudioRecorder.Service.IGetRequestService;
import com.just.AudioRecorder.Service.IOnLoginedCallBack;
import com.just.AudioRecorder.Service.IOnRegistedCallBack;
import com.just.AudioRecorder.Service.impl.AudioToTxtService;
import com.just.AudioRecorder.Service.impl.AudioToTxtService.MyBinder;
import com.just.AudioRecorder.Service.impl.GetRequestService;
import com.just.AudioRecorder.Service.impl.GetRequestService.MyBinder2;
import com.just.AudioRecorder.Service.impl.Thread.AcceptRequestThread;
import com.just.AudioRecorder.Service.impl.Thread.SendMessageThread;
import com.just.AudioRecorder.Service.impl.Thread.StopRecorderThread;
import com.just.AudioRecorder.ui.ICreateRoomCallBack;
import com.just.AudioRecorder.ui.IUpdateUI;
import com.just.AudioRecorder.ui.MyItemClickListener;
import com.just.AudioRecorder.ui.MyItemLongClickListener;
import com.just.AudioRecorder.ui.NavigationDrawerCallbacks;
import com.just.AudioRecorder.ui.adapter.MessageListAdapter;
import com.just.AudioRecorder.ui.adapter.TextFixAdapter;
import com.just.AudioRecorder.utils.StaticDataPackage;

public class MainActivity extends ActionBarActivity implements
		NavigationDrawerCallbacks, MyItemClickListener, OnTouchListener,
		MyItemLongClickListener, RecognizerDialogListener, ServiceConnection,
		IOnLoginedCallBack, IOnRegistedCallBack, IUpdateUI
		,ICreateRoomCallBack,TextWatcher {

	private List<Message> msglist;
	private List<UserInfoPublic> guestlist;
	private Toolbar mToolbar;
	private NavigationDrawerFragment mNavigationDrawerFragment;
	private IAudioToTxtService iaudio;
	private IGetRequestService iRequestService;
	private CircleImageView startRec;
	private MaterialEditText edtext;
	private boolean isInit = false;
	private ListView listView;
	private BaseAdapter adapter;
	private Handler handler,handler2,handler3;
	private ServiceConnection getRequestConn;
	private FloatingActionButton createroombutton,refreshbutton;
	private PopupWindow popupwindows;
	private LayoutInflater mInflater;
	private View contentView;
	private ListView fixtextlist;
	private BaseAdapter autofixadapter;
	private List<String> FixTextItemList;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handler = new MyHandler(this);
		handler2 = new MyHandler2(this);
		handler3 = new MyHandler3(this);
		msglist = new ArrayList<Message>();
		guestlist = new ArrayList<UserInfoPublic>();
		StaticDataPackage.faceDrawable = getResources().getDrawable(
				R.drawable.face);
		setContentView(R.layout.activity_main_topdrawer);
		mInflater = LayoutInflater.from(this);
		// getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
		// |
		// WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.fragment_drawer);
		mNavigationDrawerFragment.setup(R.id.fragment_drawer,
				(DrawerLayout) findViewById(R.id.drawer), mToolbar);
		edtext = (MaterialEditText) findViewById(R.id.content);
		createroombutton = (FloatingActionButton) findViewById(R.id.action_b);
		getRequestConn = new ServiceConnection() {
			
			@Override
			public void onServiceDisconnected(ComponentName name) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				// TODO Auto-generated method stub
				MyBinder2 myBinder2 = (MyBinder2) service;
				iRequestService = myBinder2.getService();
			}
		};
		createroombutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!StaticDataPackage.UserName.equals("")) 
					new SetCreateRoomDialog(MainActivity.this, MainActivity.this);
				else
					showTip("请先登录");
					
			}
		});
		linkService();
		linkService2();
	
		new SetLoginDialog(this, this);
	}

	

	@SuppressLint("ClickableViewAccessibility")
	private void setroomlisterner() {
		startRec = (CircleImageView) findViewById(R.id.startrec);
		startRec.setOnTouchListener(this);
		edtext.addTextChangedListener(this);
	}
	
	
	
	private void startRequestService()
	{
		iRequestService.startRequest(this, this);
	}
	
	private void linkService() {
		Intent intent = new Intent(this, AudioToTxtService.class);
		bindService(intent, this, BIND_AUTO_CREATE);
	}

	private void linkService2() {
		Intent intent = new Intent(this, GetRequestService.class);
		bindService(intent, getRequestConn, BIND_AUTO_CREATE);
	}
	
	private void setlistener() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLongItemClick(View view, int postion) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(View view, int postion) {
		// TODO Auto-generated method stub

	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		// actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onPrepareOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.menu_login) {
			OnClickLogin();
			return true;
		} else if (id == R.id.menu_regist) {
			OnClickRegist();
			return true;
		} else if(id == R.id.menu_stop){
			new Thread(new StopRecorderThread(handler3)).start();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void SwitchInChatRoom(Room room) {
		findViewById(R.id.multiple_actions).setVisibility(View.GONE);
		findViewById(R.id.ChatButtomView).setVisibility(View.VISIBLE);
		findViewById(R.id.tip).setVisibility(View.GONE);
		setroomlisterner();
		setTitle(room.getName());
		listView = (ListView) findViewById(R.id.listView1);
		adapter = new MessageListAdapter(this, msglist);
		listView.setAdapter(adapter);
		findViewById(R.id.send).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Message msg = new Message(StaticDataPackage.UserName,edtext.getText().toString() , null, null);
				senMessage(msg);
				edtext.setText("");
			}
		});
	}

	private void senMessage(Message msg) {
		// TODO Auto-generated method stub
		new Thread(new SendMessageThread(handler,msg)).start();;
	}

	@Override
	public void onError(SpeechError error) {
		// TODO Auto-generated method stub
		Toast.makeText(this, error.getPlainDescription(true),
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onResult(RecognizerResult result, boolean arg1) {
		// TODO Auto-generated method stub
		iaudio.PrintResult(result, edtext);
	}

	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		// TODO Auto-generated method stub
		MyBinder myBinder = (MyBinder) service;
		iaudio = myBinder.getService();
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if (!isInit) {
			iaudio.EngineInit(this);
			isInit = true;
		}
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			iaudio.StartListen(MainActivity.this);
			break;
		case MotionEvent.ACTION_UP:
			iaudio.StopListen();
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public void OnLogined() {
		// TODO Auto-generated method stub
		findViewById(R.id.multiple_actions).setVisibility(View.VISIBLE);
		iRequestService.startRequest(this, this);
	}

	@Override
	public void OnClickRegist() {
		// TODO Auto-generated method stub
		new SetRegistDialog(this, this);
	}

	@Override
	public void OnRegisted() {
		// TODO Auto-generated method stub
		new SetLoginDialog(this, this);
	}

	@Override
	public void OnClickLogin() {
		// TODO Auto-generated method stub
		new SetLoginDialog(this, this);
	}

	@Override
	public void RefreshGuestList(List<UserInfoPublic> list) {
		// TODO Auto-generated method stub
		guestlist = list;
		List<NavigationItem> items = new ArrayList<NavigationItem>();
		for (UserInfoPublic guest : list) {
			items.add(new NavigationItem(guest.getLoginName(),
					getResources().getDrawable(R.drawable.ic_launcher)));
		}
		mNavigationDrawerFragment.RefreshList(items);
	}

	@Override
	public void RefreshMsgs(List<Message> list) {
		// TODO Auto-generated method stub
		if (listView == null)
			return;
		msglist.addAll(list);
		adapter.notifyDataSetChanged();
		listView.setSelection(adapter.getCount() - 1);
		// notifyrefresh
	}
	
	private void dorequest(final getRequest request) {
		// TODO Auto-generated method stub
		
	}
	

	@SuppressWarnings("unused")
	private static class MyHandler extends Handler {
		private final WeakReference<MainActivity> mcontext;

		public MyHandler(MainActivity context) {
			// TODO Auto-generated constructor stub
			this.mcontext = new WeakReference<MainActivity>(context);
		}

		@Override
		public void handleMessage(android.os.Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			int Flag = msg.what;
			switch (Flag) {
			case 0:
				String errmsg = (String) msg.getData().getSerializable(
						"ErroMsg");
				((MainActivity) mcontext.get()).showTip(errmsg);
				break;
			case 1:
				break;
			default:
				break;
			}

		}

	}
	
	private static class MyHandler2 extends Handler{
		private final WeakReference<MainActivity> mcontext;
		public MyHandler2(MainActivity context) {
			// TODO Auto-generated constructor stub
			this.mcontext = new WeakReference<MainActivity>(context);
		}
		@Override
		public void handleMessage(android.os.Message msg) {
			// TODO Auto-generated method stub
			int Flag = msg.what;
			switch (Flag) {
			case 0:
				String errmsg = (String) msg.getData().getSerializable(
						"ErroMsg");
				((MainActivity) mcontext.get()).showTip(errmsg);
				break;
			case 1:
				//创建房间UI
				AcceptReturnBean acceptReturnBean = (AcceptReturnBean) msg.getData().getParcelable("AcceptReturnBean");
				((MainActivity) mcontext.get()).CreateRoomUI(acceptReturnBean);
				break;  
			default:
				break;
			}
		}
		
	}
	
	private static class MyHandler3 extends Handler{
		private final WeakReference<MainActivity> mcontext;
		public MyHandler3(MainActivity context) {
			// TODO Auto-generated constructor stub
			this.mcontext = new WeakReference<MainActivity>(context);
		}
		@Override
		public void handleMessage(android.os.Message msg) {
			// TODO Auto-generated method stub
			int Flag = msg.what;
			switch (Flag) {
			case 0:
				String errmsg = (String) msg.getData().getSerializable(
						"ErroMsg");
				((MainActivity) mcontext.get()).showTip(errmsg);
				break;
			case 1:
				((MainActivity) mcontext.get()).OnStopRecorder();
				break;  
			default:
				break;
			}
		}
		
	}
	
	private void doacceptRequest(String RoomName) {
		// TODO Auto-generated method stub
		new Thread(new AcceptRequestThread(handler2, RoomName)).start(); 
	}

	

	private void CreateRoomUI(AcceptReturnBean acceptReturnBean) {
		// TODO Auto-generated method stub
		SwitchInChatRoom(new Room(acceptReturnBean.RoomName));
		RefreshGuestList(acceptReturnBean.Guests);
		RefreshMsgs(acceptReturnBean.MsgList);
	}

	public void showTip(String errmsg) {
		// TODO Auto-generated method stub
		Toast.makeText(this, errmsg, Toast.LENGTH_LONG).show();
	}

	@Override
	public void OnReceiveRequest(final getRequest request) {
		// TODO Auto-generated method stub
		final MaterialDialog materialDialog = new MaterialDialog(this);
		materialDialog.setTitle("收到邀请");
		TextView textView = new TextView(this);
		textView.setTextColor(Color.BLACK);
		textView.setText(request.Sender+"邀请你加入"+request.RoomName+"房间");
		materialDialog.setContentView(textView);
		materialDialog.setNegativeButton("拒绝", new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				materialDialog.dismiss();
			}
		});
		materialDialog.setPositiveButton("接受", new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				materialDialog.dismiss();
				doacceptRequest(request.RoomName);
			}

		});
		materialDialog.show();
	}

	@Override
	public void OnStopRecorder() {
		// TODO Auto-generated method stub
		
		guestlist.clear();
		adapter.notifyDataSetChanged();
		//iRequestService.stopRequest();
		setTitle("语音记录器");
		findViewById(R.id.ChatButtomView).setVisibility(View.GONE);
		findViewById(R.id.multiple_actions).setVisibility(View.VISIBLE);
		final MaterialDialog materialDialog = new MaterialDialog(this);
		materialDialog.setTitle("录音已结束");
		String str = "";
		for(int i=0;i<msglist.size();i++)
		{
			str = str + msglist.get(i).Content;
		}
		msglist.clear();
		TextView textView = new TextView(this);
		textView.setTextColor(Color.BLACK);
		textView.setText(str);//拼凑字符串
		materialDialog.setContentView(textView);
		materialDialog.setNegativeButton("否", new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				materialDialog.dismiss();
			}
		});
		materialDialog.setPositiveButton("是", new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				materialDialog.dismiss();
				iRequestService.stopRequest();
			}

		});
		materialDialog.show();
	}

	@Override
	public void OnCreateRoom() {
		// TODO Auto-generated method stub
		SwitchInChatRoom(new Room(StaticDataPackage.RoomName));
	}
	
	private void RoomOwnerStopRC()
	{
		new Thread(new StopRecorderThread(null)).start();
	}
	
	private void showpopwindows()
	{
		if (popupwindows == null) {
			contentView = mInflater.inflate(R.layout.autotextfix_layout, null);
			fixtextlist = (ListView) contentView.findViewById(R.id.listView1);
 			autofixadapter = new TextFixAdapter(this, FixTextItemList);
			fixtextlist.setAdapter(autofixadapter);
			fixtextlist.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					edtext.setText(FixTextItemList.get(position));
					popupwindows.dismiss();
				}
			});
			popupwindows = new PopupWindow(contentView, 320, 320);
		}
		int[] location = new int[2];
		edtext.getLocationInWindow(location);
		popupwindows.showAtLocation(edtext, Gravity.END | Gravity.TOP, location[0], location[1]);
		adapter.notifyDataSetChanged();
	}



	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		processTextfix(s.toString());
	}



	private void processTextfix(String str) {
		// TODO Auto-generated method stub
		if (popupwindows!=null&&popupwindows.isShowing()) {
			popupwindows.dismiss();
		}	
			FixTextItemList = new TextFixDao().StrFix(str);
			if (FixTextItemList!=null&&FixTextItemList.size()>0) {
				showpopwindows();
			}
			
	}
	
}