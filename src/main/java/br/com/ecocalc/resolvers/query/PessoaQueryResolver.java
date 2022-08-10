package br.com.ecocalc.resolvers.query;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import br.com.ecocalc.domains.*;
import br.com.ecocalc.repositories.*;
import graphql.kickstart.tools.GraphQLQueryResolver;

import java.util.Optional;
import java.util.List;


@Component
public class PessoaQueryResolver implements GraphQLQueryResolver{
    @Autowired
    private PessoaRepository pessoaRepository;

    public Optional<Pessoa> getPessoaById(Long id){
        return pessoaRepository.findById(id);
    }

    public List<Pessoa> getPessoas(Long usuarioId){
        return pessoaRepository.findAllByUsuario_Id(usuarioId);
    }

    public List<Pessoa> getPessoasSemResidencia(Long usuarioId){
        return pessoaRepository.findPessoasSemResidencia(usuarioId);
    }
}