package br.senai.sp.cfp138.gamerguide.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.cfp138.gamerguide.model.Game;

public interface JogoRepository extends PagingAndSortingRepository<Game, Long>{

}
