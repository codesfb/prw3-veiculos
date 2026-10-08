package br.edu.ifsp.prw3_veiculos.repository;

import br.edu.ifsp.prw3_veiculos.model.Conserto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsertoRepository extends JpaRepository<Conserto, Long> {
}