package br.com.rodrigo.api.rest;

import br.com.rodrigo.api.models.CotacaoFrete;
import br.com.rodrigo.api.models.dto.CotacaoFreteRequest;
import br.com.rodrigo.api.negocio.FreteStrategyFactory;
import br.com.rodrigo.api.service.impl.CalculoFreteStrategy;
import br.com.rodrigo.api.service.impl.CotacaoFreteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CotacaoFreteController {

    private final FreteStrategyFactory freteStrategyFactory;
    private final CotacaoFreteService cotacaoFreteService;

    @PostMapping("/calcular-frete")
    public CotacaoFrete calcularFrete(@RequestBody @Valid CotacaoFreteRequest cotacaoFrete) {

        double peso = cotacaoFrete.getPeso();
        String cepOrigem = cotacaoFrete.getCepOrigem();
        String cepDestino = cotacaoFrete.getCepDestino();
        String nomeDestinatario = cotacaoFrete.getNomeDestinatario();

        CalculoFreteStrategy estrategia = freteStrategyFactory.selecionarEstrategia(cepOrigem, cepDestino);

        CotacaoFrete cotacao = estrategia.calcularFrete(peso, cepOrigem, cepDestino, nomeDestinatario);

        cotacaoFreteService.salvarCotacao(cotacao);

        return cotacao;
    }
}
