package ru.service.gallery.service;

import ru.service.gallery.entity.UserGroup;

public interface UserGroupService {

	public long save(UserGroup userGroup);
	public UserGroup findByGroupName(String groupName);
}
