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
import java.time.OffsetDateTime;

@Data
@Entity
@Getter
@Setter
@Table
public class Pessoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Usuario usuario;

    @OneToOne(cascade = CascadeType.REMOVE)
    private Residencia residencia;

    @NotBlank
	private String nome;

    @NotBlank
    private String sobrenome;

    public Long getUsuarioId() {
        return usuario.getId();
    }

    public Long getResidenciaId() {
        return residencia.getId();
    }

}