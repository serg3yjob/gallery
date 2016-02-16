package ru.service.gallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.service.gallery.entity.UserApp;
import ru.service.gallery.service.UserAppService;

@Controller("userprofileController")
public class UserprofileController {

	@Autowired
	private UserAppService userAppService;
	
	@RequestMapping(value = "/pages/userprofile/{userId}", method = RequestMethod.GET)
	public String userprofilePage(@PathVariable("userId") long userId, Model model){
		UserApp userApp = userAppService.findByUserIdWithUserInfo(userId);
		model.addAttribute("userAppByUserProfile", userApp);
		return "gallery/userprofile";
	}
}
