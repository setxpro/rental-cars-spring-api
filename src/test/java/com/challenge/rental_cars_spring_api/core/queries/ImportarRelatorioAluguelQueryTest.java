package com.challenge.rental_cars_spring_api.core.queries;

import com.challenge.rental_cars_spring_api.infrastructure.repositories.AluguelRepository;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.CarroRepository;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;


class ImportarRelatorioAluguelQueryTest {
    @InjectMocks
    private ImportarRelatorioAluguelQuery importarRelatorioAluguelQuery;

    @Mock
    private CarroRepository carroRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private AluguelRepository aluguelRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void executeSuccess() throws IOException {

    }

    @Test
    void executeCarroOrClienteNotFound() throws IOException {

    }

    @Test
    void executeMalformedLine() throws IOException {

    }

    @Test
    void executePartialSuccess() throws IOException {

    }

    @Test
    void executeFileWithNoLines() throws IOException {

    }

}