package com.github.cidarosa.ms.produto.dto;

import com.github.cidarosa.ms.produto.entities.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CategoriaRequestDto {

    @NotBlank(message = "Campo nome não pode ser vazio, nulo ou em branco")
    @Size(min = 3, max = 100, message = "O campo nome deve ter entre 3 e 100 caracteres")
    @Schema(example = "Ferramentas")
    private String nome;

    public CategoriaRequestDto(Categoria categoria) {
        nome = categoria.getNome();
    }
}
