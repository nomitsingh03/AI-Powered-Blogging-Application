package com.shinom.blogging.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shinom.blogging.entities.Category;
import com.shinom.blogging.entities.Post;
import com.shinom.blogging.entities.User;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {

	List<Post> findByUser(User user);
	
	List<Post> findByCategory(Category category);
	
	List<Post> findByTitleContainingAllIgnoreCase(String title);
	
List<Post> findByUserAndDeletedFalseAndArchiveFalse(User user);
List<Post> findByUserAndDeletedAndArchive(User user, boolean deleted, boolean archive);
//List<Post> findByUserAndArchiveTrueAndDeletedFalse(User user);
	
	List<Post> findByCategoryAndDeletedAndArchive(Category category, boolean deleted, boolean archive);
	
	Page<Post> findByDeletedFalseAndArchiveFalse(Pageable pageable);
	
	Page<Post> findByDeletedTrue(Pageable pageable);
	
	Page<Post> findByDeletedFalseAndArchiveTrue(Pageable pageable);
	
	List<Post>findByUserAndDeletedFalseAndArchiveTrue(User user);

	Page<Post> findByCategoryAndDeletedAndArchive(Category category, boolean b, boolean c, Pageable pageable);

	List<Post> findByUserAndDeletedFalse(User user);
	
	List<Post> findByCategoryAndArchive(Category category, boolean archive, Sort sort);
	
	List<Post> findByArchive(boolean archive, Sort sort);
}
