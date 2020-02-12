package br.com.wanderarce.services;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.wanderarce.entities.Releases;
import br.com.wanderarce.entities.Sections;
import br.com.wanderarce.entities.Types;
import br.com.wanderarce.repository.ReleasesRepositories;

@Service
public class ReleasesServices {

	@Autowired
	private ReleasesRepositories releasesRepository;

	public Page<Releases> findAll(Integer page, Integer size, String firstDirection, String secondDirection) {

		Pageable paging = PageRequest.of(page, size,
				Sort.by(firstDirection.toLowerCase().equals("asc") ? Sort.DEFAULT_DIRECTION.ASC
						: Sort.DEFAULT_DIRECTION.DESC, "created")
						.and(Sort.by(secondDirection.toLowerCase().equals("asc") ? Sort.DEFAULT_DIRECTION.ASC
								: Sort.DEFAULT_DIRECTION.DESC, "section")));
		Page<Releases> releases = this.releasesRepository.findAll(paging);
		return releases;
	}
	
	public Page<Releases> 
	findAllFilters(Long typeId, Long sectionId, Integer page, Integer size, String firstDirection, String secondDirection){
			ExampleMatcher filter = ExampleMatcher.matching().withMatcher("type_id", match -> match.exact())
					.withMatcher("section_id", match -> match.exact());
			Sections sec = new Sections();
			sec.setId(sectionId);

			Types type = new Types();
			type.setId(typeId);
			Releases releaseFilter = new Releases();
			releaseFilter.setSection(sec);
			releaseFilter.setType(type);

			Pageable paging = PageRequest.of(page, size,
					Sort.by(firstDirection.toLowerCase().equals("asc") ? Sort.DEFAULT_DIRECTION.ASC
							: Sort.DEFAULT_DIRECTION.DESC, "created")
							.and(Sort.by(secondDirection.toLowerCase().equals("asc") ? Sort.DEFAULT_DIRECTION.ASC
									: Sort.DEFAULT_DIRECTION.DESC, "section"))); 
			return this.releasesRepository.findAll(Example.of(releaseFilter, filter), paging);
	}

	public Optional<Releases> findOne(Long id) {
		Optional<Releases> release = this.releasesRepository.findById(id);
		return release;
	}

	public Releases saveOrupdate(Releases entity) {
		if (entity.getId() != null)
			entity.setCreated(LocalDateTime.now());
		else
			entity.setModified(LocalDateTime.now());
		return this.releasesRepository.save(entity);
	}

	public Optional<Releases> delete(Long id) {
		Optional<Releases> release = this.releasesRepository.findById(id);
		if (release.isPresent())
			return this.releasesRepository.findById(id);
		return null;
	}

	public Optional<Releases> findBySection_Id(Long id) {
		return this.releasesRepository.findBySection_Id(id);

	}

	public Long sumCurrentVolumeByType(Long typeId) {
		Long sum = this.releasesRepository.sumCurrentVolumeByType(typeId);
		return sum == null ? 0 : sum;
	}

	public Long sumCurrentEntryVolumeByType(Long typeId) {
		Long sum = this.releasesRepository.sumCurrentEntryVolumeByType(typeId);
		return sum == null ? 0 : sum;
	}

	public Long sumCurrentOutVolumeByType(Long typeId) {
		Long sum = this.releasesRepository.sumCurrentOutVolumeByType(typeId);
		return sum == null ? 0 : sum;
	}

	
}
