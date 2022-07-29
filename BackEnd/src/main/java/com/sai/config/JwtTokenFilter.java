package com.sai.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sai.exception.CustomException;

public class JwtTokenFilter extends OncePerRequestFilter {

	private JwtTokenProvider jwtTokenProvider;

	public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// 헤더에서 JWT를 받아온다
		String token = jwtTokenProvider.resolveToken(request);

		try {
			// 유효한 토큰인지 확인
			if (token != null && jwtTokenProvider.validateToken(token)) {
				// 토큰이 유효하면 토큰으로부터 유저 정보 받아오기
				Authentication auth = jwtTokenProvider.getAuthentication(token);
				//SecurityContext에 Authentication 객체 저장
				SecurityContextHolder.getContext().setAuthentication(auth);
			}

		} catch (CustomException e) {
			SecurityContextHolder.clearContext();
			response.sendError(e.getHttpStatus().value(), e.getMessage());
			return;
		}

	}

}
