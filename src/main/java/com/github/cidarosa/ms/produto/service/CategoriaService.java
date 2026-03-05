package com.github.cidarosa.ms.produto.service;

import com.github.cidarosa.ms.produto.dto.CategoriaDto;
import com.github.cidarosa.ms.produto.entities.Categoria;
import com.github.cidarosa.ms.produto.exceptions.ResourceNotFoundException;
import com.github.cidarosa.ms.produto.repositories.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<CategoriaDto> findAllCategorias(){

        return  categoriaRepository.findAll()
                .stream().map(CategoriaDto::new).toList();
    }

    @Transactional(readOnly = true)
    public CategoriaDto findCategoriaById(Long id){

        Categoria categoria = categoriaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado. ID: " + id)
        );

        return new CategoriaDto(categoria);
    }

    @Transactional
    public CategoriaDto saveCategoria(CategoriaDto inputDto){

        Categoria categoria = new Categoria();
        copyDtoToCategoria(inputDto, categoria);
        categoria = categoriaRepository.save(categoria);
        return new CategoriaDto(categoria);
    }

    @Transactional
    public CategoriaDto updateCategoria(Long id, CategoriaDto inputDto){

        try {
            Categoria categoria = categoriaRepository.getReferenceById(id);
            copyDtoToCategoria(inputDto, categoria);
            categoria = categoriaRepository.save(categoria);
            return new CategoriaDto(categoria);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }
    }

    @Transactional
    public void deleteCategoriaById(Long id){
        if(!categoriaRepository.existsById(id)){
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }

        categoriaRepository.deleteById(id);
    }

    private void copyDtoToCategoria(CategoriaDto inputDto, Categoria categoria) {

        categoria.setNome(inputDto.getNome());
    }
}
