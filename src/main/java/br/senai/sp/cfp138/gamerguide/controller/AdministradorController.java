package br.senai.sp.cfp138.gamerguide.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senai.sp.cfp138.gamerguide.annotation.Publico;
import br.senai.sp.cfp138.gamerguide.model.Administrador;
import br.senai.sp.cfp138.gamerguide.repository.AdminRepository;
import br.senai.sp.cfp138.gamerguide.util.HashUtil;


@Controller
public class AdministradorController {
	
		// repository para persistencia do Administrador 
		//@AutoWired
	
		@Autowired
	 	 private AdminRepository repository;
		
		//requesting mapping para formulario
		@RequestMapping(value = "formAdm",method = RequestMethod.GET )
		public String formAdm() {
			return "adm/form";
			
			
		}
		
		@RequestMapping(value = "salvarAdm",method = RequestMethod.POST )
		public String salvarAdm(@Valid Administrador admin, BindingResult result, RedirectAttributes attr) {
			
			//verifica se houve erro na validação do objeto
			if(result.hasErrors()) {
				
				// envia msg de erro via requisição
				attr.addFlashAttribute("mensagemErro", "Verifique os campo...");
				return "redirect:formAdm";
				
			}
			
			// verifica se esta sendo feita uma alteração ao invés de uma inserção 
			boolean alteracao = admin.getId() != null ? true : false;
			
			// verifica se a senha está vazia
			if(admin.getSenha().equals(HashUtil.hash256(""))) {
				
				// se não for alteração, eu defino a= primeira parte do email como senha 
				if(!alteracao) {
					
					//extrai a parte do email antes do @
					String parte = admin.getEmail().substring(0, admin.getEmail().indexOf("@"));
					
					// define a senha dp admin
					admin.setSenha(parte);
					
				}else {
					
					//busca a senha atual
					String hash = repository.findById(admin.getId()).get().getSenha();
					
					// "seta" a senha com hash 
					admin.setSenhaComHash(hash);
					
				}
				
				
				
			}
			
			try {
			
			//salva o admin
			repository.save(admin);
			attr.addFlashAttribute("mensagemSucesso", "Adminstrador cadastrado com sucesso . ID: "+admin.getId());
			
			} catch(Exception e) {
					attr.addFlashAttribute("mensagemErro","Houve um erro ao cadastrar o Adminsitrador: "+e.getMessage());
				
				
			}
				
			return "redirect:formAdm";
			
		}
		
		//request mapping para listar informando a pagina desejada 
		@RequestMapping("listarAdm/{page}")
		public String listar(Model model, @PathVariable("page") int page) {
			
				// cria um pageblw com 6 elementos por pagina, ordenando os objetos pelo nome, de forma ascendente
				PageRequest pageable = PageRequest.of(page-1, 6, Sort.by(Sort.Direction.ASC, "nome"));
				
				// cria a pagina atual atraves do repository
				Page<Administrador> pagina = repository.findAll(pageable);
				
				// descobrir o total de paginas
				int totalPages = pagina.getTotalPages();
				
				//cria uma lista de insteiros para representar as paginas
				List<Integer> pageNumbers = new ArrayList<Integer>();
				
				//preemcher a lista com as páginas
				for(int i = 0; i < totalPages; i++) {
					pageNumbers.add(i+1);
					
				}
				
				//adiciona as variaveis na Model
				model.addAttribute("admins", pagina.getContent());
				model.addAttribute("paginaAtual", page);
				model.addAttribute("totalPaginas", totalPages);
				model.addAttribute("numPaginas", pageNumbers);
				
				//retorna o html da lista
				return "adm/lista";
			
			
			
		}
		
		@RequestMapping("alterarAdm")
		public String alterarAdm(Model model, Long id) {
			
			Administrador admin = repository.findById(id).get();
			model.addAttribute("adm", admin);
			return "forward:formAdm";
			
			
		}
		
		@Publico
		@RequestMapping("login")
		public String login(Administrador admLogin, RedirectAttributes attr, HttpSession session) {
			
			// buscar o administrador no BD, através do e-mail e da senha
			Administrador admin = repository.findByEmailAndSenha(admLogin.getEmail(), admLogin.getSenha());
			
			// verifica se o adm existe
			if (admin == null) {
				// avisa ao usuario
				attr.addFlashAttribute("mensagemErro", "Login e/ou senha inválido(s)");
				return "redirect:/";
			}else {
				
			// se não for bulo, salva na sessão e acessa o sistema 
				session.setAttribute("usuarioLogado", admin);
				return "redirect:/listarJogo/1";
				
			}
			
		}
		
		
		@RequestMapping("logout")
		public String logout(HttpSession session) {
			
			// elimina o usario da session
			session.invalidate();
			
			// retorna para a página inicial
			
			return "redirect:/";
			
			
		}
		
		@RequestMapping("excluirAdm" )
		public String excluirAdm(Long id) {
				repository.deleteById(id);
				return "redirect:listarAdm/1";
			
			
		}
		
		

}
