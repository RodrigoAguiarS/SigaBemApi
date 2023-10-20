package br.com.rodrigo.api.models.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class FreteDto {

    private Long id;

    @NotNull(message = "O campo peso é obrigatório")
    @Min(value = 1, message = "O peso não pode ser menor que 1")
    private double peso;

    @NotNull(message = "O campo cepOrigem é obrigatório")
    @Size(min = 8, max = 8, message = "O cep de origem deve conter 8 caracteres")
    private String cepOrigem;

    @NotNull(message = "O campo cepDestino é obrigatório")
    @Size(min = 8, max = 8, message = "O cep de origem deve conter 8 caracteres")
    private String cepDestino;

    @NotBlank(message = "O campo 'nomeDestinatario' é obrigatório")
    private String nomeDestinatario;

    private double valorTotalFrete;

    private LocalDate dataPrevistaEntrega;

    private LocalDate dataConsulta;
}
