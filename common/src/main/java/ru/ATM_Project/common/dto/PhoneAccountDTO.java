package ru.ATM_Project.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class PhoneAccountDTO {
    private final Long id;
    private final Long phoneNumber;
    private final BigDecimal balance;
}
