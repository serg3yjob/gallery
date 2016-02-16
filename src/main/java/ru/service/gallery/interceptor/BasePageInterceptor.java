package ru.service.gallery.interceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import ru.service.gallery.entity.UserApp;
import ru.service.gallery.model.UserRegistration;
import ru.service.gallery.service.UserAppService;

public class BasePageInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private UserAppService userAppService;
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
		if(modelAndView != null){
//			Для задания классов CSS пунктам левого меню
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
			modelAndView.addObject("mapLeftMenuCSS", mapLeftMenuCSS);
//			Для вывода списка пользователей в левом меню
			List<UserApp> userApps = userAppService.allUsersWithUserInfo();
			modelAndView.addObject("usersByLeftMenu", userApps);
//			Для того чтобы не было исключения при загрузки страниц где не используется "userRegistration"
			modelAndView.addObject("userRegistration", UserRegistration.getEmptyUserRegistration());
		}
	}
}
