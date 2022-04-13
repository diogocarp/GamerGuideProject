package br.senai.sp.cfp138.gamerguide.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Game {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@ManyToOne
	private TipoGame tipo;
	@Column(columnDefinition = "TEXT")
	private String descricao;
	@Column(columnDefinition = "TEXT")
	private String fotos;
	private String requisitos;
	private String classificacao;
	private boolean crossplay;
	private boolean multiplayer;
	
	public String[] verFotos() {
		
		return this.fotos.split(";");
		
	}
	
	
	
	
	
}
