package ru.service.gallery.model;

import java.util.Date;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class UserRegistration {

	private String userName;
	private String userPassword;
	private String userRetryPassword;
	private String email;
	private Date regDate;
	
	public UserRegistration() {
		super();
		regDate = new Date();
	}
	public UserRegistration(String userName, String userPassword, String userRetryPassword, String email, Date regDate) {
		super();
		this.userName = userName;
		this.userPassword = userPassword;
		this.userRetryPassword = userRetryPassword;
		this.email = email;
		this.regDate = regDate;
	}
	
//	@NotEmpty(message = "{regform.username.required}")
//	@Size(min = 2, max = 14, message = "{regform.username.rangelength}")
	@NotEmpty(message = "Поле \"Ваше имя\" обязательно для заполнения")
	@Size(min = 2, max = 14, message = "Поле \"Ваше имя\" должно содержать от {2} до {1} символов")	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
//	@NotEmpty(message = "{regform.userPassword.required}")
//	@Size(min = 6, message = "{regform.userPassword.minlength}")
	@NotEmpty(message = "Введите пароль")
	@Size(min = 6, message = "Пароль должен содержать не менее {2} символов")
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserRetryPassword() {
		return userRetryPassword;
	}
	public void setUserRetryPassword(String userRetryPassword) {
		this.userRetryPassword = userRetryPassword;
	}
//	@NotEmpty(message = "{regform.email.required}")
//	@Email(message = "{regform.email.notemail}")
	@NotEmpty(message = "Введите адрес электронной почты")
	@Email(message = "Поле \"Почта\" должно содержать адрес электронной почты")	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
//	@AssertTrue(message = "{regform.userRetryPassword.equalToPassword}")
	@AssertTrue(message = "Подтверждение пароля не совпадает с введеным паролем")
	public boolean isPasswordEqualToRetryPassword() {
		if(userPassword == null)return true;
		return userPassword.equals(userRetryPassword);
	}
	@Override
	public String toString() {
		return "UserRegistration [userName=" + userName + ", userPassword=" + userPassword + ", userRetryPassword="
				+ userRetryPassword + ", email=" + email + "]";
	}
	public static UserRegistration getEmptyUserRegistration() {
		return new UserRegistration("", "", "", "", new Date());
	}
}
