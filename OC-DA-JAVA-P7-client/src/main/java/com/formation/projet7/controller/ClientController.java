package com.formation.projet7.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.formation.projet7.constants.Constants;
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
import com.formation.projet7.proxy.MicroServiceMail;
import com.formation.projet7.proxy.MicroServiceOuvrages;
import com.formation.projet7.service.EmpruntOuvrage;
import com.formation.projet7.service.PageOuvrage;
import com.formation.projet7.service.UserConnexion;

import ch.qos.logback.classic.pattern.Util;

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
	
	@Autowired
	UserConnexion userConnexion;
	
	@GetMapping("/")
	public String accueil() {
		
		return "index";
	}
	
	@GetMapping("/presentation")
	public String presentation() {
		
		return "presentation";
	}
	
	
	@GetMapping("/connexion")     // Accès formulaire de connexion
	public String connexion(Model model) {
		
		model.addAttribute("login", new Login());
		
		return "connexion";
	}
	
	@PostMapping("/connexion")  // Traitement formulaire de connexion
	public String demandeConnexion(Login login, Model model) {
		
		
		Utilisateur utilisateur = userConnexion.identifierUtilisateur(login);
		model.addAttribute("utilisateur", utilisateur);
		model.addAttribute("authentification", true);
		
		return "espace";
	}
	
	
	// Test sécurité
	
	@GetMapping("/access")
	public String access() {
		
		ResponseEntity<List <String>> dataBody = (ResponseEntity<List<String>>) microServiceOuvrages.getInformacionBancaria();
		List<String> datas = dataBody.getBody();
		String data = datas.get(0);
		
		for (int i=0; i<datas.size(); i++) {
		System.out.println("data, " + i +" :" + datas.get(i));
		}
		
		return "ok";
		
	}
	
	@GetMapping("/espace")
	public String espace(Model model) {
		
		Utilisateur utilisateur = new Utilisateur(22, "Lopez", "Michel", "michel@gmail.com", "michel", true, null, null);
		model.addAttribute("utilisateur", utilisateur);
		model.addAttribute("authentification", true);
		return "espace";
	}
	
	@GetMapping("/ouvrages")
	public String listeOuvgrages(Model model) {
		
		List<OuvrageAux> ouvrages = microServiceOuvrages.tousLesOuvrages();
		Utilisateur utilisateur = new Utilisateur(22, "Lopez", "Michel", "michel@gmail.com", "michel", true, null, null);
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
		List<OuvrageAux> ouvrages = microServiceOuvrages.tousLesOuvrages();	
		return "rubriques";
	}
	
	@GetMapping("/emprunter/{id}/{rubrique}")
	public String emprunter(@PathVariable("id") Integer id
		, @PathVariable("rubrique") String rubrique, Model model) {
		
		Utilisateur utilisateur = new Utilisateur(22, "Lopez", "Michel", "michel@gmail.com", "michel", true, null, null);
		EmpruntAux empruntAux = new EmpruntAux();
		empruntAux.setIdUser(utilisateur.getId());
		empruntAux.setNumero(id);
		empruntAux.setRubrique(rubrique);
		
		microServiceOuvrages.enregistrerEmprunt(empruntAux);
		model.addAttribute("enregistrement", true);
		return Constants.CONFIRMATION;
	}
	
	
	@GetMapping("/emprunts/{id}")
	public String voirEmprunts(@PathVariable("id") Integer id, Model model) {
		
		
		Utilisateur utilisateur = new Utilisateur(22, "Lopez", "Michel", "michel@gmail.com", "michel", true, null, null);
		List<LigneEmprunt> emprunts = microServiceOuvrages.empruntsActifs(utilisateur.getId());
		model.addAttribute("utilisateur", utilisateur);
		model.addAttribute("emprunts", emprunts);
		model.addAttribute("historique", false);
		model.addAttribute("authentification", true);
		return Constants.EMPRUNTS;
		
	}
	
	@GetMapping("/emprunts/historique/{id}")
	public String voirTousEmprunts(@PathVariable("id") Integer id, Model model) {
		
		Utilisateur utilisateur = new Utilisateur(22, "Lopez", "Michel", "michel@gmail.com", "michel", true, null, null);
		List<LigneEmprunt> emprunts = microServiceOuvrages.empruntsHist(utilisateur.getId());
		model.addAttribute("utilisateur", utilisateur);
		model.addAttribute("emprunts", emprunts);
		model.addAttribute("historique", true);
		model.addAttribute("authentification", true);
		return Constants.EMPRUNTS;
		
	}
	
	@GetMapping("/prolonger/{id}")
	public String prolonger(@PathVariable("id") Integer id, Model model) {
		
		Utilisateur utilisateur = new Utilisateur(22, "Lopez", "Michel", "michel@gmail.com", "michel", true, null, null);
		List<LigneEmprunt> emprunts = microServiceOuvrages.empruntsActifs(utilisateur.getId());
		LigneEmprunt emprunt = emprunts.get(id);
		Integer idExemplaire = emprunt.getId();
		microServiceOuvrages.prolonger(idExemplaire);
		model.addAttribute("utilisateur", utilisateur);
		model.addAttribute("authentification", true);
		model.addAttribute("enregistrement", false);
		return Constants.CONFIRMATION;
	}
	
	// Simulation service mailing
	
	@GetMapping("/mail")
	public String mail() {
		
		microServiceMail.sendSimpleEmail();
		
		return "ok";
	}
	
	

}
