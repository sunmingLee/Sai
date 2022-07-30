package com.sai.model.dto.family;

import com.sai.model.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
	private String userId;
	private FamilyDto family;
	private String userName;
	
//	// Entity to Dto
//	public UserDto(User user) {
//		this.userId = user.getUserId();
//		if (user.getFamily() != null)
//			this.family = new FamilyDto(user.getFamily());
//		this.userName = user.getUserName();
//		this.nickname = user.getNickname();
//	}
//
//	// Dto to Entity
//	public User toEntity() {
//		return User.builder().userId(userId).family(family.toEntity()).userName(userName).nickname(nickname).build();
//	}
}
