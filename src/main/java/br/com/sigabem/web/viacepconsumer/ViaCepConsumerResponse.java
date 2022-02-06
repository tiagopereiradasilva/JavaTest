package br.com.sigabem.web.viacepconsumer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViaCepConsumerResponse {
    private String dddOrigem;
    private String ufOrigem;
    private String dddDestinatario;
    private String ufDestinatario;
}
