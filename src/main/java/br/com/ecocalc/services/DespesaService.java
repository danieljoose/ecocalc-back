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
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.List;


// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import graphql.GraphQLException;


import java.util.Optional;
import java.time.OffsetDateTime;
import java.math.BigDecimal;


@Service
public class DespesaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ResidenciaRepository residenciaRepository;

    @Autowired
    private UsuarioService usuarioService;        

   
    @Transactional
	public Despesa cadastrarDespesa(String titulo, OffsetDateTime data, BigDecimal valor, Long residenciaId, Long pessoaId, Long usuarioId) {

	    Optional<Usuario> optionalUsuario = usuarioRepository.findById(usuarioId);
        System.out.println(optionalUsuario);
        if (!optionalUsuario.isPresent())
            throw new GraphQLException("forbidden");

        Despesa despesa = new Despesa();

        if(residenciaId != null){
            Optional<Residencia> optionalResidencia = residenciaRepository.findById(residenciaId);
            despesa.setResidencia(optionalResidencia.get());
        } else {
            Optional<Pessoa> optionalPessoa = pessoaRepository.findById(pessoaId);
            despesa.setPessoa(optionalPessoa.get());
            despesa.setResidencia(optionalPessoa.get().getResidencia());

        }        
        
        despesa.setUsuario(optionalUsuario.get());
        despesa.setTitulo(titulo);
        despesa.setValor(valor);
        despesa.setData(data);
        despesaRepository.save(despesa);

		return despesa;
	}



}