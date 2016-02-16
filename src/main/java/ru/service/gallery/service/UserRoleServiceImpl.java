package ru.service.gallery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.service.gallery.dao.UserRoleRepository;
import ru.service.gallery.entity.UserRole;

@Repository
@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Transactional(readOnly = true)
	@Override
	public UserRole findByRoleName(String roleName) {
		return userRoleRepository.findByRoleName(roleName);
	}

}
