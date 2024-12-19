package com.challenge.rental_cars_spring_api.core.queries.dtos;

import com.challenge.rental_cars_spring_api.core.domain.Aluguel;
import com.challenge.rental_cars_spring_api.core.domain.exceptions.InvalidPhoneNumberException;
import com.challenge.rental_cars_spring_api.core.enums.PagoEnum;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public record ListarAlugueisQueryResultItem(
        List<AluguelDTO> alugueis,               // lista de alugueis
        BigDecimal totalNaoPago
) {
    public static ListarAlugueisQueryResultItem from(List<Aluguel> alugueis) {

        // Mapeia cada aluguel para o DTO
        List<AluguelDTO> alugueisDTO = alugueis.stream()
                .map(aluguel -> new AluguelDTO(
                        aluguel.getDataAluguel(),
                        aluguel.getCarroId().getModelo(),
                        aluguel.getCarroId().getKm(),
                        aluguel.getClienteId().getNome(),
                        formatarTelefone(aluguel.getClienteId().getTelefone()),
                        aluguel.getDataDevolucao(),
                        aluguel.getValor(),
                        aluguel.isPago() ? PagoEnum.SIM : PagoEnum.NAO
                ))
                .collect(Collectors.toList());

        // Cálculo do valor total ainda não pago
        BigDecimal naoPago = alugueis.stream()
                .filter(aluguel -> !aluguel.isPago())
                .map(Aluguel::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new ListarAlugueisQueryResultItem(alugueisDTO, naoPago);
    }

    // Método privado para formatar o telefone no formato desejado
    private static String formatarTelefone(String telefone) {
        if (telefone != null) {
            int length = telefone.length();
            int parte1 = Integer.parseInt(telefone.substring(0, Math.min(2, length)));
            int parte2 = length > 2 ? Integer.parseInt(telefone.substring(2, Math.min(4, length))) : 0;
            int parte3 = length > 4 ? Integer.parseInt(telefone.substring(4, Math.min(9, length))) : 0;
            int parte4 = length > 9 ? Integer.parseInt(telefone.substring(9, Math.min(13, length))) : 0;

            // Formata o telefone conforme o padrão especificado
            return String.format("+%d(%02d)%05d-%04d",
                    parte1,
                    parte2,
                    parte3,
                    parte4);
        }

        throw new InvalidPhoneNumberException("Formato de telefone inválido.");
    }
}
