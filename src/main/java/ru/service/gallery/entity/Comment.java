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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity @Table(name="COMMENT")
public class Comment implements Comparable<Comment>{

	private long commentId;
	private String text;
	private Date date;
	private Comment comment;
	private Set<Comment> answers = new HashSet<>();
	private Image image;
	private UserInfo userInfo;
	private Attachment attachment;
	
	public Comment() {
		super();
	}
	public Comment(String text) {
		super();
		this.text = text;
	}
	
	@Id
	@SequenceGenerator(name="seqGenerator", sequenceName="COMMENT_ID_SEQ")
	@GeneratedValue(generator="seqGenerator", strategy=GenerationType.SEQUENCE)
	@Column(name="COMMENT_ID")
	public long getCommentId() {
		return commentId;
	}
	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}
	@Column(name="TEXT", nullable = false)
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE", nullable = false)
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@ManyToOne(optional = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_COMMENT_ID")
	public Comment getComment() {
		return comment;
	}
	public void setComment(Comment comment) {
		this.comment = comment;
	}
	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "comment")
	public Set<Comment> getAnswers() {
		return answers;
	}
	public void setAnswers(Set<Comment> answers) {
		this.answers = answers;
	}
	@ManyToOne(optional = true, cascade = {}, fetch = FetchType.LAZY)
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
	@OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "ATTACHMENT_ID")
	public Attachment getAttachment() {
		return attachment;
	}
	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}
	@Override
	public String toString() {
		return "Comment [commentId=" + commentId + ", text=" + text + "]";
	}
	@Override
	public int compareTo(Comment o) {
		if(this.date.after(o.date)) return 1;
		if(this.date.before(o.date)) return -1;
		return 0;
	}
}
