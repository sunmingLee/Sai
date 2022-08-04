package com.sai.poll;

import javax.validation.constraints.NotBlank;

import lombok.Data;


@Data
public class LoginRequest {

	@NotBlank
	private String userIdOrName;

	@NotBlank
	private String password;


}
