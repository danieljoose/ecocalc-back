package br.com.ecocalc.repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.ecocalc.domains.Pessoa;

import java.util.Optional;
@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Optional<Pessoa> findById(Long id);

    List<Pessoa> findAllByUsuario_Id(Long usuarioId);

    @Query(value =
    "SELECT * " +
        "FROM pessoa " +
        "WHERE usuario_id = :usuarioId " +
        "AND residencia_id IS NULL " +
        "ORDER BY nome ASC", nativeQuery = true)
    List<Pessoa> findPessoasSemResidencia(Long usuarioId);


}
