package ru.service.gallery.service;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Set;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.annotation.Commit;

import ru.service.gallery.entity.UserApp;
import ru.service.gallery.entity.UserGroup;
import ru.service.gallery.entity.UserRole;
import ru.service.gallery.model.UserRegistration;

@Transactional
@Commit
public class TestServices extends AbstractServiceImplTest {

	@Autowired(required = true)
	private UserGroupService userGroupService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private UserAppService userAppService;
	
	@Ignore
	@Test
	public void saveUserGroup() {
		UserGroup userGroup = new UserGroup("ADMIN");
		System.out.println(userGroupService.save(userGroup));
		userGroup = new UserGroup("USER");
		System.out.println(userGroupService.save(userGroup));
	}
	@Ignore
	@Test
	public void getUserGroupAndSaveWithRole() {
		String[] userGroups = {"ADMIN", "USER"};
		for(String groupName : userGroups){
			UserGroup userGroup = userGroupService.findByGroupName(groupName);
			UserRole userRole = new UserRole(groupName);
			Set<UserRole> userRoles = userGroup.getUserRoles();
			userRoles.add(userRole);
			Set<UserGroup> userGroups2 = userRole.getUserGroups();
			userGroups2.add(userGroup);
			userGroupService.save(userGroup);	
		}
	}
	@Ignore
	@Test
	public void getUserRoleWithGroup() {
		String[] userRoles = {"ADMIN", "USER"};
		for(String roleName : userRoles){
			UserRole userRole = userRoleService.findByRoleName(roleName);
			Set<UserGroup> userGroups = userRole.getUserGroups();
			Assert.assertNotNull(userGroups);
			int[] expectedValue = {1};
			int[] actualValue = {userGroups.size()};
			Assert.assertArrayEquals(expectedValue, actualValue);
			String[] expectedValue1 = {roleName};
			String[] actualValue1 = {userGroups.iterator().next().getGroupName()};
			Assert.assertArrayEquals(expectedValue1, actualValue1);
		}
	}
	@Ignore
	@Test
	public void saveNewUser() {
		String userName = "user", email = "a.sh.s_reg@mail.ru";
		Date regDate = new Date();
		UserRegistration userRegistration = new UserRegistration(userName, "123456", "123456", email, regDate);
		boolean success = userAppService.saveUserRegistrationAndSendEmail(userRegistration);
		Assert.assertTrue(success);
		UserApp userApp = userAppService.findByUserNameWithGroupAndUserInfo(userName);
		Assert.assertNotNull(userApp);
		Assert.assertTrue(userApp.getUserName().equals(userName));
		Assert.assertTrue(userApp.getUserInfo().getEmail().equals(email));
		Assert.assertTrue(userApp.getRegDate().equals(regDate));
		Assert.assertTrue(userApp.getUserGroups().iterator().next().getGroupName().equals("USER"));
	}
	@Ignore
	@Test
	public void activateUser() {
		long userId = 700;
		String hash = "03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4";
		userAppService.activateUser(userId, hash);
		UserApp userApp = userAppService.findOne(userId);
		Assert.assertTrue(userApp.isEnabled());
	}
}
