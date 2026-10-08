package br.edu.ifsp.prw3_veiculos.dto;

import br.edu.ifsp.prw3_veiculos.model.Conserto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosListagemConserto(
        String dataEntrada,
        String dataSaida,
        String nomeMecanico,
        String marca,
        String modelo
) {
    // Se não tiver isso, aquele ::new lá no controller não consegue converter!
    public DadosListagemConserto(Conserto conserto) {
        this(
                conserto.getDataEntrada(),
                conserto.getDataSaida(),
                conserto.getMecanicoResponsavel().getNome(),
                conserto.getVeiculo().getMarca(),
                conserto.getVeiculo().getModelo()
        );
    }
}
