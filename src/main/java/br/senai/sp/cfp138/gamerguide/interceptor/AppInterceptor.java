package br.senai.sp.cfp138.gamerguide.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import br.senai.sp.cfp138.gamerguide.annotation.Publico;


@Component
public class AppInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// vaiavel para descobrir pra onde estão tentando ir
		String uri = request.getRequestURI();
		
		// mostra a URI
		System.out.println(uri);
		
		// verifica se  o handler é um HandlerMethod, o que indica que foi encontrado um método em algum Controller para a requisição
		if(handler instanceof HandlerMethod) {
			// liberar o acesso a página inicial
			if(uri.equals("/")) {
				return true;
				
			}
			if(uri.endsWith("/error")) {
				
				return true;
			}
			// fazer o casting para HandlerMethod 
			HandlerMethod metodoChamado = (HandlerMethod) handler;
			
			// se o metodo for publico libera 
			if(metodoChamado.getMethodAnnotation(Publico.class) != null)  {
				return true;
			}
			
			// verifica s eexiste um usuario logado 
			if(request.getSession().getAttribute("usuarioLogado") !=null) {
				return true;
				
			}else {
				// redireciona para a pagina inicial
				response.sendRedirect("/");
				
			}
			
		}
		
		
		
		return true;
	}
	
	
}
