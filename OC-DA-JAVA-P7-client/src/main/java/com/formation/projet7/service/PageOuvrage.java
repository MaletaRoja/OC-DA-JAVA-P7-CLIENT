package com.formation.projet7.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.formation.projet7.model.Exemplaire;
import com.formation.projet7.model.OuvrageAux;

@Service
public class PageOuvrage {
	
	public List<Integer> exemplairesDisposParOuvrage(List<OuvrageAux> ouvrages){
	List<Integer> nbreExemplairesDispos = new ArrayList<Integer>();
	
	for (OuvrageAux ouvrage: ouvrages) {
		
		int disponibles = ouvrage.getOffrable(); 
		nbreExemplairesDispos.add(disponibles);
	}
	
	return nbreExemplairesDispos;
}
}