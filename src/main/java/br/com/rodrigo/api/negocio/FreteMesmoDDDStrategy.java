package br.com.rodrigo.api.negocio;

import br.com.rodrigo.api.config.AppConfig;
import br.com.rodrigo.api.models.CotacaoFrete;
import br.com.rodrigo.api.models.dto.ViaCepResponse;
import br.com.rodrigo.api.repository.CotacaoFreteRepository;
import br.com.rodrigo.api.service.impl.CalculoFreteStrategy;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FreteMesmoDDDStrategy implements CalculoFreteStrategy {

    // Constantes para valores que podem ser alterados
    private static final double PRECO_PORQUILO = 1.0;
    private static final int DIAS_ENTREGA_PADRAO = 10;
    private static final int DIAS_ENTREGA_SAME_DDD = 1;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppConfig viaCepBaseUrl;

    @Autowired
    private CotacaoFreteRepository cotacaoFreteRepository;

    // Outros serviços e dependências

    @Override
    public CotacaoFrete calcularFrete(double peso, String cepOrigem, String cepDestino, String nomeDestinatario) {
        ViaCepResponse origemResponse = consultarViaCEP(cepOrigem);
        ViaCepResponse destinoResponse = consultarViaCEP(cepDestino);

        boolean mesmoDDD = origemResponse.getDdd().equals(destinoResponse.getDdd());

        double vlTotalFrete = calcularValorTotalFrete(peso, mesmoDDD);
        LocalDate dataPrevistaEntrega = calcularDataPrevistaEntrega(mesmoDDD);

        CotacaoFrete cotacao = criarCotacaoFrete(vlTotalFrete, peso, dataPrevistaEntrega, cepOrigem, cepDestino, nomeDestinatario);

        persistirCotacaoNoBanco(cotacao, peso, nomeDestinatario, origemResponse, destinoResponse);

        return cotacao;
    }

    private ViaCepResponse consultarViaCEP(String cep) {
        String viaCepUrl = viaCepBaseUrl.getCepUrl() + cep + "/json";
        return restTemplate.getForObject(viaCepUrl, ViaCepResponse.class);
    }

    private double calcularValorTotalFrete(double peso, boolean mesmoDDD) {
        double vlTotalFrete = peso * PRECO_PORQUILO;
        if (mesmoDDD) {
            vlTotalFrete *= 0.5;
        }
        return vlTotalFrete;
    }

    private LocalDate calcularDataPrevistaEntrega(boolean mesmoDDD) {
        int diasEntrega = mesmoDDD ? DIAS_ENTREGA_SAME_DDD : DIAS_ENTREGA_PADRAO;
        return LocalDate.now().plusDays(diasEntrega);
    }

    private CotacaoFrete criarCotacaoFrete(double vlTotalFrete, double peso, LocalDate dataPrevistaEntrega, String cepOrigem, String cepDestino, String nomeDestinatario) {
        CotacaoFrete cotacao = new CotacaoFrete();
        cotacao.setValorTotalFrete(vlTotalFrete);
        cotacao.setPeso(peso);
        cotacao.setDataPrevistaEntrega(dataPrevistaEntrega);
        cotacao.setCepOrigem(cepOrigem);
        cotacao.setCepDestino(cepDestino);
        cotacao.setDataConsulta(LocalDate.now());
        cotacao.setNomeDestinatario(nomeDestinatario);
        return cotacao;
    }

    private void persistirCotacaoNoBanco(CotacaoFrete cotacao, double peso, String nomeDestinatario, ViaCepResponse origem, ViaCepResponse destino) {
        CotacaoFrete frete = new CotacaoFrete();
        frete.setPeso(peso);
        frete.setCepOrigem(origem.getCep());
        frete.setCepDestino(destino.getCep());
        frete.setNomeDestinatario(nomeDestinatario);
        frete.setValorTotalFrete(cotacao.getValorTotalFrete());
        frete.setDataPrevistaEntrega(cotacao.getDataPrevistaEntrega());
        frete.setDataConsulta(LocalDate.now());
        cotacaoFreteRepository.save(frete);
    }
}


