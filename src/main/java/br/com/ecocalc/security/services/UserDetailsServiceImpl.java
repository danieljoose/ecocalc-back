package br.com.ecocalc.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ecocalc.domains.Usuario;
import br.com.ecocalc.repositories.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario user = usuarioRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

		return UserDetailsImpl.build(user);
	}

}
