package com.sunny.ems.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // VERY IMPORTANT
@AllArgsConstructor
public class LoginRequest {

	private String email;
	private String password;
}
