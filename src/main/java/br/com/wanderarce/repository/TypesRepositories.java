package br.com.wanderarce.repository;



import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.wanderarce.entities.Types;

@Repository
@Transactional
public interface TypesRepositories extends JpaRepository<Types, Long>{

	Page<Types> findAll(Pageable pageable);

	Optional<Types> findByName(String name);}
