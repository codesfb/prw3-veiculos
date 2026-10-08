package br.edu.ifsp.prw3_veiculos.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Veiculo {
    private String marca;
    private String modelo;
    private int ano;
}