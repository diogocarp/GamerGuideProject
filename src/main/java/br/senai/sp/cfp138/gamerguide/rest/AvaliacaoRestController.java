package br.senai.sp.cfp138.gamerguide.rest;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.cfp138.gamerguide.annotation.Privado;
import br.senai.sp.cfp138.gamerguide.annotation.Publico;
import br.senai.sp.cfp138.gamerguide.model.Avaliacao;

import br.senai.sp.cfp138.gamerguide.repository.AvaliacaoRepository;


@RestController
@RequestMapping("/api/avaliacao")
public class AvaliacaoRestController {

	@Autowired
	private AvaliacaoRepository repository;
	

	
	@Privado
	@RequestMapping(value="", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Avaliacao> criarAvaliacao(@RequestBody Avaliacao avaliacao){
		repository.save(avaliacao);
		return ResponseEntity.created(URI.create("/avaliacao/"+avaliacao.getId())).body(avaliacao);
		
		
	}
	
	@Publico
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Avaliacao> findAvaliacao(@PathVariable("id") Long idAvaliacao){
		// busca jogo
		Optional<Avaliacao> avaliacao = repository.findById(idAvaliacao);
		if(avaliacao.isPresent()) {
			
			return ResponseEntity.ok(avaliacao.get());
		}else {
			
			return ResponseEntity.notFound().build();
			
		}
		
	}
		
		@Publico
		@RequestMapping(value="/jogo/{idJogo}", method = RequestMethod.GET)
		public List<Avaliacao> findGameAvaliacao(@PathVariable("idJogo") Long idJogo){
			// busca jogo
			List<Avaliacao> avaliacao = repository.findByJogoId(idJogo);
			return avaliacao;
		
		}	
	
	
	
	
}
