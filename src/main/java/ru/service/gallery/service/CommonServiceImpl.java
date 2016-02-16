package ru.service.gallery.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import ru.service.gallery.appsuport.exception.ResourceNotFoundException;
import ru.service.gallery.entity.UserInfo;

@Service("commonService")
public class CommonServiceImpl implements CommonService, ApplicationContextAware{

	private ApplicationContext context;
	
	@Override
	public byte[] getAvatarByUserInfo(UserInfo userInfo) {
		byte[] avatar = userInfo.getAvatar();
		if(avatar != null && avatar.length > 0)return avatar;
		Resource resource = context.getResource("/img/defaultAvatar.jpg");
		try{
			File file = resource.getFile();
			avatar = Files.readAllBytes(file.toPath());
		}catch(IOException e){
			throw new ResourceNotFoundException();
		}
		return avatar;
	}
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

}
