package com.sai.config;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.sai.exception.CustomException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	@Value("${jwt.token.secret-key}")
	private String secretKey;
	
	@Value("${jwt.token.expire-length}")
	private long expireTime;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	// 적절한 설정을 통해 토큰을 생성하여 반환
	public String generateToken(Authentication authentication) {
		Claims claims = Jwts.claims().setSubject(authentication.getName());
		
		Date now = new Date();
		Date exiresIn = new Date(now.getTime()+expireTime);
		
		Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
		
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(exiresIn)
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}
	
	// 토큰으로부터 클레임을 만들고, 이를 통해 User 객체를 생성하여 Authentication 객체를 반환
	
	public Authentication getAuthentication(String token) {
		String userName = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
		
		return new UsernamePasswordAuthenticationToken(userDetails, "",userDetails.getAuthorities());
	}
	
	// http 헤더로부터 bearer 토큰을 가져옴
	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");
		if(bearerToken != null && bearerToken.startsWith("Bearer")) {
			return bearerToken.substring(7);
		}
		return null;
	}
	
	// 토큰을 검증
	public boolean validateToken(String token) throws CustomException {
		try {
			Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
			return true;
			
		} catch (JwtException e) {
			throw new CustomException("Error on Token", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	
	
	
}
