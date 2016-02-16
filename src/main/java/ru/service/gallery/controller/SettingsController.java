package ru.service.gallery.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;

import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ru.service.gallery.entity.UserApp;
import ru.service.gallery.service.UserAppService;

@Controller("settingsController")
public class SettingsController{

	private ApplicationContext context;
	@Autowired
	private UserAppService userAppService;
	
	@RequestMapping(value = "/pages/settings", method = RequestMethod.GET)
	public String settingsPage(Principal principal, Model model){
		UserApp userApp = userAppService.findByUserNameWithUserInfo(principal.getName());
		model.addAttribute("userApp", userApp);
		return "gallery/settings";
	}
	@ResponseBody
	@RequestMapping(value = "pages/settings/avatar", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] avatar(Principal principal) {
		return userAppService.getAvatarByUserName(principal.getName());
	}
	@RequestMapping(value = "pages/settings/avatar", method = RequestMethod.POST)
	public String uploadAvatar(@RequestParam(value = "fileAvatar", required = true) Part file, Principal principal){
		try{
			byte[] avatar = new byte[file.getInputStream().available()];
			file.getInputStream().read(avatar);
			userAppService.saveAvatar(principal.getName(), avatar);
		}catch(IOException e) {
			return "redirect:/pages/error";
		}
		return "redirect:/pages/settings";
	}
}
