package ru.service.gallery.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ru.service.gallery.entity.Image;
import ru.service.gallery.entity.UserInfo;

public interface UserInfoService {

	public UserInfo findOne(long userId);
	public boolean changeemail(long userId, String email);
	public boolean existEmail(String email);
	public byte[] getAvatar(long userId);
	public boolean saveUserImages(String userAppName, List<MultipartFile> images) throws IOException;
	public String userNameByUserId(long userId);
	public UserInfo findUserInfoByIdWithUserApp(long userId);
	public boolean save(UserInfo userInfo);
	public List<Image> imagesBySibscribs(String userName);
	public boolean unfolow(String userName, Long userId);
}
