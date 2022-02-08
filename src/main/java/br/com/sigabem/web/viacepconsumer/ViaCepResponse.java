package br.com.sigabem.web.viacepconsumer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViaCepResponse {
    private String ddd;
    private String uf;
    private boolean erro;
}
