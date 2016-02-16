package ru.service.gallery.model;

import java.util.List;

public class CommentsHolder {

	private boolean emptyComment;
	private List<CommentHolder> comments;
	
	public CommentsHolder(boolean empty) {
		super();
		this.emptyComment = empty;
	}
	public CommentsHolder(boolean empty, List<CommentHolder> comments) {
		super();
		this.emptyComment = empty;
		this.comments = comments;
	}
	
	public boolean isemptyComment() {
		return emptyComment;
	}
	public void setEmptyComment(boolean empty) {
		this.emptyComment = empty;
	}
	public List<CommentHolder> getComments() {
		return comments;
	}
	public void setComments(List<CommentHolder> comments) {
		this.comments = comments;
	}
}
