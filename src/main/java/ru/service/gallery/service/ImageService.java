package ru.service.gallery.service;

import java.util.Set;

import javax.servlet.http.Part;

import ru.service.gallery.entity.Comment;
import ru.service.gallery.model.AlbomPropertyHolder;
import ru.service.gallery.model.ButtonsOfPaginator;
import ru.service.gallery.model.CommentsHolder;
import ru.service.gallery.model.GridImagesHolder;

public interface ImageService {

	public long countImageByUserName(String userName);
	public long countImageByUserInfoId(long userInfoId);
	public AlbomPropertyHolder getAlbomHolder(String principalName, Long userId, Long numberPage);
	public byte[] contentImage(long imageId);
	public void setTitleImg(String titleImg, Long imgId);
	public int totalMarks(long imgId);
	public String markImage(long idUserGuest, long idImg);
	public String followAlbom(long idUserGuest, long idUserOwner);
	public boolean comment(String comment, Part file, long userGuestId, Long imageId, Long parentCommentId);
	public CommentsHolder getCommentsHolder(Set<Comment> setComments);
	public GridImagesHolder getGridImagesHolder(Integer numberPage, ButtonsOfPaginator button);
	public byte[] imageContent(Long imgId);
}
