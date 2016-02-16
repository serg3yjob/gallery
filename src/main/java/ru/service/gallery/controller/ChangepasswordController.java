package ru.service.gallery.controller;

import java.security.Principal;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ru.service.gallery.service.UserAppService;

@Controller("changepasswordController")
public class ChangepasswordController {

	@Autowired
	private UserAppService userAppService;
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(value = "/pages/changepassword", method = RequestMethod.GET)
	public String changepasswordPage(){
		return "gallery/changepassword";
	}
	@RequestMapping(value = "/pages/changepassword", method = RequestMethod.POST)
	public String changepassword(
			@RequestParam(value = "oldUserPassword", required = true) String oldUserPassword, 
			@RequestParam(value = "settingsUserPassword", required = true) String settingsUserPassword,
			Principal principal, Model model, Locale locale, RedirectAttributes redirectAttr){
		if(userAppService.changePassword(principal.getName(), oldUserPassword, settingsUserPassword)){
			String text = messageSource.getMessage("changepassword.success", new Object[]{}, locale);
			redirectAttr.addFlashAttribute("msgSuccessChangePsw", text);
			return "redirect:/pages/settings";
		}
		return "redirect:/pages/error";
	}
}
