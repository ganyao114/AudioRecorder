package com.just.AudioRecorder.Dao.Bean;

import java.io.Serializable;
import java.util.List;

public class Room implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7709691502940746399L;
	private String Name;
	private int MaxSize;
	private int CurrentSize;
	private int RoomId;
	private String Creater;
	private String CtreateTime;
	private List<UserInfoPublic> usersInRoom;
	public Room(String RoomName) {
		// TODO Auto-generated constructor stub
		this.Name = RoomName;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getMaxSize() {
		return MaxSize;
	}
	public void setMaxSize(int maxSize) {
		MaxSize = maxSize;
	}
	public int getCurrentSize() {
		return CurrentSize;
	}
	public void setCurrentSize(int currentSize) {
		CurrentSize = currentSize;
	}
	public int getRoomId() {
		return RoomId;
	}
	public void setRoomId(int roomId) {
		RoomId = roomId;
	}
	public String getCreater() {
		return Creater;
	}
	public void setCreater(String creater) {
		Creater = creater;
	}
	public String getCtreateTime() {
		return CtreateTime;
	}
	public void setCtreateTime(String ctreateTime) {
		CtreateTime = ctreateTime;
	}
	public List<UserInfoPublic> getUsersInRoom() {
		return usersInRoom;
	}
	public void setUsersInRoom(List<UserInfoPublic> usersInRoom) {
		this.usersInRoom = usersInRoom;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
