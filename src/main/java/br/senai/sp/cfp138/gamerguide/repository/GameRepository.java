package br.senai.sp.cfp138.gamerguide.repository;

import org.springframework.data.repository.PagingAndSortingRepository;


import br.senai.sp.cfp138.gamerguide.model.TipoGame;

public interface GameRepository extends PagingAndSortingRepository<TipoGame, Long>{

}
