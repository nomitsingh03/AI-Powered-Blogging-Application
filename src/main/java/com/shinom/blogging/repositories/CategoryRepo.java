package com.shinom.blogging.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shinom.blogging.entities.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
