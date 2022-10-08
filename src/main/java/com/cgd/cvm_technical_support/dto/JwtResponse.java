package com.cgd.cvm_technical_support.dto;

import com.cgd.cvm_technical_support.model.primary.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data @AllArgsConstructor
public class JwtResponse implements Serializable {

	private final String token;
	private User user;

}