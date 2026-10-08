package br.edu.ifsp.prw3_veiculos.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Veiculo {
    @NotBlank
    private String marca;
    @NotBlank
    private String modelo;
    @NotBlank
    private String ano;
}