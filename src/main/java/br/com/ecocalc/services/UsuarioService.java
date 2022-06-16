package br.com.ecocalc.services;

import java.util.concurrent.atomic.AtomicLong;
import br.com.ecocalc.domains.*;
import br.com.ecocalc.repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.crypto.password.PasswordEncoder;

// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import graphql.GraphQLException;


import java.util.Optional;
import java.time.OffsetDateTime;



@RestController
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
        
    @Autowired
	private PasswordEncoder encoder;

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    public Usuario getUsuario(@RequestParam(value = "name", defaultValue = "World") String name){
        System.out.println(name);
        Usuario usuario = new Usuario();
        usuario.setNome(name);
        usuario.setEmail("daniel@teste.com");
        usuario.setSenha("123");
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> findById (Long id) {
		return usuarioRepository.findById(id);
	}

    @Transactional
	public Usuario cadastrarUsuario(String nome, String sobrenome, String email, String senha) {
		// if (usuarioRepository.existsByEmail(email)) {
		// 	throw new GraphQLException("email_already_exists");
		// }

        String encodedSenha = encoder.encode(senha);

		Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setSobrenome(nome);
        usuario.setEmail(email);
        usuario.setSenha(encodedSenha);
		usuario = usuarioRepository.save(usuario);

		// enviarEmailValidacao(usuario.getEmail());

		return usuarioRepository.save(usuario);
	}

    public Optional<Usuario> findByEmail(String email) {
		return usuarioRepository.findByEmail(email);
	}

    public Usuario salvar(Usuario usuario) {
    	return usuarioRepository.save(usuario);
    }

    public Usuario registerUltimoAcesso(Long id){
		Optional<Usuario> optionalUsuario = this.findById(id);

		if(!optionalUsuario.isPresent()){
			throw new GraphQLException("user_not_found");
		}

		Usuario usuario = optionalUsuario.get();
		usuario.setUltimoAcesso(OffsetDateTime.now());
		usuario.setNumAcessos(usuario.getNumAcessos() == null? 1: usuario.getNumAcessos() + 1);
		return this.salvar(usuario);
	}



}