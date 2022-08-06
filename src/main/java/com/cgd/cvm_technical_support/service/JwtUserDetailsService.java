package com.cgd.cvm_technical_support.service;

import com.cgd.cvm_technical_support.model.primary.User;
import com.cgd.cvm_technical_support.repository.primary.ReUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	ReUser userRepository;
	
	@Autowired
	public PasswordEncoder passwordEncoder ;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(
				user.getUsername(), passwordEncoder.encode(user.getPassword()),
				new ArrayList<>());

	}
	
	public User getUserFromDB(String username) {
		User user = userRepository.findByUsername(username);
		return user;
	}
}