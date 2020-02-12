package br.com.wanderarce.repository;


import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.wanderarce.entities.Sections;

@Repository
@Transactional
public interface SectionsRepositories extends JpaRepository<Sections, Long>{

	Page<Sections> findAll(Pageable pageable);

	Optional<Sections> findByType_Id(Long id);
}
