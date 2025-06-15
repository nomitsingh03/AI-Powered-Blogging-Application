package com.shinom.blogging.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shinom.blogging.entities.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {

}
