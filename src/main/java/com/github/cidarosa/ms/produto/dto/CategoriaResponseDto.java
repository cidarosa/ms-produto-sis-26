package com.github.cidarosa.ms.produto.dto;

import com.github.cidarosa.ms.produto.entities.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CategoriaResponseDto {

    private Long id;
    private String nome;

    public CategoriaResponseDto(Categoria categoria) {
        id = categoria.getId();
        nome = categoria.getNome();
    }
}
