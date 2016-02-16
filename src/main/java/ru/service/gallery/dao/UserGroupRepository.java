package ru.service.gallery.dao;

import org.springframework.data.repository.CrudRepository;

import ru.service.gallery.entity.UserGroup;

public interface UserGroupRepository extends CrudRepository<UserGroup, Long> {

	public UserGroup findByGroupName(String groupName);
}
