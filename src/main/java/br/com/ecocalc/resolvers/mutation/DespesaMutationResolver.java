package br.com.ecocalc.resolvers.mutation;

import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;
import br.com.ecocalc.domains.*;
import br.com.ecocalc.services.*;
import br.com.ecocalc.repositories.*;
import br.com.ecocalc.services.AuthenticationService;
import br.com.ecocalc.services.DespesaService;
import br.com.ecocalc.payload.response.JwtResponse;
import br.com.ecocalc.security.jwt.JwtUtils;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.GraphQLException;
import java.time.OffsetDateTime;

import java.util.Optional;
import java.math.BigDecimal;
import java.time.OffsetDateTime;


@Component
public class DespesaMutationResolver implements GraphQLMutationResolver{
    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private DespesaService despesaService;

    public Despesa cadastrarDespesa(String titulo, OffsetDateTime data, BigDecimal valor, Long residenciaId, Long pessoaId, Long usuarioId) {
        return despesaService.cadastrarDespesa(titulo, data, valor, residenciaId, pessoaId, usuarioId);
    }

}