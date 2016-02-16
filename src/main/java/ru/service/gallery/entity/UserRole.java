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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity @Table(name="USER_ROLE")
public class UserRole {

	private long userRoleId;
	private String roleName;
	private Set<UserGroup> userGroups = new HashSet<>();
	
	public UserRole() {
		super();
	}
	public UserRole(String roleName) {
		super();
		this.roleName = roleName;
	}
	@Id
	@SequenceGenerator(name="seqGenerator", sequenceName="USER_ROLE_ID_SEQ")
	@GeneratedValue(generator="seqGenerator", strategy=GenerationType.SEQUENCE)
	@Column(name="USER_ROLE_ID")
	public long getUserRoleId() {
		return userRoleId;
	}
	public void setUserRoleId(long userRoleId) {
		this.userRoleId = userRoleId;
	}
	@Column(name="ROLE_NAME", nullable = false, unique = true)
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	@ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.EAGER)
	@JoinTable(name="USER_ROLE_USER_GROUP", joinColumns=@JoinColumn(name="USER_ROLE_ID"), inverseJoinColumns=@JoinColumn(name="USER_GROUP_ID"))
	public Set<UserGroup> getUserGroups() {
		return userGroups;
	}
	public void setUserGroups(Set<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}
	@Override
	public String toString() {
		return "UserRole [userRoleId=" + userRoleId + ", roleName=" + roleName + "]";
	}
}
