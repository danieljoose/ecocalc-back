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
public class ResidenciaQueryResolver implements GraphQLQueryResolver{
    @Autowired
    private ResidenciaRepository residenciaRepository;

    @Transactional
    public Optional<Residencia> getResidenciaById(Long id){
        return residenciaRepository.findById(id);
    }

    @Transactional
    public List<Residencia> getResidencias(Long usuarioId){
        return residenciaRepository.findAllByUsuario_Id(usuarioId);
    }
}