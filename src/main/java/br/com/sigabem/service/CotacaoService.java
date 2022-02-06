package br.com.sigabem.service;

import br.com.sigabem.domain.entity.Cotacao;
import br.com.sigabem.domain.repository.CotacaoRepository;
import br.com.sigabem.util.Helper;
import br.com.sigabem.web.dto.CotacaoRequestDTO;
import br.com.sigabem.web.dto.CotacaoResponseDTO;
import br.com.sigabem.web.viacepconsumer.RestClient;
import br.com.sigabem.web.viacepconsumer.ViaCepConsumerResponse;
import br.com.sigabem.web.viacepconsumer.ViaCepResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class CotacaoService {
    private CotacaoRepository cotacaoRepository;
    private RestClient restClient;

    public CotacaoService(@Autowired CotacaoRepository cotacaoRepository) {
        this.cotacaoRepository = cotacaoRepository;
        this.restClient = new RestClient("viacep.com.br/ws/");
    }

    public CotacaoResponseDTO salvar(CotacaoRequestDTO cotacaoRequestDTO){
        Cotacao cotacao = consumirViaCep(cotacaoRequestDTO);
        cotacaoRepository.save(cotacao);
        return converterParaCotacaoResponseDTO(cotacao);
    }

    private CotacaoResponseDTO converterParaCotacaoResponseDTO(Cotacao cotacao){
        return CotacaoResponseDTO
                .builder()
                .cepDestino(cotacao.getCepDestino())
                .cepOrigem(cotacao.getCepOrigem())
                .dataPrevistaEntrega(Helper.formatarData(cotacao.getDataPrevistaEntrega(), "dd-MM-yyyy"))
                .vlTotalFrete(cotacao.getVlTotalFrete())
                .build();
    }

    private Cotacao consumirViaCep(CotacaoRequestDTO cotacaoRequestDTO){
        Map<String, String> ceps = new HashMap<>();
        ceps.put("cepOrigem", cotacaoRequestDTO.getCepOrigem());
        ceps.put("cepDestinatario", cotacaoRequestDTO.getCepDestino());
        ViaCepConsumerResponse viaCepConsumerResponse = restClient.doGet("/{cep}/json", ceps);
        return criarCotacao(viaCepConsumerResponse, cotacaoRequestDTO);
    }

    private Cotacao criarCotacao(ViaCepConsumerResponse viaCepConsumerResponse, CotacaoRequestDTO cotacaoRequestDTO){

        Cotacao cotacao = new Cotacao();
        cotacao.setPeso(cotacaoRequestDTO.getPeso());
        cotacao.setCepOrigem(cotacaoRequestDTO.getCepOrigem());
        cotacao.setCepDestino(cotacaoRequestDTO.getCepDestino());
        cotacao.setNomeDestinatario(cotacaoRequestDTO.getNomeDestinatario());
        cotacao.setVlTotalFrete(calcularFrete(viaCepConsumerResponse, cotacao.getPeso()));
        cotacao.setDataConsulta(LocalDate.now());
        cotacao.setDataPrevistaEntrega(calcularPrazoDeEntrega(viaCepConsumerResponse, cotacao.getDataConsulta()));
        return cotacao;
    }

    private float calcularFrete(ViaCepConsumerResponse viaCepConsumerResponse, float peso){
        float valorTotal = peso;

        if(viaCepConsumerResponse.getDddOrigem().equals(viaCepConsumerResponse.getDddDestinatario())){
            valorTotal -= Helper.porcentagem(peso, 50);
            return valorTotal;
        }

        if (viaCepConsumerResponse.getUfOrigem().equals(viaCepConsumerResponse.getUfDestinatario())){
            valorTotal -= Helper.porcentagem(peso, 75);
            return valorTotal;
        }
        return valorTotal;
    }

    private LocalDate calcularPrazoDeEntrega(ViaCepConsumerResponse viaCepConsumerResponse, LocalDate data){

        if(viaCepConsumerResponse.getDddOrigem().equals(viaCepConsumerResponse.getDddDestinatario())){
            return Helper.incrementarDiasEmData(data, 1);
        }

        if (viaCepConsumerResponse.getUfOrigem().equals(viaCepConsumerResponse.getUfDestinatario())){
            return Helper.incrementarDiasEmData(data, 3);
        }

        return Helper.incrementarDiasEmData(data, 10);
    }
}
