package br.com.wanderarce.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.wanderarce.entities.Sections;
import br.com.wanderarce.services.SectionsServices;

@RestController
@RequestMapping("/sections")
public class SectionsController {

	@Autowired
	private SectionsServices sectionsServices;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Sections>> findAll(@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer size) {
		Page<Sections> sections = sectionsServices.findAll(page, size);
		return new ResponseEntity<Page<Sections>>(sections, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Sections> findOne(@PathVariable("id") Long id) {
		Optional<Sections> section = sectionsServices.findOne(id);
		return new ResponseEntity<Sections>(section.get(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Sections> save(@RequestBody Sections entity) {
		HttpStatus httpStatus = null;
		Sections section = new Sections();
		section = this.sectionsServices.saveOrupdate(entity);
		httpStatus = section != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
		
		return new ResponseEntity<Sections>(section, httpStatus);
	}

	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Sections> update(@RequestBody Sections entity) {
		HttpStatus httpStatus = null;
		Sections section = new Sections();
		if (entity.getId() == null) {
			httpStatus = HttpStatus.BAD_REQUEST;
			section = null;
		} else {
			section = this.sectionsServices.saveOrupdate(entity);
			httpStatus = section != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<Sections>(section, httpStatus);
	}

	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Sections> delete(@PathVariable("id") Long id) {

		Optional<Sections> res = this.sectionsServices.findOne(id);
		if (res.isPresent()) {
			this.sectionsServices.delete(id);
			return new ResponseEntity<Sections>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<Sections>(HttpStatus.NOT_FOUND);
		}
	}
}
