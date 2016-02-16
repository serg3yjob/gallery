package ru.service.gallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.service.gallery.service.ImageService;
import ru.service.gallery.service.UserInfoService;

@Controller("commonController")
public class CommonController {

	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private ImageService imageService;
	
	@RequestMapping(value = "/avatar/{userId}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] avatar(@PathVariable("userId") long userId){
		return userInfoService.getAvatar(userId);
	}
	@RequestMapping(value = "/images/{imgId}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] image(@PathVariable("imgId") long imgId){
		return imageService.imageContent(imgId);
	}
}
