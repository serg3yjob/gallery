package ru.service.gallery.dao;

import org.springframework.data.repository.CrudRepository;

import ru.service.gallery.entity.Attachment;

public interface AttachmentRepository extends CrudRepository<Attachment, Long> {

}
