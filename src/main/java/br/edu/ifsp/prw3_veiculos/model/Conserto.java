package br.edu.ifsp.prw3_veiculos.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Embedded;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "conserto")
public class Conserto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_entrada", nullable = false)
    private String dataEntrada;

    @Column(name = "data_saida", nullable = false)
    private String dataSaida;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "nome", column = @Column(name = "mecanico_nome", nullable = false)),
            @AttributeOverride(name = "anosDeExperiencia", column = @Column(name = "mecanico_anos_de_experiencia", nullable = false))
    })
    private Mecanico mecanicoResponsavel;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "marca", column = @Column(name = "veiculo_marca", nullable = false)),
            @AttributeOverride(name = "modelo", column = @Column(name = "veiculo_modelo", nullable = false)),
            @AttributeOverride(name = "ano", column = @Column(name = "veiculo_ano", nullable = false))
    })
    private Veiculo veiculo;
}
