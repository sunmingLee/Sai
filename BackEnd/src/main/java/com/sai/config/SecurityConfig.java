package com.sai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sai.jwt.JwtAuthenticationEntryPoint;
import com.sai.jwt.JwtAuthenticationFilter;
import com.sai.jwt.JwtTokenProvider;
import com.sai.model.service.user.OAuth2UserServiceImpl;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		securedEnabled = true,
		jsr250Enabled = true,
		prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
	// JWT 관련 친구들
	private final JwtTokenProvider jwtTokenProvider;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final OAuth2UserServiceImpl oAuth2UserServiceImpl;


	// pw 암호화를 위한 BCrypt 해시 설정
	// 순환 참조 방지 위해서 따로 클래스를 만들어 관리 passwordEncoder를 Bean으로 등록
//	@Bean
//	public BCryptPasswordEncoder bCryptPasswordEncoder() {
//		return new BCryptPasswordEncoder();
//	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	// 인증 또는 인가에 대한 설정
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// 로그인
		http
			.httpBasic().disable()
			.csrf().disable(); // post 방식으로 값을 전송할 때 token을 사용해야 하는 보안 설정을 해제
//			.formLogin()
//			.loginPage("/api/user/login") // 인증되지 않은 사용자가 인증을 필요로 하는 endpoint에 접근했을 때 이동하는 페이지
//			.loginProcessingUrl("/api/user/login") // '/user/logInProc'로 post 요청이 올 때 로그인 처리를 수행
//			.usernameParameter("userId") // 사용자를 찾기 위해 사용할 매개변수(아이디)
//			.passwordParameter("password"); // 비밀번호 매개변수
//			.defaultSuccessUrl("/api/feed") // 성공시 리다이렉트할 페이지
//			.failureUrl("/api/user/login"); // 실패시 리다이렉트할 페이지

		// 로그아웃
		http
			.logout()						// 로그아웃 처리
			.logoutUrl("/api/user/logout")				// 로그아웃 처리 URL
			.logoutSuccessUrl("/api/user/login")			// 로그아웃 성공 후 이동페이지
			.deleteCookies("JSESSIONID", "remember"); 	// 로그아웃 후 쿠키 삭제

		// Remeber Me
//		http
//			.rememberMe()
//			.rememberMeParameter("remember") // 기본 파라미터명은 remember-me
//			.tokenValiditySeconds(3600) // Default 는 14일
//			.alwaysRemember(true); // 리멤버 미 기능이 활성화되지 않아도 항상 실행
//			.userDetailsService(userDetailsService);

		http
			.authorizeRequests()
			.antMatchers("/", "/favicon.ico",
                    "/**/*.png",
                    "/**/*.gif",
                    "/**/*.svg",
                    "/**/*.jpg",
                    "/**/*.html",
                    "/**/*.css",
                    "/**/*.js" ).permitAll() // 특정 URL을 설정하며, permitAll은 해당 URL의 접근을 인증없이 허용한다는 의미
			.antMatchers("/auth/**").permitAll()
			// user - 로그인, 회원가입, 아이디 찾기, 비밀번호 찾기
			.antMatchers("/", "/api/user/**").permitAll()
			.antMatchers("/api/poll/**").permitAll()
			.antMatchers("/**").permitAll()	// 개발 기간 동안 모든 사이트 허용
			.antMatchers("/v2/api-docs", "/swagger*/**").permitAll()
			.anyRequest().authenticated();

		http
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.maximumSessions(1)
			.maxSessionsPreventsLogin(true);
		http
			.oauth2Login()
			.userInfoEndpoint()
			.userService(oAuth2UserServiceImpl);

		http
			.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}




}
