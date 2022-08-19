package com.sai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//import com.sai.jwt.JwtAuthenticationEntryPoint;
import com.sai.jwt.JwtAuthenticationFilter;
import com.sai.jwt.JwtTokenProvider;
import com.sai.model.service.user.OAuth2UserServiceImpl;
import com.sai.security.JwtAccessDeniedHandler;
import com.sai.security.JwtAuthenticationEntryPoint;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
	// JWT 관련 친구들
	private final JwtTokenProvider jwtTokenProvider;
	private final OAuth2UserServiceImpl oAuth2UserServiceImpl;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	// 인증 또는 인가에 대한 설정
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// 로그인
		http.httpBasic().disable()
			.cors()
			.and()
			.csrf().disable()
			.exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler); // post 방식으로 값을 전송할 때 token을 사용해야 하는 보안 설정을 해제

		http.authorizeRequests()
				.antMatchers("/", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html",
						"/**/*.css", "/**/*.js")
				.permitAll() // 특정 URL을 설정하며, permitAll은 해당 URL의 접근을 인증없이 허용한다는 의미
				.antMatchers("/auth/**").permitAll()
				// user - 로그인, 회원가입, 아이디 찾기, 비밀번호 찾기
//			.antMatchers("/**").permitAll()	// 개발 기간 동안 모든 사이트 허용
				.antMatchers("/api/user/duplication/**", "/api/user/join", "/api/user/login", "/api/user/findId", "/api/user/findPw").permitAll()
				.antMatchers("/v2/api-docs", "/swagger*/**").permitAll()
				.anyRequest().hasAnyAuthority("ROLE_USER");

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//		http
//			.oauth2Login()
//			.userInfoEndpoint()
//			.userService(oAuth2UserServiceImpl);

		http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

}
