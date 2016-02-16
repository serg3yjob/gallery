package ru.service.gallery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("errorPageController")
public class ErrorPageController {

	@RequestMapping(value = "/pages/error", method = RequestMethod.GET)
	public String errorPage(){
		
		return "gallery/error";
	}
}
