package com.gerenciamentodetarefas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.gerenciamentodetarefas.model.Lista;
import com.gerenciamentodetarefas.service.ListaService;
import jakarta.validation.Valid;
import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/listas")
@Tag(name = "Listas", description = "API para gerenciamento de listas de tarefas")
public class ListaController {

    @Autowired
    private ListaService listaService;

    @Operation(summary = "Obter todas as listas", description = "Retorna uma lista de todas as listas de tarefas")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = Lista.class)))
    @GetMapping
    public List<Lista> getAllListas() {
        return listaService.getAllListas();
    }

    @Operation(summary = "Obter uma lista por ID", description = "Retorna uma única lista de tarefas pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista encontrada", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = Lista.class))),
        @ApiResponse(responseCode = "404", description = "Lista não encontrada", 
                     content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Lista> getListaById(@PathVariable Long id) {
        return listaService.getListaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Criar uma nova lista", description = "Cria uma nova lista de tarefas")
    @ApiResponse(responseCode = "200", description = "Lista criada com sucesso", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = Lista.class)))
    @PostMapping
    public Lista createLista(@Valid @RequestBody Lista lista) {
        return listaService.saveLista(lista);
    }

    @Operation(summary = "Atualizar uma lista", description = "Atualiza uma lista de tarefas existente pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista atualizada com sucesso", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = Lista.class))),
        @ApiResponse(responseCode = "404", description = "Lista não encontrada", 
                     content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Lista> updateLista(@PathVariable Long id, @Valid @RequestBody Lista listaDetails) {
        return listaService.getListaById(id)
                .map(lista -> {
                    lista.setTitulo(listaDetails.getTitulo());
                    return ResponseEntity.ok(listaService.saveLista(lista));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Excluir uma lista", description = "Exclui uma lista de tarefas pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista excluída com sucesso"),
        @ApiResponse(responseCode = "404", description = "Lista não encontrada", 
                     content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLista(@PathVariable Long id) {
        return listaService.getListaById(id)
                .map(lista -> {
                    listaService.deleteLista(id);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}