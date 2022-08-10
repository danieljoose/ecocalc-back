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
public class ResidenciaService {

    @Autowired
    private ResidenciaRepository residenciaRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;        

    @Transactional
	public Residencia cadastrarResidencia(String nome, Long pessoaId, Long usuarioId) {
	    Optional<Usuario> optionalUsuario = usuarioRepository.findById(usuarioId);
        System.out.println(optionalUsuario);
        if (!optionalUsuario.isPresent())
            throw new GraphQLException("forbidden");

        Residencia residencia = new Residencia();
        residencia.setNome(nome);
        residencia.setUsuario(optionalUsuario.get());
        residenciaRepository.save(residencia);

        Pessoa pessoa = pessoaRepository.findById(pessoaId).get();
        pessoa.setResidencia(residencia);
        pessoaRepository.save(pessoa);


		// enviarEmailValidacao(usuario.getEmail());

		return residencia;
	}



}