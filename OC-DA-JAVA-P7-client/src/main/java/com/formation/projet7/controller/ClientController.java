package com.formation.projet7.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.formation.projet7.model.Ouvrage;
import com.formation.projet7.model.Utilisateur;
import com.formation.projet7.proxy.MicroServiceMail;
import com.formation.projet7.proxy.MicroServiceOuvrages;

@Controller
@RequestMapping("/biblio/client")
public class ClientController {
	
	@Autowired
	MicroServiceOuvrages microServiceOuvrages;
	
	@Autowired
	MicroServiceMail microServiceMail;
	
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
		
		List<Ouvrage> ouvrages = microServiceOuvrages.tousLesOuvrages();
		System.out.println("Nombre d'ouvrages: " + ouvrages.size());
		System.out.println("---------------------------------");
		System.out.println(ouvrages.get(0).getTitre());
		System.out.println("---------------------------------");
		System.out.println(ouvrages.get(1).getTitre());
		Utilisateur utilisateur = new Utilisateur(1, "Lopez", "Michel", "michel@gmail.com", "michel", true, null, null);
		model.addAttribute("ouvrages", ouvrages);
		model.addAttribute("utilisateur", utilisateur);
		model.addAttribute("authentification", true);
		return "ouvrages";
	}
	
	
	@GetMapping("/rubriques")
	public String rubriques(Model model) {
		
		List<String> genres = microServiceOuvrages.toutesLesRubriques();
		Utilisateur utilisateur = new Utilisateur(1, "Lopez", "Michel", "michel@gmail.com", "michel", true, null, null);
		model.addAttribute("genres", genres);
		model.addAttribute("utilisateur", utilisateur);
		model.addAttribute("authentification", true);
		//System.out.println("***taille liste genre : " + genres.size());
		return "rubriques";
	}
	
	@PostMapping("/rubriques")
	public String choixRubrique(String rubrique) {
		
		System.out.println("Choix rubrique: " + rubrique);
		return "ok";
		
	}
	
	// Simulation service mailing
	
	@GetMapping("/mail")
	public String mail() {
		
		microServiceMail.sendSimpleEmail();
		
		return "ok";
	}
	
	

}
