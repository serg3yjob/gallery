package ru.service.gallery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.service.gallery.service.UserAppService;

@Controller("administrateController")
public class AdministrateController {

	@Autowired
	private UserAppService userAppService;
	
	@RequestMapping(value = "/pages/administrate", method = RequestMethod.GET)
	public String administratePage(Model model,
			@RequestParam(value = "userAppId", required = false) Long userAppId,
			@RequestParam(value = "activate", required = false) Boolean activate,
			@RequestParam(value = "block", required = false) Boolean block,
			@RequestParam(value = "userNameBySerch", required = false) String userNameBySerch) {
		if(activate != null)userAppService.updateUserStatus(activate, userAppId);
		if(block != null)userAppService.updateUserBan(block, userAppId);
		if(userNameBySerch != null)model.addAttribute("users", userAppService.listUserByLike(userNameBySerch));
		else model.addAttribute("users", userAppService.allUsersWithUserInfo());
		return "gallery/administrate";
	}
	@RequestMapping(value = "/listusernames", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<String> listUserNames(@RequestParam(value = "term", required = true) String term) {
		List<String> userNamesList = userAppService.listUserNamesByLike(term);
		return userNamesList;
	}
}
