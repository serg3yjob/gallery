package ru.service.gallery.controller;

import java.security.Principal;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ru.service.gallery.appsuport.exception.EmailFoundException;
import ru.service.gallery.service.UserAppService;
import ru.service.gallery.service.UserInfoService;

@Controller("changeemailController")
public class ChangeemailController {
	
	@Autowired
	private UserAppService userAppService;
	@Autowired
	private UserInfoService userInfoService; 
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(value = "/pages/changeemail", method = RequestMethod.GET)
	public String changeemailPage(){
		
		return "gallery/changeemail";
	}
	@RequestMapping(value = "/pages/changeemail", method = RequestMethod.POST)
	public String changeemailSendEmail(@RequestParam(value = "email", required = true) String email, Principal principal, Model model, Locale locale) {
		try{
			if(userAppService.changeemailSendEmail(principal.getName(), email)) return "redirect:/pages/changeemailnotifymsg";
		}catch(EmailFoundException e) {
			model.addAttribute("emailFoundMsg", messageSource.getMessage("changeemail.emailfound", new Object[]{}, locale));
			return "gallery/changeemail";
		}
		
		return "redirect:/pages/error";
	}
	@RequestMapping(value = "/pages/changeemailnotifymsg")
	public String changeemaiNotifyMsg() {
		return "gallery/messagesendemail";
	}
	@RequestMapping(value = "/pages/changeemail/{userId}/{email}", method = RequestMethod.GET)
	public String changeemail( @PathVariable("email") String email, @PathVariable("userId") long userId,
			Model model, Locale locale, RedirectAttributes redirectAttr){
		if(userInfoService.changeemail(userId, email)){
			String text = messageSource.getMessage("changeemail.success", new Object[]{}, locale);
			redirectAttr.addFlashAttribute("msgSuccessChangeEmail", text);
			return "redirect:/pages/settings";
		}
		return "redirect:/pages/error";
	}	
}
