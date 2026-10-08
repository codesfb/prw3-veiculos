package br.edu.ifsp.prw3_veiculos.model;

import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


import java.time.LocalDate;

@Data
public class Conserto {
    @NotNull
    @Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}")
    private String dataEntrada;
    @Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}")
    private String dataSaida;
    @Embedded
    private Mecanico mecanicoResponsavel;
    @Embedded
    private Veiculo veiculo;

}
