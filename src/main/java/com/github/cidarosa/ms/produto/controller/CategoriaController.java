package com.github.cidarosa.ms.produto.controller;

import com.github.cidarosa.ms.produto.dto.CategoriaDto;
import com.github.cidarosa.ms.produto.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaDto>> getAllCategorias() {

        List<CategoriaDto> categorias = categoriaService.findAllCategorias();

        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDto> getCategoriaById(@PathVariable Long id) {

        CategoriaDto categoriaDto = categoriaService.findCategoriaById(id);
        return ResponseEntity.ok(categoriaDto);

    }

    @PostMapping
    public ResponseEntity<CategoriaDto> createCategoria(@RequestBody @Valid CategoriaDto categoriaDto) {

        categoriaDto = categoriaService.saveCategoria(categoriaDto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(categoriaDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(categoriaDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDto> updateCategoria(@PathVariable Long id,
                                                        @Valid @RequestBody CategoriaDto categoriaDto) {

        categoriaDto = categoriaService.updateCategoria(id, categoriaDto);
        return ResponseEntity.ok(categoriaDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Valid> deleteCategoria(@PathVariable Long id) {

        categoriaService.deleteCategoriaById(id);

        return ResponseEntity.noContent().build();
    }
}
