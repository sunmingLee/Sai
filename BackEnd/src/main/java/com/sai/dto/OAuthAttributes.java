package com.sai.dto;

import java.util.Map;

import com.sai.model.entity.user.User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {
	private Map<String, Object> attributes; // OAuth2 반환하는 유저 정보 Map
	private String nameAttributeKey; // 서비스 제공자
	private String name;
	private String email;
	private String picture;

	@Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

	 public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
	        // 여기서 네이버와 카카오 등 구분 (ofNaver, ofKakao)
	        return ofNaver("id", attributes);
	    }

	 private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {

		 Map<String, Object> response = (Map<String, Object>)attributes.get("response");

	        return OAuthAttributes.builder()
	                .name((String) attributes.get("name"))
	                .email((String) attributes.get("email"))
	                .picture((String) attributes.get("profile_image"))
	                .attributes(response)
	                .nameAttributeKey(userNameAttributeName)
	                .build();
	    }

	  public User toEntity(){
	        return User.builder()
	                .userName(name)
	                .email(email)
	                .userImagePath(picture)
	                .build();
	    }

}
