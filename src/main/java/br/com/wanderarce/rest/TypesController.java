package br.com.wanderarce.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.wanderarce.entities.Types;
import br.com.wanderarce.services.TypesServices;

@RestController
@RequestMapping("types")
public class TypesController {

	@Autowired
	private TypesServices typesServices;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Types>> findAll(@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer size) {
		Page<Types> products = typesServices.findAll(page, size);
		return new ResponseEntity<Page<Types>>(products, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Types> findOne(@PathVariable("id") Long id) {
		Optional<Types> result = typesServices.findOne(id);
		Types type = new Types();
		HttpStatus httpStatus;
		if (result.isPresent()) {
			type = result.get();
			httpStatus = HttpStatus.OK;
		} else {
			type = null;
			httpStatus = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<Types>(type, httpStatus);
	}

	// @PostMapping
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Types> save(@RequestBody Types entity) {
		Types type = this.typesServices.saveOrupdate(entity);
		HttpStatus httpStatus = type == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
		return new ResponseEntity<Types>(type, httpStatus);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Types> delete(@PathVariable("id") Long id) {
		Optional<Types> res = this.typesServices.findOne(id);
		if (res.isPresent()) {
			this.typesServices.delete(id);
			return new ResponseEntity<Types>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Types>(HttpStatus.NOT_FOUND);
	}
}
