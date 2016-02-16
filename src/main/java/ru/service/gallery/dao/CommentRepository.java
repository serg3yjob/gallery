package ru.service.gallery.dao;

import org.springframework.data.repository.CrudRepository;

import ru.service.gallery.entity.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {

}
