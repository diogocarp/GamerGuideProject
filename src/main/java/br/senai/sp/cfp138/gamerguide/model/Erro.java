package br.senai.sp.cfp138.gamerguide.model;

import lombok.Data;

@Data
public class Erro {

	private int statusCode;
	private String mensagem;
	private String exception;
	
}
