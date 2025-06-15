package com.shinom.blogging.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shinom.blogging.entities.ContactSupport;

@Repository
public interface ContactSupportRepository extends JpaRepository<ContactSupport, Integer> {

}
