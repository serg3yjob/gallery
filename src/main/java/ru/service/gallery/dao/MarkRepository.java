package ru.service.gallery.dao;

import org.springframework.data.repository.CrudRepository;

import ru.service.gallery.entity.Mark;

public interface MarkRepository extends CrudRepository<Mark, Long> {

}
