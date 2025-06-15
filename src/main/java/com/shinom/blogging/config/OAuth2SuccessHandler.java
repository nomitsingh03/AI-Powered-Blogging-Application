package com.shinom.blogging.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.shinom.blogging.entities.Role;
import com.shinom.blogging.entities.User;
import com.shinom.blogging.helper.WebConstants;
import com.shinom.blogging.repositories.RoleRepo;
import com.shinom.blogging.repositories.UserRepo;
import com.shinom.blogging.services.UserServices;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OAuth2SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	 private static final Logger logger = LoggerFactory.getLogger(OAuth2SuccessHandler.class);
	@Autowired
	private UserServices services;
	
	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private UserRepo userRepo;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException, RuntimeException {
		// TODO Auto-generated method stub
		OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
		System.out.println(oauthToken);
		String registrationId = oauthToken.getAuthorizedClientRegistrationId(); 
		 OAuth2User user = (OAuth2User) authentication.getPrincipal();
		 String email = user.getAttribute("email");
		 String name = user.getAttribute("name");
		 if(email==null || email.equals("")) {
			 logger.error("Email must required ");
			 response.sendRedirect("http://localhost:3000/login");
		 } else {
		 String provider = user.getAttribute(registrationId);
		 User user2 = this.services.getUser2ByEmail(email);
		 if(user2==null) {
		 user2  = new User();
		 user2.setEmail(email);
		 user2.setName(name);
		 user2.setProvider(provider);
		 Role role = this.roleRepo.findById(WebConstants.NORMAL_USER).get();
			user2.getRoles().add(role);
			user2 = userRepo.save(user2);
		 }
		 
		response.sendRedirect("http://localhost:3000/redirect?userId="+user2.getUserId());
		 }
		super.onAuthenticationSuccess(request, response, authentication);
	}

//	private String fetchEmailFromGitHub(OAuth2AuthenticationToken oauthToken) {
//		// TODO Auto-generated method stub
//		String accessToken = oauthToken.getAuthorizedClient().getAccessToken().getTokenValue();
//	    RestTemplate restTemplate = new RestTemplate();
//	    HttpHeaders headers = new HttpHeaders();
//	    headers.add("Authorization", "Bearer " + accessToken);
//	    HttpEntity<String> entity = new HttpEntity<>(headers);
//
//	    String emailEndpoint = "https://api.github.com/user/emails";
//	    ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
//	        emailEndpoint,
//	        HttpMethod.GET,
//	        entity,
//	        new ParameterizedTypeReference<List<Map<String, Object>>>() {}
//	    );
//
//	    List<Map<String, Object>> emails = response.getBody();
//	    if (emails != null) {
//	        for (Map<String, Object> emailData : emails) {
//	            Boolean isPrimary = (Boolean) emailData.get("primary");
//	            Boolean isVerified = (Boolean) emailData.get("verified");
//	            if (isPrimary != null && isPrimary && isVerified != null && isVerified) {
//	                return (String) emailData.get("email");
//	            }
//	        }
//	    }
//		return null;
//	}

	
}
