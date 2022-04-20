package br.senai.sp.cfp138.gamerguide.repository;


import java.util.List;


import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.cfp138.gamerguide.model.Game;

public interface JogoRepository extends PagingAndSortingRepository<Game, Long>{
	
	public List<Game> findByTipoId(Long idTipo);
	
	public List<Game> findByMultiplayer(boolean a);
	
	public List<Game> findByCrossplay(boolean b);

}
