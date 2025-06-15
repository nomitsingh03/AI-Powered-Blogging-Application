package com.shinom.blogging.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Role {
	
	@Id
	private int id;
	private String roleName;
	
	@ManyToMany(mappedBy = "roles")
	private List<User> users;
	

}
