package com.cgd.cvm_technical_support.controller.rest;

import com.cgd.cvm_technical_support.config.JwtTokenUtil;
import com.cgd.cvm_technical_support.model.primary.User;
import com.cgd.cvm_technical_support.service.SeCommon;
import com.cgd.cvm_technical_support.service.JwtUserDetailsService;
import com.cgd.cvm_technical_support.dto.JwtRequest;
import com.cgd.cvm_technical_support.dto.JwtResponse;
import com.cgd.cvm_technical_support.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private SeCommon seCommon;


	@RequestMapping(value = "/api/check")
	public ResponseEntity<Object> checkServer(){
		return ResponseEntity.ok(new Response(true,"Server Running"));
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(
			HttpServletRequest request, @RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		System.out.println(userDetails);
		final String token = jwtTokenUtil.generateToken(userDetails);

		// Modified by Programmer
		User user = userDetailsService.getUserFromDB(authenticationRequest.getUsername());
		if(user!=null) seCommon.recordLastLoginTime(user);
		return ResponseEntity.ok(new JwtResponse(token,user));
		// Modified by Programmer
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

}
