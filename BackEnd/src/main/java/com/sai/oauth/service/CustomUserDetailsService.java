package com.sai.oauth.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sai.model.entity.user.User;
import com.sai.model.repository.user.UserRepository;
import com.sai.oauth.entity.UserPrincipal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUserId(username);
		if (user == null) {
			throw new UsernameNotFoundException("Can not find username.");
		}
		return UserPrincipal.create(user);
	}

}
