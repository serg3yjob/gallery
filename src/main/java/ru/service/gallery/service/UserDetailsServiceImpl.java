package ru.service.gallery.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.google.common.collect.Lists;

import ru.service.gallery.appsuport.exception.LoginUserBlockedException;
import ru.service.gallery.appsuport.exception.LoginUserDisabledException;
import ru.service.gallery.entity.UserApp;
import ru.service.gallery.entity.UserGroup;
import ru.service.gallery.entity.UserRole;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserAppService userAppService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails user = null;	
		UserApp userApp = userAppService.findByUserNameWithGroup(username);
		if(userApp != null){
			if(!userApp.isBlocked() && userApp.isEnabled()) {
				List<UserGroup> groups = Lists.newArrayList(userApp.getUserGroups());
				List<GrantedAuthority> granteds = new ArrayList<GrantedAuthority>();
				for(UserGroup group : groups){					
					for(UserRole role : group.getUserRoles()){
						GrantedAuthority granted = new SimpleGrantedAuthority(role.getRoleName());
						granteds.add(granted);
					}
				}				
				user = new User(userApp.getUserName(), userApp.getUserPassword(), granteds);
			}else {
				if(!userApp.isEnabled())throw new LoginUserDisabledException("User with name " + userApp.getUserName() + " is disabeled");
				if(userApp.isBlocked()) throw new LoginUserBlockedException("User with name " + userApp.getUserName() + " is blocked");
			}
		}else 
			throw new UsernameNotFoundException("Not find user by name \"" + username + "\"");
		return user;
	}
}
