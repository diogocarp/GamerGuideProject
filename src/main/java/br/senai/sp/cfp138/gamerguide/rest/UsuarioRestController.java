package br.senai.sp.cfp138.gamerguide.rest;

import java.net.URI;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.senai.sp.cfp138.gamerguide.annotation.Privado;
import br.senai.sp.cfp138.gamerguide.annotation.Publico;
import br.senai.sp.cfp138.gamerguide.model.Erro;
import br.senai.sp.cfp138.gamerguide.model.TokenJWT;
import br.senai.sp.cfp138.gamerguide.model.Usuario;
import br.senai.sp.cfp138.gamerguide.repository.UsuarioRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/usuario")
public class UsuarioRestController {
	
	// constantes para gerar o token
	public static final String EMISSOR = "Diogo";
	public static final String SECRET = "Diogo@Guide";
	
	

	@Autowired
	private UsuarioRepository repository;
	
	@Publico
	@RequestMapping(value="", method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criarUsuario(@RequestBody Usuario usuario){
		try {
		// salvar o usuário no banco de dados
		repository.save(usuario);
		// retirna código 201, com a URL para acesso no Location e o usuário inserido
		// no corpo da resposta
		return ResponseEntity.created(URI.create("/"+usuario.getId())).body(usuario);
	}catch(DataIntegrityViolationException e){
		e.printStackTrace();
		Erro erro = new Erro();
		erro.setStatusCode(500);
		erro.setMensagem("Erro de Constraint: Registro Duplicado");
		erro.setException(e.getClass().getName());
		return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}catch(Exception e){
		e.printStackTrace();
		Erro erro = new Erro();
		erro.setStatusCode(500);
		erro.setMensagem("Erro de Constraint:" + e.getMessage());
		erro.setException(e.getClass().getName());
		return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		
		
		
		}
		
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizarUsuario(@RequestBody Usuario usuario, @PathVariable("id") Long id){
		// valida o ID
		if(id != usuario.getId()) {
			throw new RuntimeException("ID Inválido");
			
			
		}
		// salva o usuário 
		repository.save(usuario);
		
		//criar um cabeçalho HTTP
		HttpHeaders header = new HttpHeaders();
		header.setLocation(URI.create("/api/usuario/"));
		return new ResponseEntity<Void>(header, HttpStatus.OK);
		
	}
	
	@Privado
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Usuario> findUsuario(@PathVariable("id") Long idUsuario){
		// busca jogo
		Optional<Usuario> usuario = repository.findById(idUsuario);
		if(usuario.isPresent()) {
			
			return ResponseEntity.ok(usuario.get());
		}else {
			
			return ResponseEntity.notFound().build();
			
		}
	
	}
	
	@Privado
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluirUsuario(@PathVariable("id") Long idUsuario){
		
		repository.deleteById(idUsuario);
		return ResponseEntity.noContent().build();

	}
	
	@Publico
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<TokenJWT>
			logar(@RequestBody Usuario usuario){
		// busca o usuario no BD
		usuario = repository.findByEmailAndSenha(usuario.getEmail(), usuario.getSenha());
		
		// verifica se existe o usuario 
		if(usuario != null) {
			Map<String, Object> payload = new HashMap<String, Object>();
			payload.put("id_usuario", usuario.getId());
			payload.put("nome_usuario", usuario.getNome());
			
			// definir a data de expiração
			Calendar expiracao = Calendar.getInstance();
			expiracao.add(Calendar.HOUR, 1);
			
			// algoritmo para assinar o token
			Algorithm algoritmo = Algorithm.HMAC256(SECRET);
			
			// gerar o token 
			TokenJWT tokenJwt = new TokenJWT();
			tokenJwt.setToken
			(JWT.create().withPayload(payload).withIssuer(EMISSOR).
					withExpiresAt(expiracao.getTime()).sign(algoritmo));
			
			return ResponseEntity.ok(tokenJwt);
		}
		
		return new ResponseEntity<TokenJWT>(HttpStatus.UNAUTHORIZED);
		
		
	}
		
	
	
	
	
	
	
	
 }
