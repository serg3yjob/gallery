package ru.service.gallery.appsuport.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import ru.service.gallery.entity.UserApp;
import ru.service.gallery.entity.UserGroup;
import ru.service.gallery.entity.UserInfo;
import ru.service.gallery.model.UserRegistration;
import ru.service.gallery.service.UserGroupService;
import ru.service.gallery.util.Util;

public class ConvertUserRegistrationToUserApp implements Converter<UserRegistration, UserApp> {

	@Autowired
	private UserGroupService userGroupService;
	
	@Override
	public UserApp convert(UserRegistration source) {
		String encodingPassword = Util.encodePassword(source.getUserPassword());
		UserApp userApp = new UserApp(source.getUserName(), encodingPassword, source.getRegDate());
		UserInfo userInfo = new UserInfo(source.getEmail());
		UserGroup userGroup = userGroupService.findByGroupName("USER");
		userGroup.getUserApps().add(userApp);
		userApp.getUserGroups().add(userGroup);
		userApp.setUserInfo(userInfo);
		userInfo.setUserApp(userApp);
		return userApp;
	}

}
