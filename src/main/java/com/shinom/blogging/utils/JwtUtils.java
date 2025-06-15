package com.shinom.blogging.utils;



import org.apache.commons.lang3.time.DateUtils;
import org.apache.el.parser.ELParserTokenManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.shinom.blogging.helper.WebConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import javax.crypto.SecretKey;

@Slf4j
@Service
public class JwtUtils {
	
	private static final SecretKey secretKey = Jwts.SIG.HS256.key().build();
	
	private  static  final long EXPIRATION_TIME = 86400000;

//	public static boolean validateToken(String token) {
//		return parseToken(token).isPresent();
//	}
////	
//	private static Optional<Claims> parseToken(String token) {
//		// TODO Auto-generated method stub
//	  var jwtParser =  Jwts.parser()
//			  	.verifyWith(secretKey).build();
//	  
//	  try {
//		return Optional.of(jwtParser.parseSignedClaims(token)
//	  		.getPayload());
//	  } catch (JwtException | IllegalArgumentException e) {
//		log.error("JWT Exception");
//	  }
		
//		return Optional.empty();
//	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username= getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	private boolean isTokenExpired(String token) {
		// TODO Auto-generated method stub
//		return getExpiration(token).before(new Date(System.currentTimeMillis()));
		 return extractClaims(token, Claims::getExpiration).before(new Date(System.currentTimeMillis()));
	}

//	private Date getExpiration(String token) {
//		var claimsOp = parseToken(token);
//		return claimsOp.map(Claims::getExpiration).get();
//	}

//	public static Optional<String> getUsernameFromToken(String token) {
//		var claimsOp = parseToken(token);		
//		return claimsOp.map(Claims:: getSubject);
//	}	/
	 public String getUsernameFromToken(String token){
	        return extractClaims(token, Claims::getSubject);
	    }
	 
	 private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction){
	        return claimsTFunction.apply(Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload());
	    }
	
	public static String generateToken(String username) {
		var currentDate = new Date(System.currentTimeMillis());
		var expiration = new Date(System.currentTimeMillis()+EXPIRATION_TIME);
		return Jwts.builder()
			.id(UUID.randomUUID().toString())
			.issuer(WebConstants.ISSUER)
			.subject(username)
			.signWith(secretKey)
			.issuedAt(currentDate)
			.expiration(expiration)
			.compact();
			
	}
	
}
