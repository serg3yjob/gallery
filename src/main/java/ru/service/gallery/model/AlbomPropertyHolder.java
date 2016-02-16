package ru.service.gallery.model;

import java.util.Date;

public class AlbomPropertyHolder {

	private String nameUserOwner;
	private long idUserOwner;
	private long idUserGuest;
	private long numberPage;
	private long numberNextPage;
	private long numberPreviousPage;
	private boolean emptyAlbom;
	private String titleImage;
	private Date dateUploadImage;
	private long imageId;
	private int totalMark;
	private boolean followUserGuest;
	private CommentsHolder commentsHolder;
	
	public AlbomPropertyHolder(String nameUserOwner, long idUserOwner, long idUserGuest, long numberPage, long numberNextPage,
			long numberPreviousPage, boolean empty, String titleImage, Date dateUploadImage, long imageId, int totalMark, boolean followUserGuest, CommentsHolder commentsHolder) {
		super();
		this.nameUserOwner = nameUserOwner;
		this.idUserOwner = idUserOwner;
		this.idUserGuest = idUserGuest;
		this.numberPage = numberPage;
		this.numberNextPage = numberNextPage;
		this.numberPreviousPage = numberPreviousPage;
		this.emptyAlbom = empty;
		this.titleImage = titleImage;
		this.dateUploadImage = dateUploadImage;
		this.imageId = imageId;
		this.totalMark = totalMark;
		this.followUserGuest = followUserGuest;
		this.commentsHolder = commentsHolder;
	}
	public AlbomPropertyHolder(String nameUserOwner, long idUserOwner, long idUserGuest, boolean empty) {
		this.nameUserOwner = nameUserOwner;
		this.idUserOwner = idUserOwner;
		this.idUserGuest = idUserGuest;
		this.emptyAlbom = empty;
	}

	public String getNameUserOwner() {
		return nameUserOwner;
	}
	public void setNameUserOwner(String nameUserOwner) {
		this.nameUserOwner = nameUserOwner;
	}
	public long getIdUserOwner() {
		return idUserOwner;
	}
	public void setIdUserOwner(long idUserOwner) {
		this.idUserOwner = idUserOwner;
	}
	public long getIdUserGuest() {
		return idUserGuest;
	}
	public void setIdUserGuest(long idUserGuest) {
		this.idUserGuest = idUserGuest;
	}
	public long getNumberNextPage() {
		return numberNextPage;
	}
	public void setNumberNextPage(long numberNextPage) {
		this.numberNextPage = numberNextPage;
	}
	public long getNumberPreviousPage() {
		return numberPreviousPage;
	}
	public void setNumberPreviousPage(long numberPreviousPage) {
		this.numberPreviousPage = numberPreviousPage;
	}
	public boolean isEmptyAlbom() {
		return emptyAlbom;
	}
	public void setEmptyAlbom(boolean emptyAlbom) {
		this.emptyAlbom = emptyAlbom;
	}
	public String getTitleImage() {
		return titleImage;
	}
	public void setTitleImage(String titleImage) {
		this.titleImage = titleImage;
	}
	public Date getDateUploadImage() {
		return dateUploadImage;
	}
	public void setDateUploadImage(Date dateUploadImage) {
		this.dateUploadImage = dateUploadImage;
	}
	public long getNumberPage() {
		return numberPage;
	}
	public void setNumberPage(long numberPage) {
		this.numberPage = numberPage;
	}
	public long getImageId() {
		return imageId;
	}
	public void setImageId(long imageId) {
		this.imageId = imageId;
	}
	public int getTotalMark() {
		return totalMark;
	}
	public void setTotalMark(int totalMark) {
		this.totalMark = totalMark;
	}
	public boolean isFollowUserGuest() {
		return followUserGuest;
	}
	public void setFollowUserGuest(boolean followUserGuest) {
		this.followUserGuest = followUserGuest;
	}
	public CommentsHolder getCommentsHolder() {
		return commentsHolder;
	}
	public void setCommentsHolder(CommentsHolder commentsHolder) {
		this.commentsHolder = commentsHolder;
	}
}
