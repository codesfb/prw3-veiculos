package br.edu.ifsp.prw3_veiculos.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@Embeddable
public class Mecanico {
    @NotBlank
    private String nome;

    @PositiveOrZero
    private int anosDeExperiencia;
}
