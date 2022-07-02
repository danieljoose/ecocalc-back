package br.com.ecocalc.domains;

import java.util.List;
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
public class Residencia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Usuario usuario;

    @OneToMany(mappedBy = "residencia", fetch = FetchType.LAZY)
    private List<Pessoa> pessoas;

    @NotBlank
	private String nome;

    public Long getUsuarioId() {
        return usuario.getId();
    }

}