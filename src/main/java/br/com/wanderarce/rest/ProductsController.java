package br.com.wanderarce.rest;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.wanderarce.entities.Products;
import br.com.wanderarce.services.ProductsServices;

@RestController
@RequestMapping("products")
public class ProductsController {

	@Autowired
	private ProductsServices prodcutsServices;

	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Products>> findAll(@RequestParam(value ="page", defaultValue = "0") Integer page, 
            @RequestParam(value="size", defaultValue = "10") Integer size) {
		Page<Products> products = this.prodcutsServices.findAll(page, size);
		return new ResponseEntity<Page<Products>>(products, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Products> findOne(@PathVariable("id") Long id) {
		Optional<Products> result = this.prodcutsServices.findone(id);
		HttpStatus httpStatus = null;
		
		if(result.isPresent()) {
			httpStatus = HttpStatus.OK;
		} else {
			httpStatus = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<Products>(result.orElse(null), httpStatus);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Products> save(@RequestBody Products entity) {
		Products product = new Products();
	
		product = prodcutsServices.saveOrupdate(entity);
		HttpStatus status =  product == null ? HttpStatus.BAD_GATEWAY : HttpStatus.CREATED;
		return new ResponseEntity<Products>(product, status);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Products> delete(@PathVariable("id") Long id) {
		Optional<Products> result = this.prodcutsServices.findone(id);
		if(result.isPresent()) {
			return new ResponseEntity<Products>(HttpStatus.NOT_FOUND);
		} else {
			this.prodcutsServices.delete(id);
			return new ResponseEntity<Products>(HttpStatus.NO_CONTENT);
		}
	}
}
