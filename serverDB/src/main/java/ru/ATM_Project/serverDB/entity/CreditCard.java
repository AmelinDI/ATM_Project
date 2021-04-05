package ru.ATM_Project.serverDB.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "CREDIT_CARDS")
@NoArgsConstructor
@Getter
@Setter
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cardNumber;
    private LocalDate expDate;
    private int cardPIN;
    private BigDecimal balance;
}
