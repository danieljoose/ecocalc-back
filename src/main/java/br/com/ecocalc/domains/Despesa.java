package br.com.ecocalc.domains;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Entity
@Getter
@Setter
@Table
public class Despesa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Usuario usuario;

    @OneToOne(cascade = CascadeType.REMOVE)
    private Residencia residencia;

    @OneToOne(cascade = CascadeType.REMOVE)
    private Pessoa pessoa;

    @NotBlank
	private String titulo;

    private OffsetDateTime data;

    private BigDecimal valor;

    public Long getUsuarioId() {
        return usuario.getId();
    }

}