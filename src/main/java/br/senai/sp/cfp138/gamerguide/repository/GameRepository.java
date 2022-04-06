package br.senai.sp.cfp138.gamerguide.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.senai.sp.cfp138.gamerguide.model.TipoGame;

public interface GameRepository extends PagingAndSortingRepository<TipoGame, Long>{

	@Query("SELECT g FROM TipoGame g WHERE g.palavraChave LIKE %:p%")
	public List<TipoGame>buscarChave(@Param("p")String palavraChave);
	
	public List<TipoGame> findAllByOrderByNomeAsc();
}
