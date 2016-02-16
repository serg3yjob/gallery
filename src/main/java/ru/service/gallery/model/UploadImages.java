package ru.service.gallery.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class UploadImages {

	private List<MultipartFile> images;

	public List<MultipartFile> getImages() {
		return images;
	}
	public void setImages(List<MultipartFile> images) {
		this.images = images;
	}
	
}
