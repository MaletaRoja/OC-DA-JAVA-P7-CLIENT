package com.formation.projet7.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.formation.projet7.model.Login;
import com.formation.projet7.model.Utilisateur;
import com.formation.projet7.model.UtilisateurAux;
import com.formation.projet7.proxy.MicroServiceOuvrages;

@Service
public class UserConnexion {
	
	@Autowired
	MicroServiceOuvrages microServiceOuvrages;
	
	
	public Utilisateur identifierUtilisateur(Login login, HttpSession session) {
		
		System.out.println("Username: " + login.getUser());
		System.out.println("password: " + login.getPassword());
		//ResponseEntity<String> tokenBody = microServiceOuvrages.generate(login);
		ResponseEntity<UtilisateurAux> userBody = microServiceOuvrages.generate(login);
		//String token = tokenBody.getBody();
		UtilisateurAux userAux = userBody.getBody();
		System.out.println("Token: " + userAux.getToken() );
		System.out.println("Nom de l'utilisateur récupéré: " + userAux.getNom());
		
		// 
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setId(userAux.getId());
		utilisateur.setNom(userAux.getNom());
		utilisateur.setPrenom(userAux.getPrenom());
		utilisateur.setUsername(userAux.getUsername());
		
		String token = userAux.getToken();
		
		session.setAttribute("USER", utilisateur);
		session.setAttribute("TOKEN", token);
		
		return utilisateur;
		
	}
	
	public Utilisateur obtenirUtilisateur (HttpSession session, Model model) {
		
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("USER");
		if (utilisateur == null) {
			
			System.out.println("aucun utilisateur");
			model.addAttribute("authentification", false);
			
		}else {
			
			System.out.println("Nom de utilisateur session: " + utilisateur.getNom());
			model.addAttribute("utilisateur", utilisateur);
			model.addAttribute("authentification", true);
			
		}
		return utilisateur;
		
	}

}
