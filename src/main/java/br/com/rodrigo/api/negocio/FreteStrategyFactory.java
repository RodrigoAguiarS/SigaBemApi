package br.com.rodrigo.api.negocio;

import br.com.rodrigo.api.config.AppConfig;
import br.com.rodrigo.api.models.dto.ViaCepResponse;
import br.com.rodrigo.api.service.impl.CalculoFreteStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FreteStrategyFactory {
    private final FreteMesmoDDDStrategy mesmoDDDStrategy;
    private final FreteMesmoEstadoStrategy mesmoEstadoStrategy;
    private final FreteEstadosDiferentesStrategy estadosDiferentesStrategy;
    private final RestTemplate restTemplate;
    private final AppConfig viaCepBaseUrl;

    @Autowired
    public FreteStrategyFactory(FreteMesmoDDDStrategy mesmoDDDStrategy, FreteMesmoEstadoStrategy mesmoEstadoStrategy, FreteEstadosDiferentesStrategy estadosDiferentesStrategy, RestTemplate restTemplate, AppConfig viaCepBaseUrl) {
        this.mesmoDDDStrategy = mesmoDDDStrategy;
        this.mesmoEstadoStrategy = mesmoEstadoStrategy;
        this.estadosDiferentesStrategy = estadosDiferentesStrategy;
        this.restTemplate = restTemplate;
        this.viaCepBaseUrl = viaCepBaseUrl;
    }

    public CalculoFreteStrategy selecionarEstrategia(String cepOrigem, String cepDestino) {
        ViaCepResponse origemResponse = consultarViaCEP(cepOrigem);
        ViaCepResponse destinoResponse = consultarViaCEP(cepDestino);

        if (origemResponse.getDdd().equals(destinoResponse.getDdd())) {
            return mesmoDDDStrategy;
        } else if (origemResponse.getUf().equals(destinoResponse.getUf())) {
            return mesmoEstadoStrategy;
        } else {
            return estadosDiferentesStrategy;
        }
    }

    private ViaCepResponse consultarViaCEP(String cep) {
        String viaCepUrl = viaCepBaseUrl.getCepUrl() + cep + "/json";
        return restTemplate.getForObject(viaCepUrl, ViaCepResponse.class);
    }
}
