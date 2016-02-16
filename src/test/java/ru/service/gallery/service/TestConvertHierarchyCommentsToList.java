package ru.service.gallery.service;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ru.service.gallery.entity.Comment;
import ru.service.gallery.model.CommentHolder;
import ru.service.gallery.model.CommentsHolder;

public class TestConvertHierarchyCommentsToList {

	private Set<Comment> setComment = null;
	
	@Before
	public void prepareTestData() {
		setComment = new HashSet<>();
		int m = 0;
		for(int i = 0; i < 3; i++){
			Comment comment0 = new Comment("Comment " + i + ", Layer " + 0);
			comment0.setDate(new Date( new Date().getTime() + i));
			comment0.setCommentId(++m);
			Set<Comment> answers1 = new HashSet<>();
			for(int j = 0; j < 3; j++){
				Comment comment1 = new Comment("Comment " + j + ", Layer " + 1);
				comment1.setCommentId(++m);
				comment1.setDate(new Date( new Date().getTime() + j));
				comment1.setComment(comment0);
				answers1.add(comment1);
				Set<Comment> answers2 = new HashSet<>();
				for(int k = 0; k < 3; k++){
					Comment comment2 = new Comment("Comment " + k + ", Layer " + 2);
					comment2.setDate(new Date( new Date().getTime() + k));
					comment2.setComment(comment1);
					answers2.add(comment2);
				}
				comment1.setAnswers(answers2);
			}
			comment0.setAnswers(answers1);
			setComment.add(comment0);
		}
	}
	@Test
	public void test() {
		ImageService imageService = new ImageServiceImpl();
		CommentsHolder commentsHolder = imageService.getCommentsHolder(setComment);
		for(CommentHolder holder : commentsHolder.getComments()){
			for(int i = 0; i <= holder.getLayer(); i++)System.out.print("->");
			System.out.println(holder);
		}
		System.out.println(commentsHolder.getComments().size());
	}

}
