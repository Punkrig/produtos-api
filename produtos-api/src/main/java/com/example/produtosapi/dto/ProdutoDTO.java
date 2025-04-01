package com.example.produtosapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    private String descricao;

    @NotNull(message = "Preço é obrigatório")
    @Min(value = 0, message = "Preço deve ser maior ou igual a zero")
    private BigDecimal preco;

    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 0, message = "Quantidade deve ser maior ou igual a zero")
    private Integer quantidade;

    private String categoria;
}