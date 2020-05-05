package com.formation.projet7.proxy;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.formation.projet7.model.Emprunt;
import com.formation.projet7.model.EmpruntAux;
import com.formation.projet7.model.Exemplaire;
import com.formation.projet7.model.ExemplaireDispo;
import com.formation.projet7.model.Login;
import com.formation.projet7.model.Ouvrage;
import com.formation.projet7.model.OuvrageAux;
import com.formation.projet7.model.Utilisateur;
import com.formation.projet7.model.UtilisateurAux;
import com.formation.projet7.model.auxiliaire.LigneEmprunt;

import feign.Body;
import feign.Headers;

@FeignClient(name="biblio-service", url="localhost:8081/biblio")
public interface MicroServiceOuvrages {
	
	@GetMapping("/ouvrage/liste")
	List<OuvrageAux> tousLesOuvrages(@RequestHeader("Authorization") String token);
	
	@GetMapping("/ouvrage/{id}")
	ResponseEntity<?> unOuvrage(@PathVariable("id") Integer id, @RequestHeader("Authorization") String token);
	
	@GetMapping("/ouvrage/rubriques")
	public List<String> toutesLesRubriques(@RequestHeader("Authorization") String token);
	
	@GetMapping("/exemplaire/disponibles")
	public List<Exemplaire> ListerExemplairesDisponibles(@RequestHeader("Authorization") String token);
	
	@GetMapping("/exemplaire/disponibles/{id}")
	public List<Exemplaire> ListerExemplairesDisponiblesParOuvrage(@PathVariable("id") Integer id, @RequestHeader("Authorization") String token);

	@GetMapping("/ouvrage/liste/rubrique/{rubrique}")
	public List<OuvrageAux> tousLesOuvragesParRubrique(@PathVariable  String rubrique, @RequestHeader("Authorization") String token);
	
	@PutMapping("/emprunts/save")
	void enregistrerEmprunt(EmpruntAux empruntAux, @RequestHeader("Authorization") String token);
	
	@GetMapping("/ouvrage/emprunts/actifs/{id}")
	public List<LigneEmprunt> empruntsActifs(@PathVariable  Integer id, @RequestHeader("Authorization") String token);
	
	@GetMapping("/ouvrage/emprunts/hist/{id}")
	public List<LigneEmprunt> empruntsHist(@PathVariable  Integer id, @RequestHeader("Authorization") String token);
	
	@GetMapping("/prolonger/{id}")	
	void prolonger(@PathVariable  Integer id);
	
	@PutMapping("/modifier/compte/{id}")
	public void modifierCompte(@PathVariable  Integer id, @RequestHeader("Authorization") String token, @RequestBody UtilisateurAux utilisateurAux);
	
	@PostMapping("connexion/")
	public ResponseEntity<UtilisateurAux> generate(@RequestBody final Login login);
		
	@PostMapping("compte/")
	public void creerCompte (UtilisateurAux user);
	
	@GetMapping("/recherche/simple")
	public List<OuvrageAux> rechercheSimple(@RequestHeader("Authorization") String token, @PathVariable  String phrase);
	
}

