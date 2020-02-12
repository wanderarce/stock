package br.com.wanderarce.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.wanderarce.entities.Releases;

@Repository
@Transactional
public interface ReleasesRepositories extends JpaRepository<Releases, Long> {

	Page<Releases> findAll(Pageable pageable);

	Optional<Releases> findBySection_Id(Long id);

	@Query(value = "SELECT sum(volume) from Releases where type_id = ?1", nativeQuery = true)
    Long sumCurrentVolumeByType(Long id);
	
	@Query(value = "SELECT sum(volume) from Releases where movement = "+ "'ENTRADA'" + " AND  type_id = ?1", nativeQuery = true)
    Long sumCurrentEntryVolumeByType(Long id);

	@Query(value = "SELECT sum(volume) from Releases where movement = "+ "'SAIDA'" + " AND  type_id = ?1", nativeQuery = true)
    Long sumCurrentOutVolumeByType(Long typeId);
	
	
}
