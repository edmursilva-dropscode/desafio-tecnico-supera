package com.gerenciamentodetarefas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.gerenciamentodetarefas.model.Item;
import com.gerenciamentodetarefas.model.EstadoItem;
import com.gerenciamentodetarefas.service.ItemService;
import jakarta.validation.Valid;
import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/itens")
@Tag(name = "Itens", description = "API para gerenciamento de itens de tarefas")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Operation(summary = "Obter todos os itens", description = "Retorna uma lista de todos os itens de tarefas")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = Item.class)))
    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @Operation(summary = "Obter um item por ID", description = "Retorna um único item de tarefa pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item encontrado", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = Item.class))),
        @ApiResponse(responseCode = "404", description = "Item não encontrado", 
                     content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        return itemService.getItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Criar um novo item", description = "Cria um novo item de tarefa")
    @ApiResponse(responseCode = "200", description = "Item criado com sucesso", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = Item.class)))
    @PostMapping
    public Item createItem(@Valid @RequestBody Item item) {
        return itemService.saveItem(item);
    }

    @Operation(summary = "Atualizar um item", description = "Atualiza um item de tarefa existente pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = Item.class))),
        @ApiResponse(responseCode = "404", description = "Item não encontrado", 
                     content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @Valid @RequestBody Item itemDetails) {
        return itemService.getItemById(id)
                .map(item -> {
                    item.setTitulo(itemDetails.getTitulo());
                    item.setEstado(itemDetails.getEstado());
                    item.setDestacado(itemDetails.isDestacado());
                    return ResponseEntity.ok(itemService.saveItem(item));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Excluir um item", description = "Exclui um item de tarefa pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Item não encontrado", 
                     content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        return itemService.getItemById(id)
                .map(item -> {
                    itemService.deleteItem(id);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Atualizar estado de um item", description = "Atualiza o estado de um item de tarefa")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estado do item atualizado com sucesso", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = Item.class))),
        @ApiResponse(responseCode = "404", description = "Item não encontrado", 
                     content = @Content)
    })
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Item> updateItemEstado(@PathVariable Long id, @RequestBody EstadoItem novoEstado) {
        Item updatedItem = itemService.updateItemEstado(id, novoEstado);
        return updatedItem != null ? ResponseEntity.ok(updatedItem) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Alternar destaque de um item", description = "Alterna o estado de destaque de um item de tarefa")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estado de destaque atualizado com sucesso", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = Item.class))),
        @ApiResponse(responseCode = "404", description = "Item não encontrado", 
                     content = @Content)
    })
    @PatchMapping("/{id}/destaque")
    public ResponseEntity<Item> toggleItemDestaque(@PathVariable Long id) {
        Item updatedItem = itemService.toggleItemDestaque(id);
        return updatedItem != null ? ResponseEntity.ok(updatedItem) : ResponseEntity.notFound().build();
    }
}
