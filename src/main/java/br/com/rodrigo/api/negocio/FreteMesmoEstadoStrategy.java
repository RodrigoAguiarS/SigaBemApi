package br.com.rodrigo.api.negocio;

import br.com.rodrigo.api.models.CotacaoFrete;
import br.com.rodrigo.api.service.impl.CalculoFreteStrategy;
import br.com.rodrigo.api.util.ConstantesValores;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FreteMesmoEstadoStrategy implements CalculoFreteStrategy {

    @Override
    public CotacaoFrete calcularFrete(double peso, String cepOrigem, String cepDestino, String nomeDestinatario) {
        double valorTotalFrete = peso * ConstantesValores.VALOR_POR_KG;
        LocalDate dataPrevistaEntrega = LocalDate.now().plusDays(ConstantesValores.DIAS_ENTREGA_ESTADOS_IGUAIS);
        valorTotalFrete *= ConstantesValores.DESCONTO_ESTADOS_IGUAIS;
        LocalDate dataAtual = LocalDate.now();
        return new CotacaoFrete(valorTotalFrete, peso, dataPrevistaEntrega,
                cepOrigem, cepDestino, nomeDestinatario, dataAtual);
    }
}
