package br.senai.sp.cfp138.gamerguide.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import br.senai.sp.cfp138.gamerguide.util.HashUtil;
import lombok.Data;

// para gerar getters and setters
@Data

// para mapear como uma entidade JPA
@Entity
public class Administrador {
	
	// chave primaria e auto incremento
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String nome;
	@Email
	@Column(unique = true)
	private String email; 
	@NotEmpty
	private String senha; 
	
	//metodo para setar a senha aplicando hash
	public void setSenha(String senha) {
		
		// aplica o hash e "seta" a senha no objeto
		this.senha = HashUtil.hash256(senha);
		
		
	}
}
