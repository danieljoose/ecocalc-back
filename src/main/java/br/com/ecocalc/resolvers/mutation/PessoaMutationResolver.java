package br.com.ecocalc.resolvers.mutation;

import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;
import br.com.ecocalc.domains.*;
import br.com.ecocalc.services.*;
import br.com.ecocalc.repositories.*;
import br.com.ecocalc.services.AuthenticationService;
import br.com.ecocalc.payload.response.JwtResponse;
import br.com.ecocalc.security.jwt.JwtUtils;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.GraphQLException;

import java.util.Optional;
import java.time.OffsetDateTime;


@Component
public class PessoaMutationResolver implements GraphQLMutationResolver{
    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaService pessoaService;

    public Pessoa cadastrarPessoa(String nome, String sobrenome, Long residenciaId, Long usuarioId) {
        return pessoaService.cadastrarPessoa(nome, sobrenome, residenciaId, usuarioId);
    }

}