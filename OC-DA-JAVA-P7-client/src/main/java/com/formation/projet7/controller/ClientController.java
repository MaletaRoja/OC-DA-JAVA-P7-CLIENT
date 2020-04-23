package com.formation.projet7.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.formation.projet7.model.Emprunt;
import com.formation.projet7.model.EmpruntAux;
import com.formation.projet7.model.Exemplaire;
import com.formation.projet7.model.ExemplaireDispo;
import com.formation.projet7.model.Ouvrage;
import com.formation.projet7.model.OuvrageAux;
import com.formation.projet7.model.Utilisateur;
import com.formation.projet7.proxy.MicroServiceMail;
import com.formation.projet7.proxy.MicroServiceOuvrages;
import com.formation.projet7.service.EmpruntOuvrage;
import com.formation.projet7.service.PageOuvrage;

@Controller
@RequestMapping("/biblio/client")
public class ClientController {
	
	@Autowired
	MicroServiceOuvrages microServiceOuvrages;
	
	@Autowired
	MicroServiceMail microServiceMail;
	
	@Autowired
	PageOuvrage pageOuvrage;
	
	@Autowired
	EmpruntOuvrage empruntOuvrage;
	
	@GetMapping("/")
	public String accueil() {
		
		return "index";
	}
	
	@GetMapping("/presentation")
	public String presentation() {
		
		return "presentation";
	}
	
	@GetMapping("/connexion")
	public String connexion(Model model) {
		
		Utilisateur utilisateur = new Utilisateur(1, "Lopez", "Michel", "michel@gmail.com", "michel", true, null, null);
		model.addAttribute("utilisateur", utilisateur);
		model.addAttribute("authentification", true);
		return "espace";
	}
	
	@GetMapping("/espace")
	public String espace(Model model) {
		
		Utilisateur utilisateur = new Utilisateur(1, "Lopez", "Michel", "michel@gmail.com", "michel", true, null, null);
		model.addAttribute("utilisateur", utilisateur);
		model.addAttribute("authentification", true);
		return "espace";
	}
	
	@GetMapping("/ouvrages")
	public String listeOuvgrages(Model model) {
		
		List<OuvrageAux> ouvrages = microServiceOuvrages.tousLesOuvrages();
		Utilisateur utilisateur = new Utilisateur(1, "Lopez", "Michel", "michel@gmail.com", "michel", true, null, null);
		List<Integer> nbreExemplairesDispos = pageOuvrage.exemplairesDisposParOuvrage(ouvrages);
		model.addAttribute("ouvrages", ouvrages);
		model.addAttribute("utilisateur", utilisateur);
		model.addAttribute("authentification", true);
		model.addAttribute("nbreExemplairesDispos", nbreExemplairesDispos);
		model.addAttribute("rubrique", "toutes");
		return "ouvrages";
	}
	
	
	@GetMapping("/rubriques")     // Demande choix de rubrique pour affichage des ouvrages correspondants
	public String rubriques(Model model) {
		
		List<String> genres = microServiceOuvrages.toutesLesRubriques();
		Utilisateur utilisateur = new Utilisateur(22, "Lopez", "Michel", "michel@gmail.com", "michel", true, null, null);
		model.addAttribute("genres", genres);
		model.addAttribute("utilisateur", utilisateur);
		model.addAttribute("authentification", true);
		return "rubriques";
	}
	
	@PostMapping("/rubriques")    // Affichage des ouvrages par rubrique/genre
	public String choixRubrique(String rubrique, Model model) {
		
		List<OuvrageAux> ouvrages = microServiceOuvrages.tousLesOuvragesParRubrique(rubrique);
		Utilisateur utilisateur = new Utilisateur(22, "Lopez", "Michel", "michel@gmail.com", "michel", true, null, null);
		List<Integer> nbreExemplairesDispos = pageOuvrage.exemplairesDisposParOuvrage(ouvrages);
		model.addAttribute("ouvrages", ouvrages);
		model.addAttribute("rubrique", rubrique);
		model.addAttribute("utilisateur", utilisateur);
		model.addAttribute("authentification", true);
		model.addAttribute("nbreExemplairesDispos", nbreExemplairesDispos);
		return "ouvrages";
		
	}
	
	@GetMapping("/exemplaire/disponibles")
	public String listeExemplairesDisponibles() {
		
		List<Exemplaire> exemplaireDisponibles = microServiceOuvrages.ListerExemplairesDisponibles();
		System.out.println("taille liste exemplaires dispo: " + exemplaireDisponibles.size());
		List<OuvrageAux> ouvrages = microServiceOuvrages.tousLesOuvrages();
		System.out.println("taille liste ouvrages: " + ouvrages.size());
		return "rubriques";
	}
	
	@GetMapping("/emprunter/{id}/{rubrique}")
	public String emprunter(@PathVariable("id") Integer id
		, @PathVariable("rubrique") String rubrique) {
		
		Utilisateur utilisateur = new Utilisateur(22, "Lopez", "Michel", "michel@gmail.com", "michel", true, null, null);
		EmpruntAux empruntAux = new EmpruntAux();
		empruntAux.setIdUser(utilisateur.getId());
		empruntAux.setNumero(id);
		empruntAux.setRubrique(rubrique);
		
		System.out.println("id user" + empruntAux.getIdUser());
		System.out.println("rubrique: " + empruntAux.getRubrique());
		System.out.println("num" + empruntAux.getNumero());
		microServiceOuvrages.enregistrerEmprunt(empruntAux);
		
		return "ok";
	}
	
	// Simulation service mailing
	
	@GetMapping("/mail")
	public String mail() {
		
		microServiceMail.sendSimpleEmail();
		
		return "ok";
	}
	
	

}
