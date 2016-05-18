package com.just.AudioRecorder.Dao.Bean;

import java.io.Serializable;

public class RegistBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5160970743218407129L;
	public String LoginName;
	public String LoginPasswd;
	public String Mail;
	
	public RegistBean(String LoginName,String LoginPasswd,
			String Mail) {
		// TODO Auto-generated constructor stub
		this.LoginName = LoginName;
		this.LoginPasswd = LoginPasswd;
		this.Mail = Mail;
	}

	public String getLoginName() {
		return LoginName;
	}

	public void setLoginName(String loginName) {
		LoginName = loginName;
	}

	public String getLoginPasswd() {
		return LoginPasswd;
	}

	public void setLoginPasswd(String loginPasswd) {
		LoginPasswd = loginPasswd;
	}

	public String getMail() {
		return Mail;
	}

	public void setMail(String mail) {
		Mail = mail;
	}

}
