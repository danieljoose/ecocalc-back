package br.com.ecocalc.repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.ecocalc.domains.Despesa;

import java.util.Optional;
@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {
    Optional<Despesa> findById(Long id);

}
