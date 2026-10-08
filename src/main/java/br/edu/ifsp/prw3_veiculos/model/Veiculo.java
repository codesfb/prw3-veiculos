package br.edu.ifsp.prw3_veiculos.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Embeddable
public class Veiculo {
    @NotBlank
    private String marca;
    @NotBlank
    private String modelo;

    @Positive
    private int ano;

    @Size(max = 20)
    private String cor;
}