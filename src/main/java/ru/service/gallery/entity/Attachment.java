package ru.service.gallery.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity @Table(name="ATTACHMENT")
public class Attachment {

	private long attachmentId;
	private byte[] content;
	private String contentType;
	
	public Attachment() {
		
	}
	public Attachment(String contentType) {
		super();
		this.contentType = contentType;
	}
	
	@Id
	@SequenceGenerator(name="seqGenerator", sequenceName="ATTACHMENT_ID_SEQ")
	@GeneratedValue(generator="seqGenerator", strategy=GenerationType.SEQUENCE)
	@Column(name="ATTACHMENT_ID")
	public long getAttachmentId() {
		return attachmentId;
	}
	public void setAttachmentId(long attachmentId) {
		this.attachmentId = attachmentId;
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
	@Override
	public String toString() {
		return "Attachment [attachmentId=" + attachmentId + "]";
	}
	@Column(name = "CONTENT_TYPE", nullable = true)
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}
