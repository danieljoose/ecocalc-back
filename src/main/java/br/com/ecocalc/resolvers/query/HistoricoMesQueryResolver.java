package br.com.ecocalc.resolvers.query;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import br.com.ecocalc.domains.*;
import br.com.ecocalc.repositories.*;
import br.com.ecocalc.services.*;
import graphql.kickstart.tools.GraphQLQueryResolver;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;

@EnableScheduling
@Component
public class HistoricoMesQueryResolver implements GraphQLQueryResolver{
    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private HistoricoMesService historicoMesService;

    public List<HistoricoMes> getHistoricoMes(Long usuarioId, Long residenciaId, Long pessoaId){
        return historicoMesService.getHistoricoMes(usuarioId, residenciaId, pessoaId);
    }


}