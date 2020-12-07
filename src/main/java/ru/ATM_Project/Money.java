package ru.ATM_Project;

import java.math.BigDecimal;

import lombok.Getter;

public class Money extends BigDecimal {
    @Getter
    private final String moneyCurrency;

    public Money(Double val, String moneyCurrency) {
        super(val);
        this.moneyCurrency = moneyCurrency;
    }

}
