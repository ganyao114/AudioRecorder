package com.just.AudioRecorder.Dao.impl;

import java.util.ArrayList;
import java.util.List;

public class TextFixDao {

	public TextFixDao() {
		// TODO Auto-generated constructor stub
	}
	
	public List<String> StrFix(String src)
	{
		int start = src.indexOf("µÇÂ·");
		List<String> list = null;
		if (start >= 0) {
			list= new ArrayList<String>();
			list.add(src.replaceAll("µÇÂ·", "µÇÂ½"));
			list.add(src.replaceAll("µÇÂ·", "µÇÂ¼"));
		}
		return list;
		
	}

}
