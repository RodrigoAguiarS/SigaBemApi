package br.com.rodrigo.api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CotacaoFrete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double peso;

    private String cepOrigem;

    private String cepDestino;

    private String nomeDestinatario;

    private double valorTotalFrete;

    private LocalDate dataPrevistaEntrega;

    private LocalDate dataConsulta;
}
