package br.senai.sp.cfp138.gamerguide.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.senai.sp.cfp138.gamerguide.annotation.Privado;
import br.senai.sp.cfp138.gamerguide.annotation.Publico;
import br.senai.sp.cfp138.gamerguide.model.Usuario;
import br.senai.sp.cfp138.gamerguide.rest.UsuarioRestController;


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
			
			// se a request for para api
			if(uri.startsWith("/api")){
				
				// variavel para o token
				String token = null;
				
				// quando for api
				//se for metodo privado 
				
				if(metodoChamado.getMethodAnnotation(Privado.class) != null) {
					
					try {
					
					// obtém o token da request
					token = request.getHeader("Authorization");
					
					// algoritmo para descriptografar
					Algorithm algoritmo = 
							Algorithm.HMAC256(UsuarioRestController.SECRET);
					
					JWTVerifier verifier = 
							JWT.require(algoritmo).withIssuer(UsuarioRestController.EMISSOR).build();
					
					DecodedJWT jwt = verifier.verify(token);
					
					// extrair os dados do payload
					Map<String, Claim> payload = jwt.getClaims();
					System.out.println(payload.get("nome_usuario"));
					}catch (Exception e) {
						
						if(token == null) {
							response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
							
						}else {
							response.sendError(HttpStatus.FORBIDDEN.value(), e.getMessage());
							
						}
						
						return false;
						
					}
					
				}
				
				return true;
				
				
			}else {
			
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
		}
		
		return true;
	}
	
	
}
