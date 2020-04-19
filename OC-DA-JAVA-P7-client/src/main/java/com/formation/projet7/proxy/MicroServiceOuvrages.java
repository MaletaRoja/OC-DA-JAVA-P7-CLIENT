package com.formation.projet7.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.formation.projet7.model.ExemplaireDispo;
import com.formation.projet7.model.Ouvrage;

@FeignClient(name="biblio-service", url="localhost:8081/biblio")
public interface MicroServiceOuvrages {
	
	@GetMapping("/ouvrage/liste")
	List<Ouvrage> tousLesOuvrages();
	
	@GetMapping("/ouvrage/{id}")
	ResponseEntity<?> unOuvrage(@PathVariable("id") Integer id);
	
	@GetMapping("/ouvrage/rubriques")
	public List<String> toutesLesRubriques();
	
	@GetMapping("/exemplaire/disponibles")
	public List<ExemplaireDispo> ListerExemplairesDisponibles();

}
