package com.just.AudioRecorder.Service;

import java.util.List;

import com.just.AudioRecorder.Dao.Bean.UserInfoPublic;

public interface IGetUserListCallBack {
	public void setAdapter(List<UserInfoPublic> list);
}
