package com.sai.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
	@Value("${jwt.token.secret-key}")
	private String secretKey;
	
	@Value("${jwt.token.expire-length}")
	private long expireTime;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	// 적절한 설정을 통해 토큰을 생성하여 반환
	
	
	
}
