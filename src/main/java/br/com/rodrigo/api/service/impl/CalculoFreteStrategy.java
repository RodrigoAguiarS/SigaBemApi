package br.com.rodrigo.api.service.impl;

import br.com.rodrigo.api.models.CotacaoFrete;

public interface CalculoFreteStrategy {
    CotacaoFrete calcularFrete(double peso, String cepOrigem, String cepDestino, String nomeDestinatario);
}
