package ru.service.gallery.dao;

import org.springframework.data.repository.CrudRepository;

import ru.service.gallery.entity.UserRole;

public interface UserRoleRepository extends CrudRepository<UserRole, Long> {

	public UserRole findByRoleName(String roleName);
}
