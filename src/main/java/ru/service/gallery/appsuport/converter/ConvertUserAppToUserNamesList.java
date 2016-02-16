package ru.service.gallery.appsuport.converter;

import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.google.common.collect.Lists;

import ru.service.gallery.entity.UserApp;

public class ConvertUserAppToUserNamesList implements Converter<List<UserApp>, List<String>> {

	@Override
	public List<String> convert(List<UserApp> source) {
		List<String> list = Lists.newArrayList();
		for(UserApp userApp : source)list.add(userApp.getUserName());
		return list;
	}

}
