package ru.service.gallery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.service.gallery.dao.UserGroupRepository;
import ru.service.gallery.entity.UserGroup;

@Repository
@Service("userGroupService")
public class UserGroupServiceImpl implements UserGroupService {

	@Autowired
	private UserGroupRepository userGroupRepository;
	
	@Transactional
	@Override
	public long save(UserGroup userGroup) {
		return userGroupRepository.save(userGroup).getUserGroupId();
	}
	@Transactional(readOnly = true)
	@Override
	public UserGroup findByGroupName(String groupName) {
		return userGroupRepository.findByGroupName(groupName);
	}

}
