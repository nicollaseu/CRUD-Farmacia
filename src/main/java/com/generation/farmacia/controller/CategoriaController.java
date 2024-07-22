package com.generation.farmacia.controller;

import com.generation.farmacia.model.CategoriaModel;
import com.generation.farmacia.repository.CategoriaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {

    @Autowired
    CategoriaRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<List<CategoriaModel>> getAll() {
        return ResponseEntity.ok(categoriaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaModel> getById(@PathVariable Long id) {
        return categoriaRepository.findById(id)
                .map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<CategoriaModel>> getByCategoria(@PathVariable String nome){
        return ResponseEntity.ok(categoriaRepository.findAllByNomeContainingIgnoreCase(nome));
    }

    @PostMapping
    public ResponseEntity<CategoriaModel> postCategoria(@Valid @RequestBody CategoriaModel categoriaModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaRepository.save(categoriaModel));
    }


    @PutMapping
    public ResponseEntity<CategoriaModel> putCategoria(@Valid @RequestBody CategoriaModel categoriaModel) {
        return categoriaRepository.findById(categoriaModel.getId())
                .map(resp -> ResponseEntity.ok().body(categoriaRepository.save(categoriaModel)))
                .orElse(ResponseEntity.notFound().build());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<CategoriaModel> categoria = categoriaRepository.findById(id);
        if(categoria.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        categoriaRepository.deleteById(id);
    }
}
