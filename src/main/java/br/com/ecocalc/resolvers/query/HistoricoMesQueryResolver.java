package br.com.ecocalc.resolvers.query;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import br.com.ecocalc.domains.*;
import br.com.ecocalc.repositories.*;
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

    // @Scheduled(cron = "0/10 * * * * *")  
    public List<HistoricoMes> getHistoricoMes(Long usuarioId, Long residenciaId, Long pessoaId){
        //    public List<HistoricoMes> getHistoricoMes(Long usuarioId, Long residenciaId, Long pessoaId){

        System.out.println(usuarioId + " - " + residenciaId + " - " + pessoaId);
        List<Despesa> despesas = new ArrayList<>();

        // despesas = despesaRepository.findByUsuario_IdOrderByDataDesc(usuarioId);
        // despesaRepository.findByUsuario_IdOrderByDataDesc(usuarioId);

        if(pessoaId != null) {
            despesas = despesaRepository.findByUsuario_IdAndPessoa_IdOrderByDataDesc(usuarioId, pessoaId);
        } else if (residenciaId != null) {
            despesas = despesaRepository.findByUsuario_IdAndResidencia_IdOrderByDataDesc(usuarioId, residenciaId);
        } else {
            despesas = despesaRepository.findByUsuario_IdOrderByDataDesc(usuarioId);
        }

         System.out.println(despesas.size());

        List<HistoricoMes> historicoMeses = new ArrayList<>();

        // System.out.println(despesas.get(0).getData().getMonth().getValue());
        // System.out.println(despesas.size());

        if(despesas.size() > 0) {


            int mes = despesas.get(0).getData().getMonth().getValue();
            int ano = despesas.get(0).getData().getYear();
            List<Despesa> despesasMes = new ArrayList<>();
            List<Pessoa> pessoasMes = new ArrayList<>();
            List<Residencia> residenciasMes = new ArrayList<>();
            BigDecimal valorTotalMes = new BigDecimal("0");

            for(Despesa despesa : despesas) {
                if(mes == despesa.getData().getMonth().getValue() && ano == despesa.getData().getYear()){
                    despesasMes.add(despesa);
                    valorTotalMes = valorTotalMes.add(despesa.getValor());

                    if(!pessoasMes.contains(despesa.getPessoa()) && despesa.getPessoa() != null){
                    
                        pessoasMes.add(despesa.getPessoa());
                    }

                    if(!residenciasMes.contains(despesa.getResidencia()) && despesa.getResidencia() != null){
                        residenciasMes.add(despesa.getResidencia());
                    }                
                } else {
                    System.out.println("Historia mes: " + mes + " - Pessoas: " + pessoasMes.size());
                    List<Despesa> despesasAdd = despesasMes;
                    List<Pessoa> pessoasAdd = pessoasMes;
                    List<Residencia> residenciasAdd = residenciasMes;

                    HistoricoMes historico = new HistoricoMes(Integer.valueOf(mes), Integer.valueOf(ano), valorTotalMes, despesasAdd, residenciasAdd, pessoasAdd);
                    historicoMeses.add(historico);
                    System.out.println(historico.getPessoas().size());

                    valorTotalMes = new BigDecimal("0");
                    valorTotalMes = valorTotalMes.add(despesa.getValor());
                    // System.out.println(despesa.getData().getMonth().getValue() + "/" + despesa.getData().getYear());

                    mes = despesa.getData().getMonth().getValue();
                    ano = despesa.getData().getYear();

                    despesasMes = new ArrayList<>();
                    despesasMes.add(despesa);

                    pessoasMes = new ArrayList<>();
                    pessoasMes.add(despesa.getPessoa());

                    residenciasMes = new ArrayList<>();
                    residenciasMes.add(despesa.getResidencia());
                }
            }

            HistoricoMes historico = new HistoricoMes(Integer.valueOf(mes), Integer.valueOf(ano), valorTotalMes, despesasMes, residenciasMes, pessoasMes);
            historicoMeses.add(historico);
            System.out.println(historico.getPessoas().size());

            // for(HistoricoMes his : historicoMeses){
            //     System.out.println(his.getPessoas().size());
            // }
        }

        // System.out.println(historicoMeses.size());
        return historicoMeses;
    }


}