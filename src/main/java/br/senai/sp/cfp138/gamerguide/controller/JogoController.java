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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.senai.sp.cfp138.gamerguide.model.Game;
import br.senai.sp.cfp138.gamerguide.repository.GameRepository;
import br.senai.sp.cfp138.gamerguide.repository.JogoRepository;

@Controller
public class JogoController {
	
	@Autowired
	private JogoRepository repTipo;
	@Autowired
	 private GameRepository repository;
	
	
	//requesting mapping para formulario
		@RequestMapping(value = "formJogo",method = RequestMethod.GET )
		public String formJogo(Model model) {
			model.addAttribute("tipos", repository.findAll());
			return "jogo/form";
			
			
		}
		
		@RequestMapping(value = "salvarJogo",method = RequestMethod.POST )
		public String salvarJogo(Game jogo, @RequestParam("fileFotos") MultipartFile[] fileFotos) {
			System.out.println(fileFotos.length);
			//repTipo.save(jogo);
			return "redirect:listarJogo/1";
			
			
		}
	
	@RequestMapping("listarJogo/{page}")
	public String listar(Model model, @PathVariable("page") int page) {
		
			// cria um pageblw com 6 elementos por pagina, ordenando os objetos pelo nome, de forma ascendente
			PageRequest pageable = PageRequest.of(page-1, 6, Sort.by(Sort.Direction.ASC, "nome"));
			
			// cria a pagina atual atraves do repository
			Page<Game> pagina = repTipo.findAll(pageable);
			
			// descobrir o total de paginas
			int totalPages = pagina.getTotalPages();
			
			//cria uma lista de insteiros para representar as paginas
			List<Integer> pageNumbers = new ArrayList<Integer>();
			
			//preemcher a lista com as páginas
			for(int i = 0; i < totalPages; i++) {
				pageNumbers.add(i+1);
				
			}
			
			//adiciona as variaveis na Model
			model.addAttribute("jogos", pagina.getContent());
			model.addAttribute("paginaAtual", page);
			model.addAttribute("totalPaginas", totalPages);
			model.addAttribute("numPaginas", pageNumbers);
			
			//retorna o html da lista
			return "jogo/lista";
		
		
		
	}
	
	@RequestMapping("alterarJogo")
	public String alterarJogo(Model model, Long id) {
		
		Game jogo = repTipo.findById(id).get();
		model.addAttribute("jogo", jogo);
		return "forward:formJogo";
		
		
	}
	
	@RequestMapping("excluirJogo")
	public String excluirJogo(Long id) {
			repTipo.deleteById(id);
			return "redirect:listarJogo/1";
		
		
	}
	
	
	
}
