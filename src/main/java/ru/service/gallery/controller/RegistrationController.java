package ru.service.gallery.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.service.gallery.model.UserRegistration;
import ru.service.gallery.service.UserAppService;

@Controller("registrationController")
public class RegistrationController {

	@Autowired
	private UserAppService userAppService;
	
	@RequestMapping(value = "/pages/registrate", method = RequestMethod.POST)
	public String registrate(@ModelAttribute("userRegistration") @Valid UserRegistration userRegistration, BindingResult bindingResult,
			Model model){
		System.out.println(userRegistration);
		if(bindingResult.hasErrors()){
			model.addAttribute("userRegistration", userRegistration);
			model.addAttribute("regerror", true);
			return "gallery/images";
		}
		if(userAppService.saveUserRegistrationAndSendEmail(userRegistration)) return "gallery/messagesendemail";
		else return "redirect:/pages/error";
	}
	@RequestMapping(value = "/pages/registrate/{userId}/{hash}", method = RequestMethod.GET)
	public String enableUser(@PathVariable("userId") long userId, @PathVariable("hash") String hash) {
		if(userAppService.activateUser(userId, hash)) return "gallery/successregistration";
		else return "gallery/error";
	}
}
