package com.supera.gerenciamentodetarefas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
@OpenAPIDefinition
@ComponentScan(basePackages = "com.supera.gerenciamentodetarefas")
//@ComponentScan(basePackages = {"com.supera.gerenciamentodetarefas", "com.gerenciamentodetarefas.controller", "com.gerenciamentodetarefas.service"})
public class GerenciamentoDeTarefasApplication {

    public static void main(String[] args) {
        SpringApplication.run(GerenciamentoDeTarefasApplication.class, args);
    }
}
