package com.just.AudioRecorder.Dao.impl;

import java.util.ArrayList;
import java.util.List;

public class TextFixDao {

	public TextFixDao() {
		// TODO Auto-generated constructor stub
	}
	
	public List<String> StrFix(String src)
	{
		int start = src.indexOf("��·");
		List<String> list = null;
		if (start >= 0) {
			list= new ArrayList<String>();
			list.add(src.replaceAll("��·", "��½"));
			list.add(src.replaceAll("��·", "��¼"));
		}
		return list;
		
	}

}
