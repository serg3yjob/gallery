package ru.service.gallery.appsuport.exception;

import org.springframework.security.core.AuthenticationException;

public class LoginUserDisabledException extends AuthenticationException {

	private String customMsg;

	public LoginUserDisabledException(String msg){
		super(msg);
	}
	
	public String getCustomMsg() {
		return customMsg;
	}

	public void setCustomMsg(String customMsg) {
		this.customMsg = customMsg;
	}
}
