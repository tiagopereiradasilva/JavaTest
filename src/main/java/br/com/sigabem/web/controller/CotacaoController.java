package br.com.sigabem.web.controller;

import br.com.sigabem.service.CotacaoService;
import br.com.sigabem.web.dto.CotacaoRequestDTO;
import br.com.sigabem.web.dto.CotacaoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/cotacao")
public class CotacaoController {
    private CotacaoService cotacaoService;

    public CotacaoController(@Autowired CotacaoService cotacaoService) {
        this.cotacaoService = cotacaoService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CotacaoResponseDTO> salvar(@RequestBody CotacaoRequestDTO cotacaoRequestDTO){
        return ResponseEntity.ok(cotacaoService.salvar(cotacaoRequestDTO));
    }
}
