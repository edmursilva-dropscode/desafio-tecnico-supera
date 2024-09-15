package com.gerenciamentodetarefas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gerenciamentodetarefas.model.Lista;
import com.gerenciamentodetarefas.repository.ListaRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ListaService {

    @Autowired
    private ListaRepository listaRepository;

    public List<Lista> getAllListas() {
        return listaRepository.findAll();
    }

    public Optional<Lista> getListaById(Long id) {
        return listaRepository.findById(id);
    }

    public Lista saveLista(Lista lista) {
        return listaRepository.save(lista);
    }

    public void deleteLista(Long id) {
        listaRepository.deleteById(id);
    }
}

















































