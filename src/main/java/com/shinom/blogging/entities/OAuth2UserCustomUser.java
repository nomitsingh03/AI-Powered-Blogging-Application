package com.shinom.blogging.entities;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class OAuth2UserCustomUser implements OAuth2User {
	
	private int userId;
	private String email;
	private String name;
	private String password;
	private static Collection<? extends GrantedAuthority> authorities;
	private Map<String, Object> attributes;
	
	public OAuth2UserCustomUser(int id, String email, String name, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.userId = id;
		this.email = email;
		this.name = name;
		this.password = password;
		this.authorities = authorities;
	}
	
	public static OAuth2UserCustomUser create(User user) {
		List<GrantedAuthority> authorities = Collections.
                singletonList(new SimpleGrantedAuthority("ROLE_USER"));
		return new OAuth2UserCustomUser(
				user.getUserId(),
				user.getEmail(),
				user.getName(),
				user.getPassword(),
				authorities);
	}
	
	public static OAuth2UserCustomUser create(User user, Map<String, Object> attributes) {
		OAuth2UserCustomUser customUser = OAuth2UserCustomUser.create(user);
		customUser.setAttributes(attributes);
		return customUser;
	}

	private void setAttributes(Map<String, Object> attributes) {
		// TODO Auto-generated method stub
		 this.attributes = attributes;
		
	}

	@Override
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return this.attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}
	
	public String getEmail() {
		// TODO Auto-generated method stub
		return this.email;
	}

}
