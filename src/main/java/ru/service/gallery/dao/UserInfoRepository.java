package ru.service.gallery.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ru.service.gallery.entity.UserInfo;

public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {

	public UserInfo findByEmail(String email);
	
	@Query("from UserInfo userInfo join fetch userInfo.userApp where userInfo.userInfoId =:id")
	public UserInfo findByIdWithUserApp(@Param("id") long id);
	
/*	@Query("from UserInfo userInfo where userInfo.userApp.userName =:userName")
	public UserInfo findByUserNameWithIdols(@Param("userName") String userName);
	
	@Query("from UserInfo userInfo where userInfo.userInfoId =:userId")
	public UserInfo findByUserIdWithFollowers(@Param("userId") long userId);*/
	
	@Query("from UserInfo userInfo left join fetch userInfo.idols where userInfo.userApp.userName =:userName")
	public UserInfo findByUserNameWithIdols(@Param("userName") String userName);
	
	@Query("from UserInfo userInfo left join fetch userInfo.subscribers where userInfo.userInfoId =:userId")
	public UserInfo findByUserIdWithFollowers(@Param("userId") long userId);
}
