package br.com.sigabem.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CotacaoRequestDTO {
    private float peso;
    private String cepOrigem;
    private String cepDestino;
    private String nomeDestinatario;
}
