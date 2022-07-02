package br.com.ecocalc.repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.ecocalc.domains.Residencia;

import java.util.Optional;
@Repository
public interface ResidenciaRepository extends JpaRepository<Residencia, Long> {
    Optional<Residencia> findById(Long id);

    List<Residencia> findAllByUsuario_Id(Long usuarioId);

}
