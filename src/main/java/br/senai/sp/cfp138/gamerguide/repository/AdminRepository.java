package br.senai.sp.cfp138.gamerguide.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.cfp138.gamerguide.model.Administrador;

public interface AdminRepository extends PagingAndSortingRepository<Administrador, Long>{

	public Administrador findByEmailAndSenha(String email, String senha);
	
}
