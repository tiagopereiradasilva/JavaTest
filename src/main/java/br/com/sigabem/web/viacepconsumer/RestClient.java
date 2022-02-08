package br.com.sigabem.web.viacepconsumer;

import br.com.sigabem.exception.ViaCepConsumerException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

public class RestClient {

    private WebClient webClient;
    private String baseUrl;

    public RestClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.webClient = WebClient.builder()
                .baseUrl(this.baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public ViaCepConsumerResponse doGet(String endpoint, Map<String, String> path){
        Mono<ViaCepResponse> monoResponse1 = this.webClient
                .get()
                .uri(endpoint, path.get("cepOrigem"))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new ViaCepConsumerException("Cep de origem é inválido")))
                .bodyToMono(ViaCepResponse.class);


        Mono<ViaCepResponse> monoResponse2 = this.webClient
                .get()
                .uri(endpoint, path.get("cepDestinatario"))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new ViaCepConsumerException("Cep de destino é inválido")))
                .bodyToMono(ViaCepResponse.class);

        ViaCepConsumerResponse viaCepConsumerResponse = Mono.zip(monoResponse1, monoResponse2).map(responses -> {
            if(responses.getT1().isErro() || responses.getT2().isErro()){
                throw new ViaCepConsumerException("Certifique-se de que os ceps informados são existentes");
            }
            ViaCepConsumerResponse response = new ViaCepConsumerResponse();
            response.setDddOrigem(responses.getT1().getDdd());
            response.setUfOrigem(responses.getT1().getUf());
            response.setDddDestinatario(responses.getT2().getDdd());
            response.setUfDestinatario(responses.getT2().getUf());
            return response;
        }).block();
        return viaCepConsumerResponse;
    }
}
