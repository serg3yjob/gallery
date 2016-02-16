package ru.service.gallery.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ru.service.gallery.entity.Image;
import ru.service.gallery.service.UserInfoService;

@Controller("subscribsController")
public class SubscribsController {

	@Autowired
	private UserInfoService userInfoService;
	
	@RequestMapping(value = "/pages/subscribs", method = RequestMethod.GET)
	public String subscribsPage(Principal principal, Model model,
			@RequestParam(value = "userId", required = false) Long userId){
		if(userId != null)
			if(!userInfoService.unfolow(principal.getName(), userId))
				return "redirect:/pages/error";
		List<Image> images = userInfoService.imagesBySibscribs(principal.getName());
		model.addAttribute("imagesS", images);
		return "gallery/subscribs";
	}
}
