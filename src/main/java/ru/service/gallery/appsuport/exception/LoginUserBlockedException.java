package ru.service.gallery.appsuport.exception;

import org.springframework.security.core.AuthenticationException;

public class LoginUserBlockedException extends AuthenticationException {

	private String customMsg;

	public LoginUserBlockedException(String msg){
		super(msg);
	}
	
	public String getCustomMsg() {
		return customMsg;
	}

	public void setCustomMsg(String customMsg) {
		this.customMsg = customMsg;
	}
}
