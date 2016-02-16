package ru.service.gallery.service;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.service.gallery.appsuport.exception.EmailFoundException;
import ru.service.gallery.dao.UserAppRepository;
import ru.service.gallery.entity.UserApp;
import ru.service.gallery.entity.UserInfo;
import ru.service.gallery.model.UserRegistration;
import ru.service.gallery.util.Util;

@Repository
@Service("userAppService")
public class UserAppServiceImpl implements UserAppService, ApplicationContextAware {

	private @Value("${applicationUrl}") String applicationUrl;
	private ApplicationContext context;
	@Autowired
	private UserAppRepository userAppRepository;
	@Autowired
	private MailSendService mailSendService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private CommonService commonService;
	
	@Override
	@Transactional(readOnly = true)
	public UserApp findByUserNameWithGroup(String userName) {
		return userAppRepository.findByUserNameWithGroup(userName);
	}
	@Override
	@Transactional
	public UserApp save(UserApp userApp) {
		return userAppRepository.save(userApp);
	}
	@Override
	@Transactional(readOnly = true)
	public UserApp findByUserNameWithGroupAndUserInfo(String userName) {
		return userAppRepository.findByUserNameWithGroupAndUserInfo(userName);
	}
	@Transactional(readOnly = true)
	public boolean existUserByEmailOrName(String email, String userName) {
		if(userAppRepository.findByUserNameOrUserInfoEmail(userName, email) != null)return true;
		return false;
	}
	@Override
	@Transactional
	public boolean saveUserRegistrationAndSendEmail(UserRegistration userRegistration) {
		if(existUserByEmailOrName(userRegistration.getEmail(), userRegistration.getUserName()))return false;
		ConversionService conversionService = context.getBean("conversionService", ConversionService.class);
		UserApp userApp = conversionService.convert(userRegistration, UserApp.class);
		userApp = save(userApp);
		String registrationLink = applicationUrl + "/pages/registrate/" + userApp.getUserAppId() + "/" + userApp.getUserPassword();
		return mailSendService.sendRegistrationEmail(userRegistration, registrationLink);
	}
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
	@Override
	@Transactional
	public boolean activateUser(long userId, String hash) {
		UserApp userApp = userAppRepository.findOne(userId);
		if(userApp.getUserPassword().equals(hash)) {
			userApp.setEnabled(true);
			userAppRepository.save(userApp);
			return true;
		} else return false;
	}
	@Override
	@Transactional(readOnly = true)
	public UserApp findOne(long userId) {
		return userAppRepository.findOne(userId);
	}
	@Override
	@Transactional
	public boolean changePassword(String userName, String oldPsw, String password) {
		UserApp userApp = findByUserName(userName);
		if(userApp == null) return false;
		if(!userApp.getUserPassword().equals(Util.encodePassword(oldPsw))) return false;
		userApp.setUserPassword(Util.encodePassword(password));
		save(userApp);
		return true;
	}
	@Override
	@Transactional
	public boolean changePasswordRestore(long userId, String password) {
		UserApp userApp = findOne(userId);
		if(userApp == null) return false;
		userApp.setUserPassword(Util.encodePassword(password));
		save(userApp);
		return true;
	}	
	@Override
	@Transactional(readOnly = true)
	public UserApp findByUserName(String userName) {
		return userAppRepository.findByUserName(userName);
	}
	@Override
	@Transactional(readOnly = true)
	public UserApp findByUserNameWithUserInfo(String userName) {
		return userAppRepository.findByUserNameWithUserInfo(userName);
	}
	@Override
	@Transactional(readOnly = true)
	public UserApp findByUserIdWithUserInfo(long userId) {
		return userAppRepository.findByUserNameWithUserInfo(userId);
	}
	@Override
	@Transactional(readOnly = true)
	public boolean changeemailSendEmail(String userName, String email) {
		if(userInfoService.existEmail(email)) throw new EmailFoundException("Пользователь с email " + email + " уже существует");
		UserApp userApp = userAppRepository.findByUserNameWithUserInfo(userName);
		String changeEmailLink = applicationUrl + "/pages/changeemail/" + userApp.getUserInfo().getUserInfoId() + "/" + email;
		return mailSendService.sendChangeEmail(userName, changeEmailLink, email);
	}
	@Override
	@Transactional(readOnly = true)
	public UserApp findByUserInfoEmail(String email) {
		return userAppRepository.findByUserInfoEmail(email);
	}
	@Override
	public boolean restorePasswordSendEmail(String email) {
		UserApp userApp = findByUserInfoEmail(email);
		if(userApp != null){
			String restoreLink = applicationUrl + "/pages/changepassword/" + userApp.getUserPassword() + "/" + userApp.getUserAppId();
			return mailSendService.sendResorePswEmail(userApp.getUserInfo(), restoreLink);
		}else throw new UsernameNotFoundException("Not find user by email \"" + email + "\"");
	}
	@Override
	@Transactional(readOnly = true)
	public boolean canRestorePassword(long userId, String hash) {
		if(userAppRepository.findByUserAppIdAndUserPassword(userId, hash) != null)return true;
		return false;
	}
	@Override
	@Transactional(readOnly = true)
	public byte[] getAvatarByUserName(String userName) {
		UserApp userApp = userAppRepository.findByUserNameWithUserInfo(userName);
		return commonService.getAvatarByUserInfo(userApp.getUserInfo());
	}
	@Override
	@Transactional
	public boolean saveAvatar(String userName, byte[] avatar) {
		UserApp userApp = userAppRepository.findByUserNameWithUserInfo(userName);
		UserInfo userInfo = userApp.getUserInfo();
		userInfo.setAvatar(avatar);
		userAppRepository.save(userApp);
		return true;
	}
	@Override
	@Transactional(readOnly = true)
	public List<UserApp> allUsersWithUserInfo() {
		return userAppRepository.allUsersWithUserInfo();
	}
	@Override
	@Transactional
	public boolean updateUserStatus(boolean status, long userAppId) {
		UserApp userApp = userAppRepository.findOne(userAppId);
		userApp.setEnabled(status);
		userAppRepository.save(userApp);
		return true;
	}
	@Override
	@Transactional
	public boolean updateUserBan(boolean ban, long userAppId) {
		UserApp userApp = userAppRepository.findOne(userAppId);
		userApp.setBlocked(ban);
		userAppRepository.save(userApp);
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<String> listUserNamesByLike(String byLike) {
		ConversionService conversionService = context.getBean("conversionService", ConversionService.class);
		byLike = "%" + byLike + "%";
		return conversionService.convert(userAppRepository.findByUserNameLike(byLike), List.class);
	}
	@Override
	@Transactional(readOnly = true)
	public List<UserApp> listUserByLike(String byLike) {
		byLike = "%" + byLike + "%";
		return userAppRepository.findByUserNameLike(byLike);
	}
	@Override
	public long userInfoIdByUserName(String userName) {
		return userAppRepository.findByUserNameWithUserInfo(userName).getUserInfo().getUserInfoId();
	}
	
}
