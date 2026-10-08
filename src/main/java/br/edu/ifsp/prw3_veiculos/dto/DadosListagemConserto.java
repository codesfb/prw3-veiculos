package br.edu.ifsp.prw3_veiculos.dto;

import br.edu.ifsp.prw3_veiculos.model.Conserto;

public record DadosListagemConserto(
        Long id,
        String dataEntrada,
        String dataSaida,
        String nomeMecanico,
        String marca,
    String modelo,
    String cor
) {
    public DadosListagemConserto(Conserto conserto) {
        this(
                conserto.getId(),
                conserto.getDataEntrada(),
                conserto.getDataSaida(),
                conserto.getMecanicoResponsavel().getNome(),
                conserto.getVeiculo().getMarca(),
                conserto.getVeiculo().getModelo(),
                conserto.getVeiculo().getCor()
        );
    }
}
