package br.edu.ifsp.prw3_veiculos.repository;

import br.edu.ifsp.prw3_veiculos.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
}