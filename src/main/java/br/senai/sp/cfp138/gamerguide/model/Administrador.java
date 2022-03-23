package br.senai.sp.cfp138.gamerguide.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

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
}
