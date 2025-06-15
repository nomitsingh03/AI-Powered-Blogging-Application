package com.shinom.blogging.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shinom.blogging.entities.LoginDetails;

public interface LoginDetailsRepo extends JpaRepository<LoginDetails, Integer> {
	
	List<LoginDetails> findByUserId(int userId);

}
