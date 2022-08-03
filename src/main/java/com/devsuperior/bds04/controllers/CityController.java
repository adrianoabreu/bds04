package com.devsuperior.bds04.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.services.CityService;
import com.devsuperior.bds04.services.EventService;

@RestController
@RequestMapping(value = "/cities") //annotation que determina a rota do controlador REST
public class CityController {

	@Autowired
	private CityService service;
	
	//ResponseEntity encapsula respostas HTTP
	@GetMapping  //Determina o Endpoint do controlador REST
	public ResponseEntity<List<CityDTO>> findAll(){		
		//PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("name"));
		List<CityDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@PostMapping
	public ResponseEntity<CityDTO> insert(@Valid @RequestBody CityDTO dto){		
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){		
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
