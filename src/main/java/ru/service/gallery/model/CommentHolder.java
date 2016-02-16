package ru.service.gallery.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommentHolder {

	private String text;
	private String userName;
	private Date date;
	private long parentId;
	private boolean existAttach;
	private long attachId;
	private int layer;
	private long id;
	
	public CommentHolder(String text, String userName, Date date, int layer, long id, long parentId) {
		super();
		this.text = text;
		this.userName = userName;
		this.date = date;
		this.id = id;
		this.parentId = parentId;
		this.layer = layer;
	}
	public CommentHolder(String text, String userName, Date date, int layer,  long id, long parentId, boolean existAttach, long attachId) {
		super();
		this.text = text;
		this.userName = userName;
		this.date = date;
		this.layer = layer;
		this.id = id;
		this.parentId = parentId;
		this.existAttach = existAttach;
		this.attachId = attachId;
	}
	
	public int getLayer() {
		return layer;
	}
	public void setLayer(int layerId) {
		this.layer = layerId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public boolean isExistAttach() {
		return existAttach;
	}
	public void setExistAttach(boolean existAttach) {
		this.existAttach = existAttach;
	}
	public long getAttachId() {
		return attachId;
	}
	public void setAttachId(long attachId) {
		this.attachId = attachId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		SimpleDateFormat dfrm = new SimpleDateFormat("mm:ss:S");
		return "CommentHolder [text=" + text + ", layer=" + layer  + ", parentId=" + parentId + ", date=" + dfrm.format(date) + ", userName=" + userName
				+ ", existAttach=" + existAttach + ", attachId=" + attachId + "]";
	}
}
