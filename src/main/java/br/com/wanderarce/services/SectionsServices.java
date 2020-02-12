package br.com.wanderarce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.wanderarce.entities.Releases;
import br.com.wanderarce.entities.Sections;
import br.com.wanderarce.repository.SectionsRepositories;

@Service
public class SectionsServices {

	@Autowired
	private SectionsRepositories sectionsRepository;

	public Page<Sections> findAll(int page, int size) {
		Pageable paging = PageRequest.of(page, size);
		Page<Sections> sections = sectionsRepository.findAll(paging);
		return sections;
	}

	public Optional<Sections> findOne(Long id) {
		Optional<Sections> section = sectionsRepository.findById(id);
		return section;
	}

	public Sections saveOrupdate(Sections entity) {
		Optional<Sections> section = this.sectionsRepository.findByType_Id(entity.getType().getId());
		if (section.isPresent() && section.get().getId() != entity.getId()) {
			return null;
		}
		return this.sectionsRepository.save(entity);
	}

	public Optional<Sections> delete(Long id) {
		Optional<Sections> section = this.sectionsRepository.findById(id);
		if (section.isPresent())
			return this.sectionsRepository.findById(id);
		return null;
	}

	public List<Sections> findAll() {
		return this.sectionsRepository.findAll();
	}

}
