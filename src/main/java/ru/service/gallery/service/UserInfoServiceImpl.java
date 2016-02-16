package ru.service.gallery.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ru.service.gallery.dao.UserInfoRepository;
import ru.service.gallery.entity.Image;
import ru.service.gallery.entity.UserApp;
import ru.service.gallery.entity.UserInfo;

@Repository
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	private CommonService commonService;
	@Autowired
	private UserAppService userAppService;
	
	@Override
	@Transactional(readOnly = true)
	public UserInfo findOne(long userId) {
		return userInfoRepository.findOne(userId);
	}
	@Override
	@Transactional
	public boolean changeemail(long userId, String email) {
		UserInfo userInfo = userInfoRepository.findOne(userId);
		userInfo.setEmail(email);
		userInfoRepository.save(userInfo);
		return true;
	}
	@Override
	@Transactional(readOnly = true)
	public boolean existEmail(String email) {
		if(userInfoRepository.findByEmail(email) != null) return true;
		return false;
	}
	@Override
	@Transactional(readOnly = true)
	public byte[] getAvatar(long userId) {
		return commonService.getAvatarByUserInfo(userInfoRepository.findOne(userId));
	}
	@Override
	@Transactional
	public boolean saveUserImages(String userAppName, List<MultipartFile> images) throws IOException {
		UserInfo userInfo = userAppService.findByUserNameWithUserInfo(userAppName).getUserInfo();
		int i = 0;
		for(MultipartFile img : images){
			Image image = new Image();
			image.setUserInfo(userInfo);
			image.setContent(img.getBytes());
			Date date = new Date(new Date().getTime() + i++);//сделано для того чтобы временные метки даты сохранения файалов гарантировано различались 
			image.setDateUpload(date);
			userInfo.getImages().add(image);
		}
		userInfoRepository.save(userInfo);
		return true;
	}
	@Override
	@Transactional(readOnly = true)
	public String userNameByUserId(long userId) {
		UserApp userApp = userInfoRepository.findByIdWithUserApp(userId).getUserApp();
		return userApp.getUserName();
	}
	@Override
	@Transactional(readOnly = true)
	public UserInfo findUserInfoByIdWithUserApp(long userId) {
		return userInfoRepository.findByIdWithUserApp(userId);
	}
	@Transactional
	@Override
	public boolean save(UserInfo userInfo) {
		userInfoRepository.save(userInfo);
		return true;
	}
	@Transactional(readOnly = true)
	@Override
	public List<Image> imagesBySibscribs(String userName) {
		List<Image> images = new ArrayList<>();
		Set<UserInfo> idols = userInfoRepository.findByUserNameWithIdols(userName).getIdols();
		for(UserInfo idol : idols)
			images.addAll(idol.getImages());
		Collections.sort(images, new Comparator<Image>() {
			public int compare(Image img1, Image img2){
				if(img1.getDateUpload().after(img2.getDateUpload())) return -1;
				if(img1.getDateUpload().before(img2.getDateUpload())) return 1;
				return 0;
			}
		});
		return images;
	}
	@Override
	@Transactional
	public boolean unfolow(String userName, Long userId) {
		UserInfo userFolower = userInfoRepository.findByUserNameWithIdols(userName);
		Iterator<UserInfo> iterator = userFolower.getIdols().iterator();
		UserInfo idol;
		boolean in = false;
		while(iterator.hasNext()){
			idol = iterator.next();
			if(idol.getUserInfoId() == userId){
				iterator.remove();
				userInfoRepository.save(userFolower);
				in = true;
				break;
			}
		}
		UserInfo userIdol = userInfoRepository.findByUserIdWithFollowers(userId);
		iterator = userIdol.getSubscribers().iterator();
		UserInfo follower;
		while(iterator.hasNext()){
			follower = iterator.next();
			if(follower.getUserInfoId() == userFolower.getUserInfoId()){
				iterator.remove();
				userInfoRepository.save(userIdol);
				break;
			}
		}
		return in;
	}
}
