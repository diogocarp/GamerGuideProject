package br.senai.sp.cfp138.gamerguide.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.cfp138.gamerguide.model.Avaliacao;


public interface AvaliacaoRepository extends PagingAndSortingRepository<Avaliacao, Long> {

	public List<Avaliacao> findByJogoId(Long idJogo);
	
	
}
