package com.shinom.blogging.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="comment")
@Data
@NoArgsConstructor
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	public String comment;
	private Date date;
	private int likes;
	private boolean deleted;
	
	
	@ManyToOne()
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne()
	@JoinColumn(name="post_id")
	private Post post;
	
}
