package br.com.ecocalc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.ecocalc.domains.Usuario;
import br.com.ecocalc.payload.response.JwtResponse;
import br.com.ecocalc.security.jwt.JwtUtils;
import br.com.ecocalc.security.services.UserDetailsImpl;

@Service
public class AuthenticationService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtils jwtUtils;

	public JwtResponse authenticateUser(String login, String senha) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(login, senha));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		JwtResponse jwtResponse = new JwtResponse(jwt);
		return jwtResponse;
	}
}
