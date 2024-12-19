package com.challenge.rental_cars_spring_api.core.queries;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.challenge.rental_cars_spring_api.core.domain.Carro;
import com.challenge.rental_cars_spring_api.core.queries.dtos.ListarCarrosQueryResultItem;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.CarroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

class ListarCarrosQueryTest {

    @InjectMocks
    private ListarCarrosQuery listarCarrosQuery;

    @Mock
    private CarroRepository carroRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute() {
        // Given
        Carro carro1 = new Carro(1L, "Modelo1", "2020", 4, 200, "FIAT", new BigDecimal("300"));
        Carro carro2 = new Carro(2L, "Modelo2", "2024", 6, 200, "BMW", new BigDecimal("3.000"));
        List<Carro> carros = List.of(carro1, carro2);

        when(carroRepository.findAll()).thenReturn(carros);

        // When
        List<ListarCarrosQueryResultItem> result = listarCarrosQuery.execute();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Modelo1", result.get(0).modelo());
        assertEquals("Modelo2", result.get(1).modelo());
    }

    @Test
    public void testExecuteWhenNoCarros() {
        // Given
        when(carroRepository.findAll()).thenReturn(List.of());

        // When
        List<ListarCarrosQueryResultItem> result = listarCarrosQuery.execute();

        // Then
        assertNotNull(result);
        assertEquals(0, result.size());
    }
}