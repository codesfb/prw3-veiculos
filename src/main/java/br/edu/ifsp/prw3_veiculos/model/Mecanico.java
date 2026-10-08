package br.edu.ifsp.prw3_veiculos.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Mecanico {
    private String nome;
    private int anosDeExperiencia;
}
