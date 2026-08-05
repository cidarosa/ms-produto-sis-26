package com.github.cidarosa.ms.produto.service;

import com.github.cidarosa.ms.produto.dto.CategoriaRequestDto;
import com.github.cidarosa.ms.produto.dto.CategoriaResponseDto;
import com.github.cidarosa.ms.produto.entities.Categoria;
import com.github.cidarosa.ms.produto.exceptions.DatabaseException;
import com.github.cidarosa.ms.produto.exceptions.ResourceNotFoundException;
import com.github.cidarosa.ms.produto.repositories.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<CategoriaResponseDto> findAllCategorias(){

        return  categoriaRepository.findAll()
                .stream().map(CategoriaResponseDto::new).toList();
    }

    @Transactional(readOnly = true)
    public CategoriaResponseDto findCategoriaById(Long id){

        Categoria categoria = categoriaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado. ID: " + id)
        );

        return new CategoriaResponseDto(categoria);
    }

    @Transactional
    public CategoriaResponseDto saveCategoria(CategoriaRequestDto inputDto){

        Categoria categoria = new Categoria();
        copyDtoToCategoria(inputDto, categoria);
        categoria = categoriaRepository.save(categoria);
        return new CategoriaResponseDto(categoria);
    }

    @Transactional
    public CategoriaResponseDto updateCategoria(Long id, CategoriaRequestDto inputDto){

        try {
            Categoria categoria = categoriaRepository.getReferenceById(id);
            copyDtoToCategoria(inputDto, categoria);
            categoria = categoriaRepository.save(categoria);
            return new CategoriaResponseDto(categoria);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteCategoriaById(Long id){

        if(!categoriaRepository.existsById(id)){
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }

        try {
            categoriaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Não foi possível excluir categoria. Existem produtos associados a ela");
        }
    }

    private void copyDtoToCategoria(CategoriaRequestDto inputDto, Categoria categoria) {

        categoria.setNome(inputDto.getNome());
    }
}
