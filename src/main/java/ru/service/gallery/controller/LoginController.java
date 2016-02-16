package ru.service.gallery.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ru.service.gallery.entity.UserApp;
import ru.service.gallery.model.ButtonsOfPaginator;
import ru.service.gallery.model.UserRegistration;
import ru.service.gallery.service.ImageService;
import ru.service.gallery.service.UserAppService;

@Controller("loginController")
public class LoginController {

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private UserAppService userAppService;
	@Autowired
	private ImageService imageService;
	
	@RequestMapping(value = {"/", "/pages/images"}, method = RequestMethod.GET)
	public String basePage(@RequestParam(name = "loginerror", required = false) boolean loginerror, Locale locale, Model model, HttpServletRequest request,
			@RequestParam(name = "numberPage", required = false) Integer numberPage,
			@RequestParam(name = "button", required = false) String button_) {
		ButtonsOfPaginator button = ButtonsOfPaginator.LEFT;
		if(button_ != null){
			try {
				button = ButtonsOfPaginator.valueOf(button_);
			}catch(IllegalArgumentException e) {
				button = ButtonsOfPaginator.LEFT; 
			}
		}
		model.addAttribute("gridHolder", imageService.getGridImagesHolder(numberPage, button));
		model.addAttribute("loginerror", loginerror);
		if(loginerror){
			model.addAttribute("msgLoginError", messageSource.getMessage("loginform.username.failed", new Object[] {}, locale));
		}
		model.addAttribute("userRegistration", UserRegistration.getEmptyUserRegistration());
		List<UserApp> userApps = userAppService.allUsersWithUserInfo();
		model.addAttribute("usersByLeftMenu", userApps);
//		Для задания классов CSS пунктам левого меню
		Map<String, Boolean> mapLeftMenuCSS = new HashMap<>(5);
		String[] subStrsUrl = new String[] {"images", "albom", "subscribs", "settings", "administrate"};
		for(String str : subStrsUrl) mapLeftMenuCSS.put(str, false);
		boolean in = false;
		for(String str : subStrsUrl)  if(request.getRequestURI().contains(str)){
			mapLeftMenuCSS.put(str, true);
			in = true;
			break;
		}
		if(!in)mapLeftMenuCSS.put("images", true);
		model.addAttribute("mapLeftMenuCSS", mapLeftMenuCSS);
//		Конец, для задания классов CSS пунктам левого меню		

		return "gallery/images";
	}
}
