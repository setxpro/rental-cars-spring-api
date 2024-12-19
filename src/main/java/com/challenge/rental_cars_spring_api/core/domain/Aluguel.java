package com.challenge.rental_cars_spring_api.core.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "aluguel")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Aluguel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "carro_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Carro carroId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Cliente clienteId;

    @Column(name = "data_aluguel")
    private Date dataAluguel;

    @Column(name = "data_devolucao")
    private Date dataDevolucao;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "pago")
    private boolean pago;
}
