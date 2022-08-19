package com.sai.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
	private String secretKey = "16글자를넘겨야한다니정말로어렵군요흑흑빨리끝나라";
	private long tokenValidTime = 1000L * 60 * 120;

	Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

	private final UserDetailsService userDetailsService;

// 토큰 생성
	public String createToken(String userId){

		Date now = new Date();
	    Claims claims = Jwts.claims()
	    	.setSubject(userId)
	        .setIssuedAt(now) //생성일 설정
	        .setExpiration(new Date(now.getTime()+tokenValidTime)); //만료일 설정

//	    claims.put("userId", userId); //담고 싶은 값

	    return Jwts.builder()
		        .setHeaderParam("typ", "JWT")
		        .setClaims(claims)
		        .signWith(key, SignatureAlgorithm.HS256)
		        .compact();
	}

	// 토큰 유효성 검사
	public boolean checkClaim(String jwt) {
	    try {
	        Claims claims = Jwts.parserBuilder()
	            .setSigningKey(key).build()
	            .parseClaimsJws(jwt).getBody();
	        return true;

	    }catch(ExpiredJwtException e) {   //Token이 만료된 경우 Exception이 발생한다.
	    	System.out.println("토큰 만료지롱");
	        return false;

	    }catch(JwtException e) {        //Token이 변조된 경우 Exception이 발생한다.
	    	System.out.println("변조된 토큰이지롱");
	        return false;
	    }
	}

	// token 정보 확인
	public Claims getJwtContents(String jwt) {
	    Claims claims = Jwts.parserBuilder()
	        .setSigningKey(key).build()
	        .parseClaimsJws(jwt).getBody();
	    return claims;
	}

	// 토큰에서 회원정보 추출
	 public String getUserPk(String jwt) {
		 String info = this.getJwtContents(jwt).getSubject();
		 System.out.println("토큰의 회원정보야");
		 System.out.println(info);
	        return info;
	    }

	 // Request의 Header에서 token 값을 가져옵니다. "accessToken" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        String token = null;
        System.out.println("토큰 프로바이더");
        if(request.getHeader("Authorization") != null) token = request.getHeader("Authorization");
//        Cookie cookie = WebUtils.getCookie(request, "accessToken");
//        if(cookie != null) token = cookie.getValue();
        return token;
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        System.out.println(userDetails.getAuthorities().toString());
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
