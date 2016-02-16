package ru.service.gallery.service;

import ru.service.gallery.entity.UserRole;

public interface UserRoleService {

	public UserRole findByRoleName(String roleName);
}
