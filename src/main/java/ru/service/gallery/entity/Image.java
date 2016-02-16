package ru.service.gallery.entity;

import java.util.Date;
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity @Table(name="IMAGE")
public class Image {

	private long imageId;
	private String title;
	private Date dateUpload;
	private byte[] content;
	private UserInfo userInfo;
	private Set<Comment> comments = new HashSet<>();
	private Set<Mark> marks = new HashSet<>();
	
	@Id
	@SequenceGenerator(name="seqGenerator", sequenceName="IMAGE_ID_SEQ")
	@GeneratedValue(generator="seqGenerator", strategy=GenerationType.SEQUENCE)
	@Column(name="IAMGE_ID")
	public long getImageId() {
		return imageId;
	}
	public void setImageId(long imageId) {
		this.imageId = imageId;
	}
	@Column(name="TITLE", nullable = true)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPLOAD", nullable = false)
	public Date getDateUpload() {
		return dateUpload;
	}
	public void setDateUpload(Date dateUpload) {
		this.dateUpload = dateUpload;
	}
	@Basic(fetch = FetchType.LAZY)
	@Type(type="org.hibernate.type.BinaryType")
	@Lob
	@Column(name="CONTENT", nullable = true)
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_INFO_ID")
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "image")
	public Set<Comment> getComments() {
		return comments;
	}
	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "image")
	public Set<Mark> getMarks() {
		return marks;
	}
	public void setMarks(Set<Mark> marks) {
		this.marks = marks;
	}
	@Override
	public String toString() {
		return "Image [imageId=" + imageId + ", title=" + title + ", dateUpload=" + dateUpload + "]";
	}
}
