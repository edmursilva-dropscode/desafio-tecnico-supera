package com.gerenciamentodetarefas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gerenciamentodetarefas.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}








