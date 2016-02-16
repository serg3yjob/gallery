package ru.service.gallery.service;

import ru.service.gallery.entity.UserInfo;

public interface CommonService {

	public byte[] getAvatarByUserInfo(UserInfo userInfo);
}
