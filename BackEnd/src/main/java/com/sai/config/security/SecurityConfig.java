package com.sai.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.sai.config.JwtTokenProvider;
import com.sai.config.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	// JWT 관련 친구들
	private final JwtTokenProvider jwtTokenProvider;
	
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}
	
	// pw 암호화를 위한 BCrypt 해시 설정
	// passwordEncoder를 Bean으로 등록
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	// 인증 또는 인가에 대한 설정
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf().disable() // post 방식으로 값을 전송할 때 token을 사용해야 하는 보안 설정을 해제
			.formLogin()
			.loginPage("user/logIn") // 인증되지 않은 사용자가 인증을 필요로 하는 endpoint에 접근했을 때 이동하는 페이지
			.loginProcessingUrl("user/logInProc") // '/user/logInProc'로 post 요청이 올 때 로그인 처리를 수행
			.usernameParameter("userId") // 사용자를 찾기 위해 사용할 매개변수
			.passwordParameter("password") // 비밀번호 매개변수
			.defaultSuccessUrl("/") // 성공시 리다이렉트할 페이지
			.failureUrl("/user/logIn?fail=true"); // 실패시 리다이렉트할 페이지
		http
			.authorizeRequests()
			.antMatchers("/", "user/join").permitAll() // 특정 URL을 설정하며, permitAll은 해당 URL의 접근을 인증없이 허용한다는 의미
			.anyRequest().authenticated();
		http
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.apply(new JwtSecurityConfig(jwtTokenProvider));
		
		
		return http.build();
	}
	
	
	

}
