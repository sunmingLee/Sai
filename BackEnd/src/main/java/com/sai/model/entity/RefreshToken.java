package com.sai.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table(name = "refresh_token")
public class RefreshToken {

	@Id
	private String key;

	@Column(nullable = false)
	private String value;

	public void updateValue(String token) {
		this.value = token;
	}

	@Builder
	public RefreshToken(String key, String value) {
		this.key = key;
		this.value = value;
	}

}
