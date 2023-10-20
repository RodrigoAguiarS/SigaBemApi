package br.com.rodrigo.api.service;

import br.com.rodrigo.api.models.CotacaoFrete;
import br.com.rodrigo.api.service.impl.CalculoFreteStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CotacaoFreteService {

    private final CalculoFreteStrategy strategy;

    public CotacaoFrete calcularFrete(double peso, String cepOrigem, String cepDestino, String nomeDestinatario) {
        return strategy.calcularFrete(peso, cepOrigem, cepDestino, nomeDestinatario);
    }

}