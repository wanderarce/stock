package br.com.wanderarce.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.wanderarce.entities.Types;
import br.com.wanderarce.repository.TypesRepositories;

@Service
public class TypesServices {

	@Autowired
	private TypesRepositories typesRepository;

	public Page<Types> findAll(int page, int size) {
		Pageable paging = PageRequest.of(page, size);
		Page<Types> types = typesRepository.findAll(paging);
		return types;
	}
	
	public Optional<Types> findOne(Long id) {
		Optional<Types> type = typesRepository.findById(id);
		return type;
	}
	
	public Optional<Types> findByName(String name) {
		Optional<Types> type = typesRepository.findByName(name);
		return type;
	}

	public Types saveOrupdate(Types entity) {
		Optional<Types> type = this.findByName(entity.getName());
		if(type.isPresent() && (type.get().getId() != null && type.get().getId() != entity.getId()))
			return null;
		return this.typesRepository.save(entity);
	}
	
	public Optional<Types> delete(Long id) {
		Optional<Types> type = this.typesRepository.findById(id);
		if(type.isPresent())
			return this.typesRepository.findById(id);
		return null;
	}
}
