package br.edu.ifsp.prw3_veiculos.model;

import jakarta.persistence.Embedded;
import lombok.Data;


import java.time.LocalDate;

@Data
public class Conserto {
    private LocalDate dataEntrada;
    private LocalDate dataSaida;
    @Embedded
    private Mecanico mecanicoResponsavel;
    @Embedded
    private Veiculo veiculo;

}
