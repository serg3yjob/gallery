package ru.service.gallery.controller;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.service.gallery.entity.Attachment;
import ru.service.gallery.model.UploadImages;
import ru.service.gallery.service.AttachmentService;
import ru.service.gallery.service.ImageService;
import ru.service.gallery.service.UserInfoService;

@Controller("albomController")
public class AlbomController {

	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private ImageService imageService;
	@Autowired
	private AttachmentService attachmentService; 
	
	@RequestMapping(value = "/pages/albom", method = RequestMethod.GET)
	public String albomPage(Model model, Principal principal,
			@RequestParam(value = "userId", required = false) Long userId,
			@RequestParam(value = "numberPage", required = false) Long numberPage,
			@RequestParam(value = "titleImage", required = false) String titleImage,
			@RequestParam(value = "imgId", required = false) Long imgId){
		if(titleImage != null && imgId != null)imageService.setTitleImg(titleImage, imgId);
		model.addAttribute("albomHolder", imageService.getAlbomHolder(principal.getName(), userId, numberPage));
		model.addAttribute("uploadImages", new UploadImages());
		return "gallery/albom";
	}
	@RequestMapping(value = "/albom/upload", method = RequestMethod.POST)
	public String uploadImage(@ModelAttribute("uploadImages") UploadImages uploadImages, Principal principal) {
		try{
			userInfoService.saveUserImages(principal.getName(), uploadImages.getImages());
		}catch(IOException e){
			return "redirect:/pages/error";
		}
		return "redirect:/pages/albom";
	}
	@RequestMapping(value = "/albom/image/{imageId}", method = RequestMethod.GET)
	@ResponseBody
	public byte[] getAlbomImage(@PathVariable("imageId") long imageId) {
		return imageService.contentImage(imageId);
	}
	@RequestMapping(value = "/albom/mark/{idUserOwner}/{idUserGuest}/{idImg}", method = RequestMethod.GET, produces = "text/plain")
	@ResponseBody
	public String markImage(@PathVariable("idUserOwner") long idUserOwner, @PathVariable("idUserGuest") long idUserGuest, @PathVariable("idImg") long idImg) {
		return imageService.markImage(idUserGuest, idImg);
	}
	@RequestMapping(value = "/albom/follow/{idUserOwner}/{idUserGuest}", method = RequestMethod.GET, produces = "text/plain")
	@ResponseBody
	public String followAlbom(@PathVariable("idUserOwner") long idUserOwner, @PathVariable("idUserGuest") long idUserGuest) {
		return imageService.followAlbom(idUserGuest, idUserOwner);
	}
	@RequestMapping(value = "/albom/comment", method = RequestMethod.POST)
	public String comment(@RequestParam(value="numberPage", required = true) long numberPage,
			@RequestParam(value="userId", required = true) long userId,
			@RequestParam(value="userGuestId", required = true) long userGuestId,
			@RequestParam(value="comment", required = true) String comment,
			@RequestParam(value="imageId", required = false) Long imageId,
			@RequestParam(value = "commentFile", required = false) Part file,
			@RequestParam(value="parentCommentId", required = false) Long parentCommentId) {
		if(imageId == null && parentCommentId == null) return "redirect:/pages/error";
		if(imageService.comment(comment, file, userGuestId, imageId, parentCommentId))
			return "redirect:/pages/albom?userId=" + userId + "&numberPage=" + numberPage;
		else return "redirect:/pages/error";
	}
	@RequestMapping(value = "/albom/attach/{id}", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<Byte[]> attach(@PathVariable("id") long id, HttpServletResponse response) {
		Attachment attachment =  attachmentService.attachment(id);
		String contentType = attachment.getContentType();
		contentType = (contentType == null ? "application/octet-stream" : contentType);
//		response.setContentType(contentType); //Не работает
//		response.setHeader("Content-Type", contentType); //Не работает
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf(contentType));
		Byte[] bytes = ArrayUtils.toObject(attachment.getContent());
		return new HttpEntity<Byte[]>(bytes, headers);
	}
}
