package com.sai.jwt;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sai.security.UserPrincipal;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	private final JwtTokenProvider jwtTokenProvider;
//	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		System.out.println("나는 filter야");
		// 헤더에서 JWT를 받아온다
		  String token = jwtTokenProvider.resolveToken(request);
		  System.out.println(token);
		  // 여기서 밑으로 안넘어가고 반복됨 왜지?
		// 토큰 유효성 검사
	      if(token != null && jwtTokenProvider.checkClaim(token)){
	    	  // 토큰이 유효하면 토큰으로부터 유저 정보 받아오기
	            Authentication authentication = jwtTokenProvider.getAuthentication(token);
	          // SecurityContext에 Authentication 객체를 저장
	            SecurityContextHolder.getContext().setAuthentication(authentication);
	            // 로그용
	            UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
	            System.out.println("이건 필터가 authentication.getprincipal 아이디를 가져온거야");
	    		System.out.println(userDetails.getUserId());
	    		
	      }

	        filterChain.doFilter(request, response);
	    }

}
