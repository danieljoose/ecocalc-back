package br.com.ecocalc.services;

import java.util.concurrent.atomic.AtomicLong;
import br.com.ecocalc.domains.*;
import br.com.ecocalc.repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import graphql.GraphQLException;


import java.util.Optional;
import java.time.OffsetDateTime;



@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ResidenciaRepository residenciaRepository;
        
    @Autowired
	private PasswordEncoder encoder;

    @Value("${jwt.senhaJwtExpirationMs}")
	private int senhaJwtExpirationMs;


    public Optional<Usuario> getRequester() {
    try {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            System.out.println(securityContext);
            Authentication authentication = securityContext.getAuthentication();
            System.out.println(authentication.getName());
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername();
            
            Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(email);
            return optionalUsuario;
        } catch (Exception e) {
            System.out.println(e);
            return Optional.empty();
        }
	}

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
        System.out.println(email);
        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(email);
		if (optionalUsuario.isPresent()) {
			throw new GraphQLException("email_already_exists");
		}

        String encodedSenha = encoder.encode(senha);

		Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setSobrenome(nome);
        usuario.setEmail(email);
        usuario.setSenha(encodedSenha);
        usuario = usuarioRepository.save(usuario);

        Residencia residencia = new Residencia();
        residencia.setNome("Minha residência");
        residencia.setUsuario(usuario);
        residenciaRepository.save(residencia);


		// enviarEmailValidacao(usuario.getEmail());

		return usuario;
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