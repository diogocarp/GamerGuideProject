package br.senai.sp.cfp138.gamerguide.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import br.senai.sp.cfp138.gamerguide.model.TipoGame;
import br.senai.sp.cfp138.gamerguide.repository.GameRepository;

@Controller
public class GameController {

	
	@Autowired
	 private GameRepository repository;
	
	//requesting mapping para formulario
	@RequestMapping(value = "formGame",method = RequestMethod.GET )
	public String formGame() {
		return "game/form";
		
		
	}
	
	@RequestMapping(value = "salvarGame",method = RequestMethod.POST )
	public String salvarGame(TipoGame game) {
		repository.save(game);
		return "redirect:listarGame/1";
		
		
	}
	
	@RequestMapping(value="indexGame")
	public String indexGame() {
		return "Index";
	}
	
	//request mapping para listar informando a pagina desejada 
	@RequestMapping("listarGame/{page}")
	public String listar(Model model, @PathVariable("page") int page) {
		
			// cria um pageblw com 6 elementos por pagina, ordenando os objetos pelo nome, de forma ascendente
			PageRequest pageable = PageRequest.of(page-1, 6, Sort.by(Sort.Direction.ASC, "nome"));
			
			// cria a pagina atual atraves do repository
			Page<TipoGame> pagina = repository.findAll(pageable);
			
			// descobrir o total de paginas
			int totalPages = pagina.getTotalPages();
			
			//cria uma lista de insteiros para representar as paginas
			List<Integer> pageNumbers = new ArrayList<Integer>();
			
			//preemcher a lista com as páginas
			for(int i = 0; i < totalPages; i++) {
				pageNumbers.add(i+1);
				
			}
			
			//adiciona as variaveis na Model
			model.addAttribute("games", pagina.getContent());
			model.addAttribute("paginaAtual", page);
			model.addAttribute("totalPaginas", totalPages);
			model.addAttribute("numPaginas", pageNumbers);
			
			//retorna o html da lista
			return "game/lista";
		
		
		
	}
	
	@RequestMapping("alterarGame")
	public String alterarGame(Model model, Long id) {
		
		TipoGame game = repository.findById(id).get();
		model.addAttribute("game", game);
		return "forward:formGame";
		
		
	}
	
	@RequestMapping("excluirGame")
	public String excluirGame(Long id) {
			repository.deleteById(id);
			return "redirect:listarGame/1";
		
		
	}
	
	@RequestMapping("buscarGame")
	public String buscarGame(String palavraChave, Model model) {
		
			model.addAttribute("games", repository.buscarChave("%"+palavraChave+"%"));
			return "game/lista";
		
		
	}
	
	
	
	
}
