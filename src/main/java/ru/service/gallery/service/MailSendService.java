package ru.service.gallery.service;

import ru.service.gallery.entity.UserInfo;
import ru.service.gallery.model.UserRegistration;

public interface MailSendService {

	public boolean sendResorePswEmail(UserInfo userInfo, String restoreLink);
	public boolean sendRegistrationEmail(UserRegistration userRegistration, String registrationLink);
	public boolean sendChangeEmail(String userName, String changeEmailLink, String email);
}
