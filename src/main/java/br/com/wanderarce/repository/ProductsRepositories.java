package br.com.wanderarce.repository;


import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.wanderarce.entities.Products;

@Repository
@Transactional
public interface ProductsRepositories extends JpaRepository<Products, Long>{

	Page<Products> findAll(Pageable pageable);}
