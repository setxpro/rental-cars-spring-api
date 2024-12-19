package com.challenge.rental_cars_spring_api.core.queries.dtos;

import com.challenge.rental_cars_spring_api.core.enums.PagoEnum;

import java.math.BigDecimal;
import java.util.Date;

public record AluguelDTO(
        Date dataAluguel,            // Data do aluguel
        String modelo,                // Modelo do carro
        Integer km,                   // Quilometragem do carro
        String nomeCliente,           // Nome do cliente
        String telefoneCliente,       // Telefone do cliente no formato +XX(XX)XXXXX-XXXX
        Date dataDevolucao,           // Data de devolução
        BigDecimal valor,             // Valor do aluguel
        PagoEnum pago                 // Status de pagamento (SIM/NAO)
) {
}
