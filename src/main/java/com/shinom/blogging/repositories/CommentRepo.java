package com.shinom.blogging.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shinom.blogging.entities.Comment;
import com.shinom.blogging.entities.Post;
import com.shinom.blogging.entities.User;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Integer> {

	List<Comment> findByUserAndPost(User user, Post post);
	
	List<Comment> findByPost(Post post);
	
	List<Comment> findByUser(User user);
	
	List<Comment> findByUserAndPostAndDeletedFalse(User user, Post post);
	
	List<Comment> findByPostAndDeletedFalse(Post post);
	
	List<Comment> findByUserAndDeletedFalse(User user);
}
