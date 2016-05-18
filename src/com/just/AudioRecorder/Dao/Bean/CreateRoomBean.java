package com.just.AudioRecorder.Dao.Bean;

import java.io.Serializable;
import java.util.List;

public class CreateRoomBean implements Serializable{
	public String getRoomName() {
		return RoomName;
	}
	public void setRoomName(String roomName) {
		RoomName = roomName;
	}
	public List<UserInfoPublic> getUsers() {
		return users;
	}
	public void setUsers(List<UserInfoPublic> users) {
		this.users = users;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -4518425008413908909L;
	public String RoomName;
	public List<UserInfoPublic> users;
	public CreateRoomBean(String RoomName,List<UserInfoPublic> users) {
		// TODO Auto-generated constructor stub
		this.RoomName = RoomName;
		this.users = users;
	}
}
