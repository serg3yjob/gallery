package ru.service.gallery.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity @Table(name="USER_GROUP")
public class UserGroup {

	private long userGroupId;
	private String groupName;
	private Set<UserRole> userRoles = new HashSet<>();
	private Set<UserApp> userApps = new HashSet<>();
	
	public UserGroup() {
		super();
	}
	
	public UserGroup(String groupName) {
		super();
		this.groupName = groupName;
	}

	@Id
	@SequenceGenerator(name="seqGenerator", sequenceName="USER_GROUP_ID_SEQ")
	@GeneratedValue(generator="seqGenerator", strategy=GenerationType.SEQUENCE)
	@Column(name="USER_GROUP_ID")
	public long getUserGroupId() {
		return userGroupId;
	}
	public void setUserGroupId(long userGroupId) {
		this.userGroupId = userGroupId;
	}
	@Column(name="GROUP_NAME", nullable =  false, unique = true)
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, mappedBy = "userGroups")
	public Set<UserRole> getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
	@ManyToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "userGroups")
	public Set<UserApp> getUserApps() {
		return userApps;
	}
	public void setUserApps(Set<UserApp> userApps) {
		this.userApps = userApps;
	}
	@Override
	public String toString() {
		return "UserGroup [userGroupId=" + userGroupId + ", groupName=" + groupName + "]";
	}
}
