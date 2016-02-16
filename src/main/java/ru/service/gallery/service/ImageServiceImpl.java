package ru.service.gallery.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.service.gallery.dao.CommentRepository;
import ru.service.gallery.dao.ImageRepository;
import ru.service.gallery.entity.Attachment;
import ru.service.gallery.entity.Comment;
import ru.service.gallery.entity.Image;
import ru.service.gallery.entity.Mark;
import ru.service.gallery.entity.UserInfo;
import ru.service.gallery.model.AlbomPropertyHolder;
import ru.service.gallery.model.ButtonsOfPaginator;
import ru.service.gallery.model.CommentHolder;
import ru.service.gallery.model.CommentsHolder;
import ru.service.gallery.model.GridImagesHolder;

@Repository
@Service("imageService")
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	private UserAppService userAppService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private CommentRepository commentRepository;
	
	@Override
	@Transactional(readOnly = true)
	public long countImageByUserName(String userName) {
		return imageRepository.countImageByUserName(userName);
	}
	@Override
	@Transactional(readOnly = true)
	public long countImageByUserInfoId(long userInfoId) {
		return imageRepository.countImageByUserInfoId(userInfoId);
	}
	@Override
	@Transactional(readOnly = true)
	public AlbomPropertyHolder getAlbomHolder(String principalName, Long userId, Long numberPage) {
		long idUserGuest = userAppService.userInfoIdByUserName(principalName);
		if(userId == null) userId = idUserGuest;
		if(numberPage == null) numberPage = 1l;
		long countPage = countImageByUserInfoId(userId);
		UserInfo userInfoOwner = userInfoService.findUserInfoByIdWithUserApp(userId);
		String nameUserOwner = userInfoOwner.getUserApp().getUserName();
		AlbomPropertyHolder holder;
		if(countPage > 0){
			if(numberPage <= 0 || numberPage > countPage) numberPage = 1l;
			long nextPage, previousPage;
			nextPage = numberPage + 1;
			nextPage = nextPage <= countPage ? nextPage : 1;
			previousPage = numberPage - 1;
			previousPage = previousPage > 0 ? previousPage : countPage;
			PageRequest pageRequest = new PageRequest(numberPage.intValue() - 1, 1, new Sort(Direction.DESC, "dateUpload"));
			Page<Image> pageImage = imageRepository.findByUserInfo(userInfoOwner, pageRequest);
			Image image = pageImage.getContent().get(0);
			int totalMark = image.getMarks().size() * 5;
			boolean isFollowUserGuest = false;
			for(UserInfo user : userInfoOwner.getSubscribers())if(user.getUserInfoId() == idUserGuest)isFollowUserGuest = true;
			holder = new AlbomPropertyHolder(
					nameUserOwner, userId, idUserGuest, numberPage, nextPage, previousPage, false,
					image.getTitle(), image.getDateUpload(), image.getImageId(), totalMark, isFollowUserGuest, getCommentsHolder(image.getComments()));
		}else holder = new AlbomPropertyHolder(nameUserOwner, userId, idUserGuest, true);
		return holder;
	}
	@Override
	@Transactional(readOnly = true)
	public byte[] contentImage(long imageId) {
		Image image = imageRepository.findOne(imageId);
		return image.getContent();
	}
	@Override
	@Transactional
	public void setTitleImg(String titleImg, Long imgId) {
		Image image = imageRepository.findOne(imgId);	
		image.setTitle(titleImg);
		imageRepository.save(image);
	}
	@Override
	@Transactional(readOnly = true)
	public int totalMarks(long imgId) {
		Image img = imageRepository.findByIdWithMark(imgId);
		return img.getMarks().size() * 5;
	}
	@Override
	@Transactional
	public String markImage(long idUserGuest, long idImg) {
		Image image = imageRepository.findOne(idImg);
		for(Mark mark : image.getMarks())
			if(mark.getUserInfo().getUserInfoId() == idUserGuest) return "false";
		UserInfo userInfo = userInfoService.findOne(idUserGuest);
		Mark newMark = new Mark();
		newMark.setImage(image);
		newMark.setUserInfo(userInfo);
		image.getMarks().add(newMark);
		imageRepository.save(image);
		return String.valueOf(image.getMarks().size() * 5);
	}
	@Override
	@Transactional
	public String followAlbom(long idUserGuest, long idUserOwner) {
		UserInfo userOwner = userInfoService.findOne(idUserOwner);
		Set<UserInfo> subscribers = userOwner.getSubscribers();
		for(UserInfo user : subscribers)if(user.getUserInfoId() == idUserGuest)return "ok";
		UserInfo userGuest = userInfoService.findOne(idUserGuest);
		subscribers.add(userGuest);
		Set<UserInfo> idols = userGuest.getIdols();
		idols.add(userOwner);
		userInfoService.save(userGuest);
		userInfoService.save(userOwner);
		return "ok";
	}
	@Override
	@Transactional
	public boolean comment(String commentText, Part file, long userGuestId, Long imageId, Long parentCommentId) {
		Comment comment_ = new Comment(commentText);
		comment_.setDate(new Date());
		UserInfo userInfo = userInfoService.findOne(userGuestId);
		comment_.setUserInfo(userInfo);

		try{
			if(file != null && file.getInputStream().available() > 0){
				byte[] fileBytes;
				InputStream inputStream = file.getInputStream();
				int avalabelBytes = inputStream.available();
				fileBytes = new byte[avalabelBytes];
				inputStream.read(fileBytes);
				if(inputStream != null)inputStream.close();
				Attachment attachment = new Attachment(file.getContentType());
				attachment.setContent(fileBytes);
				comment_.setAttachment(attachment);
			}
		}catch(IOException e){
			return false;
		}
		if(imageId != null){
			Image image = imageRepository.findOne(imageId);
			image.getComments().add(comment_);
			comment_.setImage(image);
			imageRepository.save(image);
			return true;
		}
		if(parentCommentId != null){
			Comment parent = commentRepository.findOne(parentCommentId);
			parent.getAnswers().add(comment_);
			comment_.setComment(parent);
			commentRepository.save(parent);
			return true;
		}
		return false;
	}
	@Override
	public CommentsHolder getCommentsHolder(Set<Comment> setComments) {
		if(setComments == null || setComments.size() == 0)return new CommentsHolder(true);
		List<CommentHolder> listCommentHolder = new ArrayList<>();
		List<Comment> list = getOrderedListBySetComments(setComments);
		for(Comment comment : list){
			String text = comment.getText();
			UserInfo userInfo = comment.getUserInfo();
			String userName = null;
			if(userInfo != null)userName = userInfo.getUserApp().getUserName();
			Date date = comment.getDate();
			int layer = 0;
			long id = comment.getCommentId();
			int parentId = 0;
			boolean existAttach = false;
			long attachId = 0;
			Attachment attach = comment.getAttachment();
			if( attach != null){
				existAttach = true;
				attachId = attach.getAttachmentId();
			}
			listCommentHolder.add(new CommentHolder(text, userName, date, layer, id, parentId, existAttach, attachId));
			Set<Comment> answers = comment.getAnswers();
			if(answers != null && answers.size() > 0)
				proc(answers, listCommentHolder, 1);
		}
		return new CommentsHolder(false, listCommentHolder);
	}
	private void proc(Set<Comment> setComments, List<CommentHolder> listCommentHolder, int layer) {
		List<Comment> list = getOrderedListBySetComments(setComments);
		for(Comment comment : list){
			String text = comment.getText();
			UserInfo userInfo = comment.getUserInfo();
			String userName = null;
			if(userInfo != null)userName = userInfo.getUserApp().getUserName();
			Date date = comment.getDate();
			long id = comment.getCommentId();
			long parentId = comment.getComment().getCommentId();
			boolean existAttach = false;
			long attachId = 0;
			Attachment attach = comment.getAttachment();
			if( attach != null){
				existAttach = true;
				attachId = attach.getAttachmentId();
			}
			listCommentHolder.add(new CommentHolder(text, userName, date, layer, id, parentId, existAttach, attachId));
			Set<Comment> answers = comment.getAnswers();
			if(answers != null && answers.size() > 0)
				proc(answers, listCommentHolder, layer + 1);
		}
	}
	private List<Comment> getOrderedListBySetComments(Set<Comment> setComments) {
		List<Comment> list = new ArrayList<>(setComments);
		Collections.sort(list);
		return list;
	}
	@Override
	@Transactional(readOnly = true)
	public GridImagesHolder getGridImagesHolder(Integer numberPage_, ButtonsOfPaginator button) {
		final int PER_PAGE = 8;
		long countImage = imageRepository.count();
		if(countImage == 0)return new GridImagesHolder(true);
		int numberPage = 0;
		if(numberPage_ == null || numberPage_ < 1 || numberPage_ > countImage) numberPage = 1;
		else numberPage = numberPage_.intValue();
		int countPages =  (int) Math.ceil(countImage/PER_PAGE);
		boolean emptyGrid = false;
		PageRequest pageRequest = new PageRequest(numberPage - 1, PER_PAGE, new Sort(Direction.DESC, "dateUpload"));
		Page<Image> pageImage = imageRepository.findAll(pageRequest);
		List<Image> images = pageImage.getContent();
		int leftDigit = 0; int middleDigit = 0; int rightDigit = 0;
		int firstPage = 0; int prevPage = 0; int lastPage = 0; int nextPage = 0;
		boolean presentsMiddleDigit = true; boolean presentsRightDigit = true;
		boolean blockedFirst = false; boolean blockedLast = false; boolean blockedNext = false; boolean blockedPrev = false;
		boolean activeLleftDigit = false; boolean activeMiddleDigit = false; boolean activeRightDigit = false; 
		switch(button) {
			case LEFT: {
				if(numberPage == 1){
					blockedFirst = true;
					blockedPrev = true;
					if(countPages == 1){
						blockedLast = true;
						blockedNext = true;
					}
					leftDigit = 1;
					if(countPages > 1) middleDigit = 2;
					else presentsMiddleDigit = false;
					if(countPages > 2) rightDigit = 3;
					else presentsRightDigit = false;
					activeLleftDigit = true;
				}else {
					leftDigit = numberPage - 1;
					if(countPages >= leftDigit + 1) middleDigit = leftDigit + 1;
					else presentsMiddleDigit = false;
					if(countPages >= leftDigit + 2) rightDigit = leftDigit + 2;
					else presentsRightDigit = false;
					activeMiddleDigit = true;
					if(countPages == 2){
						blockedLast = true;
						blockedNext = true;
					}
				}
			break;}
			case MIDDLE: {
				leftDigit = numberPage - 1;
				middleDigit = numberPage;
				rightDigit = numberPage + 1;
				activeMiddleDigit = true;
			break;}
			case RIGHT: {
				if(numberPage == countPages){
					blockedLast = true;
					blockedNext = true;
					rightDigit = countPages;
					middleDigit = countPages - 1;
					leftDigit = countPages - 2;
					activeRightDigit = true;
				}else {
					rightDigit = numberPage + 1;
					middleDigit = rightDigit - 1;
					leftDigit = rightDigit - 2;
					activeMiddleDigit = true;
				}
			break;}
			case FIRST: {
				blockedFirst = true;
				blockedPrev = true;
				leftDigit = 1;
				middleDigit = 2;
				if(countPages > 2) rightDigit = 3;
				else presentsRightDigit = false;
				activeLleftDigit = true;
			break;}
			case PREVIOUS: {
				if(numberPage == 1){
					blockedFirst = true;
					blockedPrev = true;
					leftDigit = 1;
					middleDigit = 2;
					if(countPages > 2) rightDigit = 3;
					else presentsRightDigit = false;
					activeLleftDigit = true;
				}else {
					leftDigit = numberPage - 1;
					middleDigit = leftDigit + 1;
					if(countPages >= leftDigit + 2) rightDigit = leftDigit + 2;
					else presentsRightDigit = false;
					activeMiddleDigit = true;
				}
			break;}
			case LAST: {
				blockedLast = true;
				blockedNext = true;
				if(countPages >= 3){
					rightDigit = countPages;
					middleDigit = rightDigit - 1;
					leftDigit = rightDigit - 2;
				}else {
					if(countPages == 2){
						leftDigit = 1;
						middleDigit = 2;
						presentsRightDigit = false;
					}
				}
				activeRightDigit = true;
			break;}
			case NEXT: {
				if(numberPage == countPages){
					blockedLast = true;
					blockedNext = true;
					rightDigit = countPages;
					middleDigit = countPages - 1;
					leftDigit = countPages - 2;
					activeRightDigit = true;
				}else {
					leftDigit = numberPage - 1;
					middleDigit = leftDigit + 1;
					if(countPages >= leftDigit + 2) rightDigit = leftDigit + 2;
					else presentsRightDigit = false;
					activeMiddleDigit = true;
					}
			break;}
		}
		firstPage = 1;
		prevPage = numberPage - 1 > 0 ? numberPage - 1 : 1;
		nextPage = numberPage + 1 <= countPages ? numberPage + 1 : countPages;
		lastPage = countPages;
		return new GridImagesHolder(emptyGrid, images, leftDigit, middleDigit, rightDigit, firstPage, prevPage, lastPage, nextPage, presentsMiddleDigit, presentsRightDigit, blockedFirst, blockedLast, blockedNext, blockedPrev, activeLleftDigit, activeMiddleDigit, activeRightDigit);
	}
	@Override
	@Transactional(readOnly = true)
	public byte[] imageContent(Long imgId) {
		return imageRepository.findOne(imgId).getContent();
	}
}
