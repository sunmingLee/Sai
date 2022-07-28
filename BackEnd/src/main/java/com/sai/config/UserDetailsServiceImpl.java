package com.sai.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sai.exception.UserNotFoundException;
import com.sai.model.entity.User;
import com.sai.model.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		
		User user = userRepository.findById(userId)
				.orElseThrow(()-> new UsernameNotFoundException(userId));
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		
		return new org
				.springframework
				.security
				.core
				.userdetails
				.User(user.getUser_id(), user.getPassword(), grantedAuthorities);
	}

}
