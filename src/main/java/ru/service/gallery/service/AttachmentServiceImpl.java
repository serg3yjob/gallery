package ru.service.gallery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.service.gallery.dao.AttachmentRepository;
import ru.service.gallery.entity.Attachment;

@Repository
@Service("attachmentService")
public class AttachmentServiceImpl implements AttachmentService {

	@Autowired
	private AttachmentRepository attachmentRepository;

	@Override
	@Transactional(readOnly = true)
	public Attachment attachment(long attachmentId) {
		return attachmentRepository.findOne(attachmentId);
	}
	
}
