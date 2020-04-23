package com.formation.projet7.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.stereotype.Service;
import org.thymeleaf.util.DateUtils;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.formation.projet7.model.Emprunt;
import com.formation.projet7.model.Exemplaire;
import com.formation.projet7.model.OuvrageAux;
import com.formation.projet7.model.Utilisateur;
import com.formation.projet7.constants.Constants;

@Service
public class EmpruntOuvrage {
	
	public Emprunt emprunter(List<OuvrageAux> ouvrages, Integer id, Utilisateur user) {
		
		/*
		OuvrageAux ouvrage = ouvrages.get(id);
		Exemplaire[] exemplaires = ouvrage.getExemplaires();
		boolean exemplaireTrouve = false;
		Exemplaire ex = null;
		int i = -1;
		while (!exemplaireTrouve) {
			i++;
			ex = exemplaires[i];
			exemplaireTrouve = ex.isActif() && ex.isDisponible();
		}
		
		LocalDateTime debut =  LocalDateTime.now();
		//LocalDateTime fin = debut.plus(Constants.DELAY_MOUNTS, ChronoUnit.MONTHS);
		LocalDateTime fin = debut.plus(Constants.DELAY_MIN, ChronoUnit.MINUTES);
		Emprunt emprunt = new Emprunt();
		emprunt.setActif(true);
		emprunt.setDebut(debut);
		emprunt.setFin(fin);
		emprunt.setEmprunteur(user);
		emprunt.setProlongation(false);
		emprunt.setExemplaire(ex);
		
		System.out.println("date début: " + debut);
		System.out.println("date fin:   " + fin);
		return emprunt;
		*/
		return null;
	}
	
}
