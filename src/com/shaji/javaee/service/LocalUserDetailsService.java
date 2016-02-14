package com.shaji.javaee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.shaji.javaee.model.User;
import com.shaji.javaee.repository.UserRepository;

@Component("localUserDetailsService")
public class LocalUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("Invalid Login");
		}

		final List<GrantedAuthority> auths;
		auths = AuthorityUtils.NO_AUTHORITIES;

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true, // enabled
				true, // account not expired
				true, // credentials not expired
				true, // account not locked
				auths);
	}

}
