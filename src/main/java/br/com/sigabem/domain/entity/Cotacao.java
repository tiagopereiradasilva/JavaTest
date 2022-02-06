package br.com.sigabem.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
public class Cotacao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private float peso;
    @Column(length = 8)
    private String cepOrigem;
    @Column(length = 8)
    private String cepDestino;
    @Column(length = 250)
    private String nomeDestinatario;
    private float vlTotalFrete;
    private LocalDate dataPrevistaEntrega;
    private LocalDate dataConsulta;
}
