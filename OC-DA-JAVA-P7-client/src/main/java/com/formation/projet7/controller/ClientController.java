package com.formation.projet7.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.formation.projet7.model.Ouvrage;
import com.formation.projet7.model.Utilisateur;
import com.formation.projet7.proxy.MicroServiceOuvrages;

@Controller
@RequestMapping("/biblio/client")
public class ClientController {
	
	@Autowired
	MicroServiceOuvrages microServiceOuvrages;
	
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
		model.addAttribute("authentifier", true);
		return "espace";
	}
	
	@GetMapping("/ouvrages")
	public String listeOuvgrages() {
		
		List<Ouvrage> ouvrages = microServiceOuvrages.tousLesOuvrages();
		System.out.println("Nombre d'ouvrages: " + ouvrages.size());
		System.out.println("---------------------------------");
		System.out.println(ouvrages.get(0).getTitre());
		System.out.println("---------------------------------");
		System.out.println(ouvrages.get(1).getTitre());
		
		return "ok";
	}
	
	

}
