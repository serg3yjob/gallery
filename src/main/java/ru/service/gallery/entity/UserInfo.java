package ru.service.gallery.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity @Table(name="USER_INFO")
public class UserInfo {

	private long userInfoId;
	private String email;
	private byte[] avatar;
	private UserApp userApp;
	private Set<Image> images = new HashSet<>();;
	private Set<UserInfo> subscribers = new HashSet<>();;
	private Set<UserInfo> idols = new HashSet<>();;
	
	public UserInfo() {
		super();
	}
	public UserInfo(String email) {
		super();
		this.email = email;
	}

	@Id
	@SequenceGenerator(name="seqGenerator", sequenceName="USER_INFO_ID_SEQ")
	@GeneratedValue(generator="seqGenerator", strategy=GenerationType.SEQUENCE)
	@Column(name="USER_INFO_ID")
	public long getUserInfoId() {
		return userInfoId;
	}
	public void setUserInfoId(long userInfoId) {
		this.userInfoId = userInfoId;
	}
	@Column(name="EMAIL", nullable = false, unique = true)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Basic(fetch = FetchType.LAZY)
	@Lob
	@Column(name="AVATAR", nullable = true)
	@Type(type="org.hibernate.type.BinaryType")
	public byte[] getAvatar() {
		return avatar;
	}
	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}
	@OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, optional = false)
	@JoinColumn(name = "USER_APP_ID")
	public UserApp getUserApp() {
		return userApp;
	}
	public void setUserApp(UserApp userApp) {
		this.userApp = userApp;
	}
	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "userInfo")
	public Set<Image> getImages() {
		return images;
	}
	public void setImages(Set<Image> images) {
		this.images = images;
	}
	@ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinTable(name = "USER_SUBSCRIBER", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "SUBSCRIBER_ID"))
	public Set<UserInfo> getSubscribers() {
		return subscribers;
	}
	public void setSubscribers(Set<UserInfo> subscribers) {
		this.subscribers = subscribers;
	}
	@ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "subscribers")
	public Set<UserInfo> getIdols() {
		return idols;
	}
	public void setIdols(Set<UserInfo> idols) {
		this.idols = idols;
	}
	@Override
	public String toString() {
		return "UserInfo [userInfoId=" + userInfoId + ", email=" + email + "]";
	}
}
