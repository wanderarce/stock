package br.com.wanderarce.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.wanderarce.entities.Products;
import br.com.wanderarce.repository.ProductsRepositories;

@Service
public class ProductsServices {

	@Autowired
	private ProductsRepositories productsRepository;

	public Page<Products> findAll(Integer page, Integer size) {
		Pageable paging = PageRequest.of(page, size);
		Page<Products> products = this.productsRepository.findAll(paging);
		return products;
	}
	
	public Optional<Products> findone(Long id) {
		Optional<Products> product = this.productsRepository.findById(id);
		return product;
	}

	public Products saveOrupdate(Products entity) {
		return this.productsRepository.save(entity);
	}
	
	public Optional<Products> delete(Long id) {
		Optional<Products> product = this.productsRepository.findById(id);
		if(product.isPresent())
			return this.productsRepository.findById(id);
		return null;
	}
}
