package br.edu.ifsp.prw3_veiculos.model;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Mecanico {
    @NotBlank
    private String nome;

    private int anosDeExperiencia;
}
