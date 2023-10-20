package br.com.rodrigo.api.service.impl;

import br.com.rodrigo.api.models.CotacaoFrete;
import br.com.rodrigo.api.repository.CotacaoFreteRepository;
import org.springframework.stereotype.Service;

@Service
public class CotacaoFreteService {

    private final CotacaoFreteRepository cotacaoFreteRepository;

    public CotacaoFreteService(CotacaoFreteRepository cotacaoFreteRepository) {
        this.cotacaoFreteRepository = cotacaoFreteRepository;
    }

    public void salvarCotacao(CotacaoFrete cotacaoFrete) {
        cotacaoFreteRepository.save(cotacaoFrete);
    }
}
