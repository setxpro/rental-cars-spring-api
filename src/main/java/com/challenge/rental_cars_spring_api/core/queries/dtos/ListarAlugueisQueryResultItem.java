package com.challenge.rental_cars_spring_api.core.queries.dtos;

import com.challenge.rental_cars_spring_api.core.domain.Aluguel;
import com.challenge.rental_cars_spring_api.core.domain.exceptions.InvalidPhoneNumberException;
import com.challenge.rental_cars_spring_api.core.enums.PagoEnum;

import java.math.BigDecimal;
import java.util.Date;

public record ListarAlugueisQueryResultItem(
        Date dataAluguel,            // Data do aluguel
        String modelo,                // Modelo do carro
        Integer km,                   // Quilometragem do carro
        String nomeCliente,           // Nome do cliente
        String telefoneCliente,       // Telefone do cliente no formato +XX(XX)XXXXX-XXXX
        Date dataDevolucao,           // Data de devolução
        BigDecimal valor,             // Valor do aluguel
        PagoEnum pago                 // Status de pagamento (SIM/NAO)
) {
    public static ListarAlugueisQueryResultItem from(Aluguel aluguel) {
        return new ListarAlugueisQueryResultItem(
                aluguel.getDataAluguel(),                            // Data do aluguel
                aluguel.getCarroId().getModelo(),                   // Modelo do carro
                aluguel.getCarroId().getKm(),                       // Quilometragem do carro
                aluguel.getClienteId().getNome(),                   // Nome do cliente
                formatarTelefone(aluguel.getClienteId().getTelefone()), // Formato do telefone
                aluguel.getDataDevolucao(),                        // Data de devolução
                aluguel.getValor(),                                // Valor do aluguel
                aluguel.isPago() ? PagoEnum.SIM : PagoEnum.NAO      // Status de pagamento
        );
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
