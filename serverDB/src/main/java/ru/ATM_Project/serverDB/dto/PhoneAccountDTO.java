package ru.ATM_Project.serverDB.dto;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public class PhoneAccountDTO {
    private final Long id;
    private final Long phoneNumber;
    private final BigDecimal balance;
}
