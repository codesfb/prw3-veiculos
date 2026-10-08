package br.edu.ifsp.prw3_veiculos.dto;

import br.edu.ifsp.prw3_veiculos.model.Conserto;
import br.edu.ifsp.prw3_veiculos.model.Mecanico;
import br.edu.ifsp.prw3_veiculos.model.Veiculo;

public record DadosListagemFull(
        String dataEntrada,
        String dataSaida,
        Mecanico mecanicoResponsavel,
        Veiculo veiculo
) {
    public DadosListagemFull(Conserto conserto) {
        this(
                conserto.getDataEntrada(),
                conserto.getDataSaida(),
                conserto.getMecanicoResponsavel(),
                conserto.getVeiculo()
        );
    }
}
