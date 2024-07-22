package com.generation.farmacia.controller;

import com.generation.farmacia.model.CategoriaModel;
import com.generation.farmacia.model.ProdutoModel;
import com.generation.farmacia.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    ProdutoRepository produtoRepository;

    @GetMapping
    public ResponseEntity<List<ProdutoModel>> getAll() {
        return ResponseEntity.ok(produtoRepository.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProdutoModel> getById(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<ProdutoModel>> getByCategoria(@PathVariable String nome){
        return ResponseEntity.ok(produtoRepository.findAllByNomeContainingIgnoreCase(nome));
    }

    @PostMapping
    public ResponseEntity<ProdutoModel> postCategoria(@Valid @RequestBody ProdutoModel produtoModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produtoModel));
    }


    @PutMapping
    public ResponseEntity<ProdutoModel> putCategoria(@Valid @RequestBody ProdutoModel produtoModel) {
        return produtoRepository.findById(produtoModel.getId())
                .map(resp -> ResponseEntity.ok().body(produtoRepository.save(produtoModel)))
                .orElse(ResponseEntity.notFound().build());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<ProdutoModel> categoria = produtoRepository.findById(id);
        if(categoria.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        produtoRepository.deleteById(id);
    }
}
