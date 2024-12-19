package com.challenge.rental_cars_spring_api.core.queries;

import com.challenge.rental_cars_spring_api.core.enums.PagoEnum;
import com.challenge.rental_cars_spring_api.core.queries.dtos.ListarAlugueisQueryResultItem;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.AluguelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ListarAlugueisQuery {
    private final AluguelRepository aluguelRepository;

    public List<ListarAlugueisQueryResultItem> execute() {
        log.info("Iniciando busca por todos os aluguéis...");

        // Recupera todos os aluguéis do repositório e os mapeia para o DTO de resultado
        List<ListarAlugueisQueryResultItem> alugueis =  aluguelRepository.findAll().stream().map(ListarAlugueisQueryResultItem::from).toList();

        // Cálculo do valor total ainda não pago
        BigDecimal valorTotalNaoPago = alugueis.stream()
                .filter(e -> e.pago() == PagoEnum.NAO)
                .map(ListarAlugueisQueryResultItem::valor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Registra a conclusão do processo de busca com o número de aluguéis encontrados
        log.info("Busca por aluguéis concluída. Registros encontrados: {}", alugueis.size());

        // Registra o valor total ainda não pago
        log.info("Valor total ainda não pago: {}", valorTotalNaoPago);

        return alugueis;
    }
}