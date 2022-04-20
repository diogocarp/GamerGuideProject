package br.senai.sp.cfp138.gamerguide.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.cfp138.gamerguide.annotation.Publico;
import br.senai.sp.cfp138.gamerguide.model.TipoGame;
import br.senai.sp.cfp138.gamerguide.repository.GameRepository;

@RequestMapping("/api/tipo")
@RestController
public class TipoRestController {
	
	@Autowired
	private GameRepository repTipo;
	

	
	
	@Publico
	@RequestMapping(value="", method = RequestMethod.GET)
	public Iterable<TipoGame> getTipo(){
		
		return repTipo.findAll();
		
	} 
	
	
	@Publico
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<TipoGame> findTipoGame(@PathVariable("id") Long idTipo){
		// busca tipo
		Optional<TipoGame> tipo = repTipo.findById(idTipo);
		if(tipo.isPresent()) {
			
			return ResponseEntity.ok(tipo.get());
		}else {
			
			return ResponseEntity.notFound().build();
			
		}
	}
	
}