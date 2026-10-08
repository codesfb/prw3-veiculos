package br.edu.ifsp.prw3_veiculos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

public record DadosAtualizacaoConserto(
        @NotBlank
        @Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}")
        String dataSaida,
        @NotBlank
        String nomeMecanico,
        @NotNull
        @PositiveOrZero
        Integer anosDeExperiencia
) {
}