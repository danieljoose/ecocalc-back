package br.com.ecocalc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public abstract class AbstractService<T> {
    public abstract T salvar(T entity);
    public abstract T atualizar(Long id, T entity);
    public abstract Page<T> findAll(Pageable pageable);
    public abstract List<T> findAll(Sort sort);
    public abstract List<T> findAll();
    public abstract List<T> findAll(Example<T> example);
	public abstract Optional<T> findById (Long id);

}
