package br.com.ecocalc.domains;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HistoricoMes {
  public int mes;
  public int ano;
  public BigDecimal valorTotal;
  public List<Despesa> despesas;
  public List<Residencia> residencias;
  public List<Pessoa> pessoas;

  public List<Pessoa> getPessoas() {
        return pessoas;
    }
}
