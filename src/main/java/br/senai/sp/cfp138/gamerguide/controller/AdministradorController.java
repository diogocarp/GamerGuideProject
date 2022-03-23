package br.senai.sp.cfp138.gamerguide.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senai.sp.cfp138.gamerguide.model.Administrador;
import br.senai.sp.cfp138.gamerguide.repository.AdminRepository;

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
			try {
			//salva o admin
			repository.save(admin);
			attr.addFlashAttribute("mensagemSucesso", "Adminstrador cadastrado com sucesso . ID: "+admin.getId());
			
			} catch(Exception e) {
					attr.addFlashAttribute("mensagemErro","Houve um erro ao cadastrar o Adminsitrador: "+e.getMessage());
				
				
			}
				
			return "redirect:formAdm";
		}
		
		/*@RequestMapping(value = "listaAdm")
		public String listarAdm(Model model) {
			
			model.addAttribute("adms", repository.findAll());
			return "adm/lista";
			
		}*/
		
		/*@RequestMapping("alterarAdm")
		public String alterarCliente(Model model, Long id) {
			
		 	Administrador administrador = repository.findById(id).get();
			model.addAttribute("adm", administrador);
			return "forward:formCliente";
			
			
		}*/
		
		/*@RequestMapping("excluirAdminstrador")
		public String excluirCliente(Long id) {
				repository.deleteById(id);
				return "redirect:listaCliente";
			
			
		}*/

}
