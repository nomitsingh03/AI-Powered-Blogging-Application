package com.shinom.blogging.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shinom.blogging.entities.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{

	Optional<User> findByEmail(String email);
	
	@Query("SELECT u FROM User u WHERE u.deactivate = true")
    List<User> findDeactivatedUserById();

    // 2. Find all users who are archived
    @Query("SELECT u FROM User u WHERE u.archive = true")
    List<User> findArchivedUsers();

    // 3. Find all users who are neither deactivated nor archived
    @Query("SELECT u FROM User u WHERE u.deactivate = false AND u.archive = false")
    List<User> findActiveNonArchivedUsers();
    
    Optional<User> findByEmailAndDeactivateFalse(String email);
	
	
}
