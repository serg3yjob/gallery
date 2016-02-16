package ru.service.gallery.entity;

import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity @Table(name="USER_APP")
public class UserApp {

	private long userAppId;
	private String userName;
	private String userPassword;
	private boolean isEnabled;
	private boolean isBlocked;
	private Date regDate;
	private Set<UserGroup> userGroups = new HashSet<>();
	private UserInfo userInfo;
	
	public UserApp() {
		super();
	}
	public UserApp(String userName, String userPassword, Date regDate) {
		super();
		this.userName = userName;
		this.userPassword = userPassword;
		this.regDate = regDate;
	}

	@Id
	@SequenceGenerator(name="seqGenerator", sequenceName="USER_APP_ID_SEQ")
	@GeneratedValue(generator="seqGenerator", strategy=GenerationType.SEQUENCE)
	@Column(name="USER_APP_ID")
	public long getUserAppId() {
		return userAppId;
	}
	public void setUserAppId(long userAppId) {
		this.userAppId = userAppId;
	}
	@Column(name="USER_NAME", nullable = false, unique = true)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name="USER_PASSWORD", nullable = false)
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	@Column(name="ENABLED", columnDefinition = "boolean NOT NULL DEFAULT false")
	public boolean isEnabled() {
		return isEnabled;
	}
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	@Column(name="BLOCKED", columnDefinition = "boolean NOT NULL DEFAULT false")
	public boolean isBlocked() {
		return isBlocked;
	}
	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="REG_DATE")
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	@ManyToMany(fetch = FetchType.LAZY, cascade = {})
	@JoinTable(name="USER_APP_USER_GROUP", joinColumns=@JoinColumn(name="USER_APP_ID"), inverseJoinColumns=@JoinColumn(name="USER_GROUP_ID"))
	public Set<UserGroup> getUserGroups() {
		return userGroups;
	}
	public void setUserGroups(Set<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}
	@OneToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy="userApp")
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	@Override
	public String toString() {
		return "UserApp [userAppId=" + userAppId + ", userName=" + userName + ", userPassword=" + userPassword
				+ ", isEnabled=" + isEnabled + ", isBlocked=" + isBlocked + ", regDate=" + regDate + "]";
	}
}
