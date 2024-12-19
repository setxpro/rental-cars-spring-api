package com.challenge.rental_cars_spring_api.core.queries;

import com.challenge.rental_cars_spring_api.core.domain.Aluguel;
import com.challenge.rental_cars_spring_api.core.domain.Carro;
import com.challenge.rental_cars_spring_api.core.domain.Cliente;
import com.challenge.rental_cars_spring_api.core.enums.PagoEnum;
import com.challenge.rental_cars_spring_api.core.queries.dtos.ListarAlugueisQueryResultItem;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.AluguelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ListarAlugueisQueryTest {

    @InjectMocks
    private ListarAlugueisQuery listarAlugueisQuery;

    @Mock
    private AluguelRepository aluguelRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute() {
        // Given
        Carro carro1 = new Carro();
        Cliente cliente1 = new Cliente();

        Aluguel aluguel1 = new Aluguel(1L, carro1, cliente1, Date.valueOf("2024-12-15"), Date.valueOf("2024-12-17"), new BigDecimal("300.00"), true);
        Carro carro2 = new Carro();
        Cliente cliente2 = new Cliente();

        Aluguel aluguel2 = new Aluguel(2L, carro2, cliente2, Date.valueOf("2024-12-15"), Date.valueOf("2024-12-17"), new BigDecimal("700.00"), false);

        List<Aluguel> alugueis = List.of(aluguel1, aluguel2);

        when(aluguelRepository.findAll()).thenReturn(alugueis);

        // When
        List<ListarAlugueisQueryResultItem> result = listarAlugueisQuery.execute();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(PagoEnum.SIM, result.get(0).pago());
        assertEquals(PagoEnum.NAO, result.get(1).pago());
    }

    @Test
    void testExecuteCalculaValorTotalNaoPago() {
        // Given
        Carro carro1 = new Carro(1L, "UNO", "2015", 6, 200, "Fiat", new BigDecimal("500.00"));
        Cliente cliente1 = new Cliente(1L, "Mévio", "12345678910", "14275386986", "5521900000000");

        Aluguel aluguel1 = new Aluguel(1L, carro1, cliente1, Date.valueOf("2024-12-15"), Date.valueOf("2024-12-17"), new BigDecimal("500.00"), true);

        Carro carro2 = new Carro(2L, "HB20s", "2017", 6, 200, "Hyundai", new BigDecimal("700.00"));
        Cliente cliente2 = new Cliente(2L, "Fulano", "14275386986", "12345678910", "5521900000000");

        Aluguel aluguel2 = new Aluguel(2L, carro2, cliente2, Date.valueOf("2024-12-15"), Date.valueOf("2024-12-17"), new BigDecimal("700.00"), false);

        List<Aluguel> alugueis = List.of(aluguel1, aluguel2);

        when(aluguelRepository.findAll()).thenReturn(alugueis);

        // When
        List<ListarAlugueisQueryResultItem> result = listarAlugueisQuery.execute();

        // Then
        BigDecimal valorTotalNaoPago = result.stream()
                .filter(e -> e.pago() == PagoEnum.NAO)
                .map(ListarAlugueisQueryResultItem::valor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        assertEquals(new BigDecimal("700.00"), valorTotalNaoPago);
    }

    @Test
    public void testExecuteWhenNoAlugueis() {
        // Given
        when(aluguelRepository.findAll()).thenReturn(List.of());

        // When
        List<ListarAlugueisQueryResultItem> result = listarAlugueisQuery.execute();

        // Then
        assertNotNull(result);
        assertEquals(0, result.size());
    }
}