package br.com.sigabem.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class CotacaoResponseDTO {
    private float vlTotalFrete;
    private LocalDate dataPrevistaEntrega;
    private String cepOrigem;
    private String cepDestino;
}
