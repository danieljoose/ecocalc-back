package br.com.ecocalc.resolvers.query;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import br.com.ecocalc.domains.*;
import br.com.ecocalc.repositories.*;
import graphql.kickstart.tools.GraphQLQueryResolver;

import java.util.Optional;


@Component
public class PessoaQueryResolver implements GraphQLQueryResolver{
    @Autowired
    private PessoaRepository pessoaRepository;

    @Transactional
    public Optional<Pessoa> getPessoaById(Long id){
        return pessoaRepository.findById(id);
    }
}