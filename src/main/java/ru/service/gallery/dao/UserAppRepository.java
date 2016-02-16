package ru.service.gallery.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ru.service.gallery.entity.UserApp;

public interface UserAppRepository extends CrudRepository<UserApp, Long> {

	@Query("from UserApp userApp join fetch userApp.userGroups where userApp.userName =:userName")
	public UserApp findByUserNameWithGroup(@Param("userName") String userName);
	
	@Query("from UserApp userApp join fetch userApp.userGroups join fetch userApp.userInfo where userApp.userName =:userName")
	public UserApp findByUserNameWithGroupAndUserInfo(@Param("userName") String userName);
	
	public UserApp findByUserName(String userName);
	
	@Query("from UserApp userApp join fetch userApp.userInfo where userApp.userName =:userName")
	public UserApp findByUserNameWithUserInfo(@Param("userName") String userName);
	
	@Query("from UserApp userApp join fetch userApp.userInfo where userApp.userAppId =:userid")
	public UserApp findByUserNameWithUserInfo(@Param("userid")long userId);
	
	@Query("from UserApp userApp where userApp.userInfo.email =:email")
	public UserApp findByUserInfoEmail(@Param("email") String email);
	
	public UserApp findByUserAppIdAndUserPassword(long userAppId, String userPassword);
	
	@Query("from UserApp userApp where userApp.userName =:userName or userApp.userInfo.email =:email")
	public UserApp findByUserNameOrUserInfoEmail(@Param("userName") String userName, @Param("email") String email);
	
	@Query("from UserApp userApp join fetch userApp.userInfo")
	public List<UserApp> allUsersWithUserInfo();
	
	@Query("from UserApp userApp where userApp.userName like :byLike")
	public List<UserApp> findByUserNameLike(@Param("byLike") String byLike);
}
