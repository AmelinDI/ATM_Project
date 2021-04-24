package ru.ATM_Project.serverDB.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "PHONE_ACCOUNTS")
@NoArgsConstructor
@Getter
@Setter
public class PhoneAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long phoneNumber;
    private BigDecimal balance;
}
