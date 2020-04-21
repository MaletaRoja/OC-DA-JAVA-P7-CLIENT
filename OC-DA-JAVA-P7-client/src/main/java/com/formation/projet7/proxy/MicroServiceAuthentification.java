package com.formation.projet7.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.formation.projet7.model.Login;

@FeignClient(name="biblio-service", url="localhost:8081/biblio/connexion/")
public interface MicroServiceAuthentification {
	
	@PostMapping
	public ResponseEntity<String> generate(@RequestBody final Login login);

}
