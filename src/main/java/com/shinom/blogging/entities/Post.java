package com.shinom.blogging.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="post")
@Data
@NoArgsConstructor
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	int postId;
	
	@Column(name="post_title", nullable = false, length=100)
	private String title;
	
	@Column(length=10000)
	private String content;
	
	private List<String> images;
	
	private LocalDate creationDate;
	
	private LocalTime creationTime;
	
	@UpdateTimestamp
	private LocalDateTime lastUpdatedDate;
	
	private int likes;
	
	private boolean visible;
	
	private boolean archive;
	
	private boolean deleted;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private List<Comment> comments;

	@ElementCollection
	private Set<Integer> reactUsers = new HashSet<>();
}
