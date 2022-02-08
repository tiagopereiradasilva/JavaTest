package br.com.sigabem.web.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Getter
@Setter
public class CotacaoRequestDTO {
    @NotNull(message = "Campo peso é obrigatório")
    private float peso;
    @NotBlank(message = "Campo cepOrigem é obrigatório")
    private String cepOrigem;
    @NotBlank(message = "Campo cepDestino é obrigatório")
    private String cepDestino;
    @NotBlank(message = "Campo nomeDestinatario é obrigatório")
    private String nomeDestinatario;
}
