package com.shinom.blogging.entities;


import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table
@NoArgsConstructor
public class LoginDetails {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int id;
	
	int userId;
	
	private String email;
	
	private LocalDateTime loginTime;
	
	private LocalDateTime accountCreationTime;

}
