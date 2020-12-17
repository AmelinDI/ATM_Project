package ru.ATM_Project;

import java.math.BigDecimal;

import lombok.Getter;

public class Money extends BigDecimal {
    @Getter
    CurrencyCode moneyCurrency;

    public Money(Double val, CurrencyCode moneyCurrency) {
        super(val);
        this.moneyCurrency = moneyCurrency;
    }

}
