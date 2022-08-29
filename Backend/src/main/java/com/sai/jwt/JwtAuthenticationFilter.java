package com.sai.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	private final JwtTokenProvider jwtTokenProvider;
//	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// 헤더에서 JWT를 받아온다
		  String token = jwtTokenProvider.resolveToken(request);

		// 토큰 유효성 검사
	      if(token != null && jwtTokenProvider.checkClaim(token)){
	    	  // 토큰이 유효하면 토큰으로부터 유저 정보 받아오기
	            Authentication authentication = jwtTokenProvider.getAuthentication(token);
	          // SecurityContext에 Authentication 객체를 저장
	            SecurityContextHolder.getContext().setAuthentication(authentication);
	        }

	        filterChain.doFilter(request, response);
	    }

//	private String getJwtFromReque(HttpServletRequest request) {
//		String bearerToken = request.getHeader("Authorization");
//		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//			return bearerToken.substring(7, bearerToken.length());
//		}
//		return null;
//	}

}
