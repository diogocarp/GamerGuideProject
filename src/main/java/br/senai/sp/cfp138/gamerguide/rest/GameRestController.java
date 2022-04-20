package br.senai.sp.cfp138.gamerguide.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.cfp138.gamerguide.annotation.Publico;
import br.senai.sp.cfp138.gamerguide.model.Game;
import br.senai.sp.cfp138.gamerguide.repository.JogoRepository;

@RequestMapping("/api/jogo")
@RestController
public class GameRestController {
	
	@Autowired
	private JogoRepository repository;
	
	
	@Publico
	@RequestMapping(value="", method = RequestMethod.GET)
	public Iterable<Game> getJogos(){
		
		return repository.findAll();
		
	} 
	
	@Publico
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Game> findGame(@PathVariable("id") Long idGame){
		// busca jogo
		Optional<Game> game = repository.findById(idGame);
		if(game.isPresent()) {
			
			return ResponseEntity.ok(game.get());
		}else {
			
			return ResponseEntity.notFound().build();
			
		}
		
	
		

	}
	
	@Publico
	@RequestMapping(value="/tipo/{idTipo}", method = RequestMethod.GET)
	public Iterable<Game> findGameTipo(@PathVariable("idTipo") Long idTipo){
		// busca jogo
		List<Game> game = repository.findByTipoId(idTipo);
		return game;
	
	}	
	
	
	@Publico
	@RequestMapping(value="/multiplayer/{a}", method = RequestMethod.GET)
	public List<Game> findGameMultiplayer(@PathVariable("a") boolean a){
		List<Game> game = repository.findByMultiplayer(a);
		// busca jogo
		if(a == true) {
			
			return game;
			
		}else {
		return game;
		}
	}	
	
	@Publico
	@RequestMapping(value="/crossplay/{b}", method = RequestMethod.GET)
	public List<Game> findGameCrossplay(@PathVariable("b") boolean b){
		List<Game> game = repository.findByCrossplay(b);
		// busca jogo
		if(b == true) {
			
			return game;
			
		}else {
			return game;
		}
	}
}
