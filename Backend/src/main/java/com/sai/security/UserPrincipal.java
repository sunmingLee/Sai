package com.sai.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sai.model.entity.User;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserPrincipal implements UserDetails, OAuth2User{
	
//	private Map<String, Object> attributes;

	private String userId;
	private String userName;

	@JsonIgnore
	private String email;

	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	public static UserPrincipal create(User user) {
		Collection<GrantedAuthority> authorities = new HashSet<>();
		authorities.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				return user.getRoleKey();
			}
		});
		
//		List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
//		new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());

		return new UserPrincipal(
                user.getUserId(),
                user.getUserName(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );

	}
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        UserPrincipal that = (UserPrincipal) o;
	        return Objects.equals(userId, that.userId);
	    }

	    @Override
	    public int hashCode() {

	        return Objects.hash(userId);
	    }


		public String getUserId() {
			return userId;
		}


		public void setUserId(String userId) {
			this.userId = userId;
		}


		// OAuth2User 구현
		@Override
		public Map<String, Object> getAttributes() {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return null;
		}

		
		


}
