package com.gerenciamentodetarefas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gerenciamentodetarefas.model.Item;
import com.gerenciamentodetarefas.model.EstadoItem;
import com.gerenciamentodetarefas.repository.ItemRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    public Item updateItemEstado(Long id, EstadoItem novoEstado) {
        Optional<Item> itemOpt = itemRepository.findById(id);
        if (itemOpt.isPresent()) {
            Item item = itemOpt.get();
            item.setEstado(novoEstado);
            return itemRepository.save(item);
        }
        return null;
    }

    public Item toggleItemDestaque(Long id) {
        Optional<Item> itemOpt = itemRepository.findById(id);
        if (itemOpt.isPresent()) {
            Item item = itemOpt.get();
            item.setDestacado(!item.isDestacado());
            return itemRepository.save(item);
        }
        return null;
    }
}






































