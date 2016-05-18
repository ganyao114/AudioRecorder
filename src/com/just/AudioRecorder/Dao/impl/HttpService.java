package com.just.AudioRecorder.Dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.just.AudioRecorder.Dao.IHttpService;
import com.just.AudioRecorder.Dao.Bean.AcceptReturnBean;
import com.just.AudioRecorder.Dao.Bean.LoginBean;
import com.just.AudioRecorder.Dao.Bean.Message;
import com.just.AudioRecorder.Dao.Bean.RegistBean;
import com.just.AudioRecorder.Dao.Bean.RequestBean;
import com.just.AudioRecorder.Dao.Bean.UserInfoPublic;
import com.just.AudioRecorder.Service.impl.ServiceRulesException;
import com.just.AudioRecorder.utils.AppConfig;
import com.just.AudioRecorder.utils.ExceptionContent;

public class HttpService implements IHttpService {
	
	private final static IHttpService httpService = new HttpService();
	private String ServiceUrl = AppConfig.ServiceUrl;
	private HttpService() {
		// TODO Auto-generated constructor stub
	}
	
	public static IHttpService getInstance()
	{
		return httpService;
	}
	@Override
	public boolean regist(RegistBean registBean) throws Exception {
		// TODO Auto-generated method stub
		HttpClient client = HttpClientFactory.GetHttpClient();
		String uri = ServiceUrl+"UserRegist";
		HttpPost post = new HttpPost(uri);
		NameValuePair LoginNamePair = new BasicNameValuePair("LoginName",
				registBean.LoginName);
		NameValuePair loginPasswordPair = new BasicNameValuePair(
				"LoginPassword", registBean.LoginPasswd);
		NameValuePair Mail = new BasicNameValuePair(
				"Mail", registBean.Mail);
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(LoginNamePair);
		parameters.add(loginPasswordPair);
		parameters.add(Mail);
		post.setEntity(new UrlEncodedFormEntity(parameters, HTTP.UTF_8));
		HttpResponse response = client.execute(post);

		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			throw new ServiceRulesException(ExceptionContent.MSG_REGIST_CONNECT_TIMEOUT);
		}
		String result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
	//	client.getConnectionManager().shutdown();//关闭连接，未验证
	//	HttpClientFactory.CloseHttpClient(client, 20);
		if (result.equals("success")) {
			
		} else {
			throw new ServiceRulesException(ExceptionContent.MSG_REGIST_FAILED);
		}
		return true;
	}

	@Override
	public String login(LoginBean loginBean) throws Exception{
		// TODO Auto-generated method stubh
		HttpClient client = HttpClientFactory.GetHttpClient();
		String uri = ServiceUrl+"LoginServlet";
		HttpPost post = new HttpPost(uri);
		NameValuePair LoginNamePair = new BasicNameValuePair("LoginName",
				loginBean.LoginName);
		NameValuePair loginPasswordPair = new BasicNameValuePair(
				"LoginPassword", loginBean.LoginPasswd);
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(LoginNamePair);
		parameters.add(loginPasswordPair);
		post.setEntity(new UrlEncodedFormEntity(parameters, HTTP.UTF_8));
		HttpResponse response = client.execute(post);

		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			throw new ServiceRulesException(ExceptionContent.MEG_LOGIN_ERROR);
		}
		String result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
	//	client.getConnectionManager().shutdown();//关闭连接，未验证
	//	HttpClientFactory.CloseHttpClient(client, 20);
		if (result.equals("success")) {
			
		} else {
			throw new ServiceRulesException(ExceptionContent.MSG_LOGIN_FAILED);
		}
		return result;
	}

	@Override
	public boolean sendmsg(String LoginName,Message msg) throws Exception{
		// TODO Auto-generated method stub
		HttpClient client = HttpClientFactory.GetHttpClient();
		String uri = ServiceUrl+"SendMsg";
		HttpPost post = new HttpPost(uri);
		NameValuePair LoginNamePair = new BasicNameValuePair("LoginName",
				LoginName);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Sender", msg.Sender);
		jsonObject.put("Receiver", msg.Receiver);
		jsonObject.put("Content", msg.Content);
		jsonObject.put("Date",msg.Data);
		NameValuePair MsgPair = new BasicNameValuePair(
				"Message",jsonObject.toString());
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(LoginNamePair);
		parameters.add(MsgPair);
		post.setEntity(new UrlEncodedFormEntity(parameters, HTTP.UTF_8));
		HttpResponse response = client.execute(post);

		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			throw new ServiceRulesException(ExceptionContent.MSG_LINK_ERROR);
		}
		String result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
	//	client.getConnectionManager().shutdown();//关闭连接，未验证
	//	HttpClientFactory.CloseHttpClient(client, 20);
		if (result != null) { 
			
		} else {
			throw new ServiceRulesException(ExceptionContent.MSG_SEND_FAILED);
		} 
		return true;
	}

	
	@Override
	public boolean createRoom(String UserName,String RoomName,List<UserInfoPublic> users)throws Exception {
		// TODO Auto-generated method stub
		HttpClient client = HttpClientFactory.GetHttpClient();
		String uri = ServiceUrl+"CreateRoom";
		HttpPost post = new HttpPost(uri);
		NameValuePair LoginNamePair = new BasicNameValuePair("LoginName",UserName);
		NameValuePair RoomNamePair = new BasicNameValuePair("RoomName", RoomName);
		JSONArray jsonArray = new JSONArray();
		for(UserInfoPublic user:users)
		{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("LoginName", user.getLoginName());
			jsonObject.put("Mail", user.getMail());
			jsonArray.put(jsonObject);
		}
		NameValuePair userListNameValuePair = new BasicNameValuePair("UserList", jsonArray.toString());
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(LoginNamePair);
		parameters.add(RoomNamePair);
		parameters.add(userListNameValuePair);
		post.setEntity(new UrlEncodedFormEntity(parameters, HTTP.UTF_8));
		HttpResponse response = client.execute(post);

		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			throw new ServiceRulesException(ExceptionContent.MSG_LINK_ERROR);
		}
		String result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
	//	client.getConnectionManager().shutdown();//关闭连接，未验证
	//	HttpClientFactory.CloseHttpClient(client, 20);
		if (result != null) {
			
		} else {
			throw new ServiceRulesException(ExceptionContent.MSG_CREAT_FAILED);
		}
		return true;
	}

	@Override
	public boolean joinRoom() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<UserInfoPublic> getUsers(String UserName) throws Exception {
		// TODO Auto-generated method stub
		HttpClient client = HttpClientFactory.GetHttpClient();
		String url = ServiceUrl + "GetUsersOnline";
		HttpPost post = new HttpPost(url);
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		NameValuePair LoginNamePair = new BasicNameValuePair("LoginName",
				UserName);
		parameters.add(LoginNamePair);
		post.setEntity(new UrlEncodedFormEntity(parameters, HTTP.UTF_8));
		HttpResponse response = client.execute(post);
		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {

			throw new ServiceRulesException(ExceptionContent.MSG_LINK_ERROR);
		}
		String result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
		// client.getConnectionManager().shutdown();//关闭连接，未验证
		//HttpClientFactory.CloseHttpClient(client, 20);
		if (result != null) {
			JSONArray jsonArray = new JSONArray(result);
			List<UserInfoPublic> templist = new ArrayList<UserInfoPublic>();
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject userObject = jsonArray.getJSONObject(i);
				String name = userObject.getString("loginName");
				String mail = userObject.getString("mail");
				templist.add(new UserInfoPublic(name, mail));
			}
			return templist;
		} else {

			throw new ServiceRulesException(ExceptionContent.MSG_GETLIST_FAILED);
		}

	}

	@Override
	public RequestBean getRequest(String UserName) throws Exception{
		// TODO Auto-generated method stub
		RequestBean requestBean = null;
		HttpClient client = HttpClientFactory.GetHttpClient();
		String url = ServiceUrl + "GetRequest";
		HttpPost post = new HttpPost(url);
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		NameValuePair LoginNamePair = new BasicNameValuePair("LoginName",
				UserName);
		parameters.add(LoginNamePair);
		post.setEntity(new UrlEncodedFormEntity(parameters, HTTP.UTF_8));
		HttpResponse response = client.execute(post);
		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {

			throw new ServiceRulesException(ExceptionContent.MSG_LINK_ERROR);
		}
		String result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
		if (result != null) {
			System.out.println(result);
			JSONObject jsonObject = new JSONObject(result);
			JSONArray msgs = null;
			JSONObject request = null;
			String stoped = null;
			JSONArray Guests = null;
			try {
				msgs = jsonObject.getJSONArray("msgs");
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				request = jsonObject.getJSONObject("request");
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				stoped = jsonObject.getString("stoped");
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				Guests = jsonObject.getJSONArray("guests");
			} catch (Exception e) {
				// TODO: handle exception
			}
			List<Message> msgList = new ArrayList<Message>();
			List<UserInfoPublic> geustlist = new ArrayList<UserInfoPublic>();
			for(int i = 0;i < msgs.length();i++)
			{	
				JSONObject msg = msgs.getJSONObject(i);
				msgList.add(new Message(msg.getString("sender"), msg.getString("content"), msg.getString("data"), msg.getString("receiver")));
			}
			for(int i = 0;i < Guests.length();i++)
			{	
				JSONObject guest = Guests.getJSONObject(i);
				geustlist.add(new UserInfoPublic(guest.getString("loginName"),guest.getString("mail")));
			}
			com.just.AudioRecorder.Dao.Bean.getRequest getrequest = null;
			
			if (request != null)
				getrequest = new com.just.AudioRecorder.Dao.Bean.getRequest(request.getString("sender"), request.getString("roomName"));
			
			requestBean = new RequestBean(msgList, getrequest, stoped, geustlist);
			}else {
			throw new ServiceRulesException(ExceptionContent.MSG_REQUEST_ERROR);
		}
		return requestBean;
	}

	@Override
	public AcceptReturnBean AcceptRequest(String LoginName, String RoomName)
			throws Exception {
		// TODO Auto-generated method stub
		AcceptReturnBean acceptReturnBean = null;
		HttpClient client = HttpClientFactory.GetHttpClient();
		String uri = ServiceUrl+"AcceptRequest";
		HttpPost post = new HttpPost(uri);
		NameValuePair LoginNamePair = new BasicNameValuePair("LoginName",
				LoginName);
		NameValuePair RoomNamePair = new BasicNameValuePair(
				"RoomName", RoomName);
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(LoginNamePair);
		parameters.add(RoomNamePair);
		post.setEntity(new UrlEncodedFormEntity(parameters, HTTP.UTF_8));
		HttpResponse response = client.execute(post);

		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			throw new ServiceRulesException(ExceptionContent.MSG_LINK_ERROR);
		}
		String result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
	//	client.getConnectionManager().shutdown();//关闭连接，未验证
	//	HttpClientFactory.CloseHttpClient(client, 20);
		if (result != null) {
			JSONObject jsonObject = new JSONObject(result);
			String Room = jsonObject.getString("roomName");
			String RoomOwner = jsonObject.getString("roomOwner");
			JSONArray MsgList = jsonObject.getJSONArray("msgList");
			JSONArray Guests = jsonObject.getJSONArray("guests");
			List<UserInfoPublic> guestList = new ArrayList<UserInfoPublic>();
			List<Message> messagelist = new ArrayList<Message>();
			for (int i = 0; i < Guests.length(); i++) {
				guestList.add(new UserInfoPublic(Guests.getJSONObject(i).getString("loginName"), Guests.getJSONObject(i).getString("mail")));
			}
			for (int i = 0; i < MsgList.length(); i++) {
				messagelist.add(new Message(MsgList.getJSONObject(i).getString("sender"), MsgList.getJSONObject(i).getString("content"), 
						MsgList.getJSONObject(i).getString("data"), MsgList.getJSONObject(i).getString("receiver")));
			}
			acceptReturnBean = new AcceptReturnBean(Room, RoomOwner, messagelist, guestList);
		} else {
			throw new ServiceRulesException(ExceptionContent.MSG_LOGIN_FAILED);
		}
		return acceptReturnBean;
	}

	@Override
	public void StopRecorder(String LoginName, String RoomName) throws Exception{
		// TODO Auto-generated method stub
		HttpClient client = HttpClientFactory.GetHttpClient();
		String uri = ServiceUrl+"StopRecorder";
		HttpPost post = new HttpPost(uri);
		NameValuePair LoginNamePair = new BasicNameValuePair("LoginName",
				LoginName);
		NameValuePair RoomNamePair = new BasicNameValuePair(
				"RoomName", RoomName);
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(LoginNamePair);
		parameters.add(RoomNamePair);
		post.setEntity(new UrlEncodedFormEntity(parameters, HTTP.UTF_8));
		HttpResponse response = client.execute(post);

		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			throw new ServiceRulesException(ExceptionContent.MSG_LINK_ERROR);
		}
		String result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
	//	client.getConnectionManager().shutdown();//关闭连接，未验证
	//	HttpClientFactory.CloseHttpClient(client, 20);
		if (result.equals("success")) {
			
		} else {
			throw new ServiceRulesException(ExceptionContent.MSG_STOP_FAILED);
		}
	}

}
