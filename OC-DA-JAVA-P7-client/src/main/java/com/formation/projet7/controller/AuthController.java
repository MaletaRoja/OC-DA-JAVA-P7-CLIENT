package com.formation.projet7.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.formation.projet7.model.Login;
import com.formation.projet7.proxy.MicroServiceOuvrages;

@Controller
public class AuthController {
	
	@Autowired
	MicroServiceOuvrages microServiceOuvrages;
	
	@GetMapping("/connex")
	public String testConnex() {
		
		Login login = new Login("michel@gmail.com","michel");
		ResponseEntity<String> token = microServiceOuvrages.generate(login);
		
		return "ok";
	}

}
