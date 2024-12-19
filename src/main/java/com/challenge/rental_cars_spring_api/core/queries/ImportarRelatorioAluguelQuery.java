package com.challenge.rental_cars_spring_api.core.queries;

import com.challenge.rental_cars_spring_api.core.domain.Aluguel;
import com.challenge.rental_cars_spring_api.core.domain.Carro;
import com.challenge.rental_cars_spring_api.core.domain.Cliente;
import com.challenge.rental_cars_spring_api.core.domain.exceptions.NotFoundException;
import com.challenge.rental_cars_spring_api.core.queries.dtos.CustomMessageDTO;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.AluguelRepository;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.CarroRepository;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImportarRelatorioAluguelQuery {

    // Repositórios injetados via construtor
    private final CarroRepository carroRepository;
    private final ClienteRepository clienteRepository;
    private final AluguelRepository aluguelRepository;

    // Formato de data utilizado para parse do arquivo
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    // Método para processar o arquivo de aluguel
    public void execute(MultipartFile file) throws IOException {
        List<String> erros = new ArrayList<>();
        int linhasProcessadas = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String linha;

            while ((linha = reader.readLine()) != null) {
                if (linha.length() == 20) {
                    try {
                        log.info("Processando linha: {}", linha);

                        // Extraindo informações da linha
                        Long idCarro = Long.parseLong(linha.substring(0, 2).trim());
                        Long idCliente = Long.parseLong(linha.substring(2, 4).trim());
                        String dataAluguelStr = linha.substring(4, 12).trim();
                        String dataDevolucaoStr = linha.substring(12, 20).trim();

                        // Validando e processando o carro e o cliente
                        Optional<Carro> carroOpt = carroRepository.findById(idCarro);
                        Optional<Cliente> clienteOpt = clienteRepository.findById(idCliente);

                        if (carroOpt.isPresent() && clienteOpt.isPresent()) {
                            Carro carro = carroOpt.get();
                            Cliente cliente = clienteOpt.get();

                            Date dataAluguel = new Date(dateFormat.parse(dataAluguelStr).getTime());
                            Date dataDevolucao = new Date(dateFormat.parse(dataDevolucaoStr).getTime());

                            // Calculando valor do aluguel
                            long diasAlugados = calcularDiasAlugados(dataAluguel, dataDevolucao);
                            BigDecimal valorDiaria = carro.getVlrDiaria();
                            BigDecimal valorTotal = valorDiaria.multiply(BigDecimal.valueOf(diasAlugados));

                            // Criando o aluguel
                            Aluguel aluguel = new Aluguel();
                            aluguel.setCarroId(carro);
                            aluguel.setClienteId(cliente);
                            aluguel.setDataAluguel(dataAluguel);
                            aluguel.setDataDevolucao(dataDevolucao);
                            aluguel.setValor(valorTotal);

                            aluguelRepository.save(aluguel);
                            linhasProcessadas++;
                        } else {
                            log.warn("Carro ou Cliente não encontrado. Linha: {}", linha);
                        }
                    } catch (Exception e) {
                        log.error("Erro ao processar linha: {} - {}", linha, e.getMessage());
                    }
                } else {
                    log.warn("Linha malformada: {}", linha);
                }
            }
        }
    }

    // Método para calcular a quantidade de dias entre duas datas
    private long calcularDiasAlugados(Date dataAluguel, Date dataDevolucao) {
        long diferencaEmMillis = dataDevolucao.getTime() - dataAluguel.getTime();
        return diferencaEmMillis / (1000 * 60 * 60 * 24);
    }
}
