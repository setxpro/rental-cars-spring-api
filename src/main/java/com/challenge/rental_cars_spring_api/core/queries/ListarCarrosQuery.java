package com.challenge.rental_cars_spring_api.core.queries;

import com.challenge.rental_cars_spring_api.core.queries.dtos.ListarCarrosQueryResultItem;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.CarroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ListarCarrosQuery {

    private final CarroRepository carroRepository;

    public List<ListarCarrosQueryResultItem> execute() {
        log.info("Iniciando busca por todos os carros...");

        // Recupera todos os carros do repositório e os mapeia para o DTO de resultado
        List<ListarCarrosQueryResultItem> carros = carroRepository.findAll().stream().map(ListarCarrosQueryResultItem::from).toList();

        log.info("Busca por carros concluída. Registros encontrados: {}", carros.size());
        return carros;
    }
}
