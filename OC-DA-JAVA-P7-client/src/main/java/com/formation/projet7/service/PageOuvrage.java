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
	int nombre = 0;
	for (OuvrageAux ouvrage: ouvrages) {
		
		Exemplaire[] exs = ouvrage.getExemplaires(); 
		for (int i=0; i<exs.length; i++) {
			Exemplaire ex = exs[i];
			boolean offrable = ex.isActif() && ex.isDisponible();
			if (offrable) {
				
				nombre++;
			}
			
		}
		nbreExemplairesDispos.add(nombre);
		nombre = 0;
	}
	
	return nbreExemplairesDispos;
}
}