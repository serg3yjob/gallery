package ru.service.gallery.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import ru.service.gallery.entity.Image;
import ru.service.gallery.entity.UserInfo;

public interface ImageRepository extends PagingAndSortingRepository<Image, Long> {

	@Query("update Image img set img.title =:title where img.imageId =:id")
	public void updateTitleImg(@Param("title") String titleImg, @Param("id") Long imgId);//Метод не работает
	
	@Query("select count(*) from Image img where img.userInfo.userApp.userName =:userName")
	public long countImageByUserName(@Param("userName") String userName);
	
	@Query("select count(*) from Image img where img.userInfo.userInfoId =:userInfoId")
	public long countImageByUserInfoId(@Param("userInfoId") long userInfoId);
	
	public Page<Image> findByUserInfo(UserInfo userInfo, Pageable pageable);
	
	public Page<Image> findAll(Pageable pageable);
	
	@Query("from Image img join fetch img.marks where img.imageId =:imgId")
	public Image findByIdWithMark(@Param("imgId") long imgId);
}
