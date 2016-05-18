package com.just.AudioRecorder.Dao;

import java.util.List;

import com.just.AudioRecorder.Dao.Bean.AcceptReturnBean;
import com.just.AudioRecorder.Dao.Bean.LoginBean;
import com.just.AudioRecorder.Dao.Bean.Message;
import com.just.AudioRecorder.Dao.Bean.RegistBean;
import com.just.AudioRecorder.Dao.Bean.RequestBean;
import com.just.AudioRecorder.Dao.Bean.UserInfoPublic;

public interface IHttpService {
	public boolean regist(RegistBean registBean) throws Exception;
	public String login(LoginBean loginBean)throws Exception;
	public boolean sendmsg(String LoginName,Message msg)throws Exception;
	public List<UserInfoPublic> getUsers(String UserName)throws Exception;
	public boolean createRoom(String UserName,String RoomName,List<UserInfoPublic> users)throws Exception;
	public boolean joinRoom()throws Exception;
	public RequestBean getRequest(String UserName)throws Exception;
	public AcceptReturnBean AcceptRequest(String LoginName,String RoomName) throws Exception;
	public void StopRecorder(String LoginName,String RoomName) throws Exception;
}
