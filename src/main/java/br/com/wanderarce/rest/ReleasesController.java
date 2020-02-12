package br.com.wanderarce.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.wanderarce.entities.Releases;
import br.com.wanderarce.entities.Sections;
import br.com.wanderarce.services.ProductsServices;
import br.com.wanderarce.services.ReleasesServices;
import br.com.wanderarce.services.SectionsServices;

@RestController
@RequestMapping("releases")
public class ReleasesController {

	@Autowired
	private ReleasesServices releasesServices;
	
	@Autowired
	private SectionsServices sectionsServices;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Releases>> findAll(
			@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer size,
			@RequestParam(defaultValue= "asc") String firstDirection,
			@RequestParam(defaultValue= "asc") String secondDirection
			) {
		Page<Releases> releases = releasesServices.findAll(page, size, firstDirection, secondDirection);
		return new ResponseEntity<Page<Releases>>(releases, HttpStatus.OK);
	}

	@RequestMapping(value="/filter/{type}/{section}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Releases>> findAllFilter(
			@PathVariable("type") Long type,
			@PathVariable("section") Long section,
			@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer size,
			@RequestParam(defaultValue= "asc") String firstDirection,
			@RequestParam(defaultValue= "asc") String secondDirection
			) {
		
		Page<Releases> releases = this.releasesServices.findAllFilters(type, section, page, size, firstDirection, secondDirection);
		
		return new ResponseEntity<Page<Releases>>(releases, HttpStatus.OK);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Releases> findOne(@PathVariable("id") Long id) {
		Optional<Releases> result = releasesServices.findOne(id);
		Releases release = new Releases();
		HttpStatus httpStatus;
		if (result.isPresent()) {
			release = result.get();
			httpStatus = HttpStatus.OK;
		} else {
			release = null;
			httpStatus = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<Releases>(release, httpStatus);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Releases> save(@RequestBody Releases entity) {
		Optional<Releases> rel =  this.releasesServices.findBySection_Id(entity.getSection().getId());
		Optional<Sections> section = this.sectionsServices.findOne(entity.getSection().getId());
		if(section.isPresent()) {
			if(section.get().getCapacity().compareTo(entity.getVolume())== 0) {
				
			}
		}
		Releases release = this.releasesServices.saveOrupdate(entity);
		HttpStatus httpStatus = release == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
		return new ResponseEntity<Releases>(release, httpStatus);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Releases> delete(@PathVariable("id") Long id) {
		Optional<Releases> res = this.releasesServices.findOne(id);
		if (res.isPresent()) {
			this.releasesServices.delete(id);
			return new ResponseEntity<Releases>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Releases>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/total-volume/type/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> sum(@PathVariable("id") Long id) {
		
		Optional<Releases> res = this.releasesServices.findOne(id);
		Long total = 0L;
		if(res.isPresent()) {
			 total = this.releasesServices.sumCurrentVolumeByType(res.get().getType().getId());

		}
		return new ResponseEntity<Long>(total, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/sections-for-storage", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Sections>> sectionsAvailableForStorage() {
		Long input = 0L;
		Long out = 0L;
		Long total = 0L;
		List<Sections> sections = new ArrayList<>();
		sections = this.sectionsServices.findAll();
		for (int i = 0; i < sections.size(); i++) {
			input = this.releasesServices.sumCurrentEntryVolumeByType(sections.get(i).getType().getId());
			out= this.releasesServices.sumCurrentOutVolumeByType(sections.get(i).getType().getId());
			total = input - out;
			if(total >= sections.get(i).getCapacity()) {
				sections.remove(i);
			}
			
		} 
		return new ResponseEntity<List<Sections>>(sections, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/sections-for-sales", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Sections>> sectionsAvailableForSales() {
		Long input = 0L;
		Long out = 0L;
		Long total = 0L;
		List<Sections> sections = new ArrayList<>();
		sections = this.sectionsServices.findAll();
		for (int i = 0; i < sections.size(); i++) {
			input = this.releasesServices.sumCurrentEntryVolumeByType(sections.get(i).getType().getId());
			out= this.releasesServices.sumCurrentOutVolumeByType(sections.get(i).getType().getId());
			total = input - out; 
			if(total==0 || input == 0) {
				sections.remove(i);
			}
			
		} 
		return new ResponseEntity<List<Sections>>(sections, HttpStatus.OK);
	}
}
