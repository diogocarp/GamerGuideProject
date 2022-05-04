package br.senai.sp.cfp138.gamerguide.repository;

import org.springframework.data.repository.PagingAndSortingRepository;


import br.senai.sp.cfp138.gamerguide.model.Usuario;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long>{

	public Usuario findByEmailAndSenha(String email, String senha);

}
