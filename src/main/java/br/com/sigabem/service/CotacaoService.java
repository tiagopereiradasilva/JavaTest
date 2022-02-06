package br.com.sigabem.service;

import br.com.sigabem.domain.entity.Cotacao;
import br.com.sigabem.domain.repository.CotacaoRepository;
import br.com.sigabem.web.dto.CotacaoRequestDTO;
import br.com.sigabem.web.dto.CotacaoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CotacaoService {
    private CotacaoRepository cotacaoRepository;

    public CotacaoService(@Autowired CotacaoRepository cotacaoRepository) {
        this.cotacaoRepository = cotacaoRepository;
    }

    public CotacaoResponseDTO savar(CotacaoRequestDTO cotacaoRequestDTO){
        return null;
    }

    private CotacaoResponseDTO converterParaCotacaoResponseDTO(Cotacao cotacao){
        return CotacaoResponseDTO
                .builder()
                .cepDestino(cotacao.getCepDestino())
                .cepOrigem(cotacao.getCepOrigem())
                .dataPrevistaEntrega(cotacao.getDataPrevistaEntrega())
                .vlTotalFrete(cotacao.getVlTotalFrete())
                .build();
    }
}
