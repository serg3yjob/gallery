package ru.service.gallery.controller;

import java.security.Principal;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ru.service.gallery.service.UserAppService;
import ru.service.gallery.service.UserInfoService;

@Controller("restorepasswordController")
public class RestorepasswordController {

	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserAppService userAppService;
	@Autowired
	private MessageSource messageSource; 
	
	@RequestMapping(value = "/pages/restorepassword", method = RequestMethod.GET)
	public String restorepasswordPage(){
		
		return "gallery/restorepassword";
	}
	@RequestMapping(value = "/pages/restorepassword", method = RequestMethod.POST)
	public String restorepassword(@RequestParam(value = "email", required = true) String email, RedirectAttributes attr, Locale locale){
		try {
			if(userAppService.restorePasswordSendEmail(email)) return "redirect:/pages/restorepasswordnotifymsg";
			else return "redirect:/pages/error";
		} catch(UsernameNotFoundException e) {
			attr.addFlashAttribute("msgNotFoundUser", messageSource.getMessage("restorepassword.notfoundemail", new Object[]{}, locale));
			return "redirect:/pages/restorepassword";
		}
	}
	@RequestMapping(value = "/pages/changepassword/{hash}/{userId}", method = RequestMethod.GET)
	public String changepasswordPage(@PathVariable("hash") String hash, @PathVariable("userId") long userId, Model model){
		if(userAppService.canRestorePassword(userId, hash)){
			model.addAttribute("userId", userId);
			return "gallery/changepasswordrestore";
		}
		else return "gallery/error";
	}
	@RequestMapping(value = "/pages/changepasswordrestore", method = RequestMethod.POST)
	public String changepasswordrestore(
			@RequestParam(value = "settingsUserPassword", required = true) String settingsUserPassword,
			@RequestParam(value = "userId", required = true) long userId,
			Model model, Locale locale){
		if(userAppService.changePasswordRestore(userId, settingsUserPassword)){
			return "redirect:/pages/restorepasswordsuccess";
		}
		return "redirect:/pages/error";
	}
	@RequestMapping(value = "/pages/restorepasswordsuccess", method = RequestMethod.GET)
	public String restorepasswordSuccessMsg() {
		return "gallery/successrestorepassword";
	}	
	@RequestMapping(value = "/pages/restorepasswordnotifymsg", method = RequestMethod.GET)
	public String restorepasswordNotifyMsg() {
		return "gallery/messagesendemail";
	}
}
