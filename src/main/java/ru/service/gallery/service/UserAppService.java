package ru.service.gallery.service;


import java.util.List;

import ru.service.gallery.entity.UserApp;
import ru.service.gallery.model.UserRegistration;

public interface UserAppService {

	public UserApp findByUserNameWithGroup(String userName);
	public UserApp save(UserApp userApp);
	public UserApp findByUserNameWithGroupAndUserInfo(String userName);
	public boolean existUserByEmailOrName(String email, String userName);
	public boolean saveUserRegistrationAndSendEmail(UserRegistration userRegistration);
	public boolean activateUser(long userId, String hash);
	public UserApp findOne(long userId);
	public boolean changePassword(String userName, String oldPsw, String password);
	public boolean changePasswordRestore(long userId, String password);
	public UserApp findByUserName(String userName);
	public UserApp findByUserNameWithUserInfo(String userName);
	public UserApp findByUserIdWithUserInfo(long userId);
	public boolean changeemailSendEmail(String userName, String email);
	public UserApp findByUserInfoEmail(String email);
	public boolean restorePasswordSendEmail(String email);
	public boolean canRestorePassword(long userId, String hash);
	public byte[] getAvatarByUserName(String userName);
	public boolean saveAvatar(String userName, byte[] avatar);
	public List<UserApp> allUsersWithUserInfo();
	public boolean updateUserStatus(boolean status, long userAppId);
	public boolean updateUserBan(boolean ban, long userAppId);
	public List<String> listUserNamesByLike(String byLike);
	public List<UserApp> listUserByLike(String byLike);
	public long userInfoIdByUserName(String userName);
}
