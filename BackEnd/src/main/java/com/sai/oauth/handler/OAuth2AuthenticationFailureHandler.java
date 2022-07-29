package com.sai.oauth.handler;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.sai.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.sai.utils.CookieUtil;

import lombok.RequiredArgsConstructor;

import static com.sai.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{
	
	 private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;

	    @Override
	    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
	        String targetUrl = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
	                .map(Cookie::getValue)
	                .orElse(("/"));

	        exception.printStackTrace();

	        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
	                .queryParam("error", exception.getLocalizedMessage())
	                .build().toUriString();

	        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);

	        getRedirectStrategy().sendRedirect(request, response, targetUrl);
	    }

}
