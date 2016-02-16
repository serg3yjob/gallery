package ru.service.gallery.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity @Table(name="MARK")
public class Mark {

	private long markId;
	private Image image;
	private UserInfo userInfo;
	
	@Id
	@SequenceGenerator(name="seqGenerator", sequenceName="MARK_ID_SEQ")
	@GeneratedValue(generator="seqGenerator", strategy=GenerationType.SEQUENCE)
	@Column(name="MARK_ID")
	public long getMarkId() {
		return markId;
	}
	public void setMarkId(long markId) {
		this.markId = markId;
	}
	@ManyToOne(optional = false, cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "IMAGE_ID")	
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	@ManyToOne(optional = false, cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_INFO_ID")		
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	@Override
	public String toString() {
		return "Mark [markId=" + markId + "]";
	}
}
