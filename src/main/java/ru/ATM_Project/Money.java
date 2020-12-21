package ru.ATM_Project;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;


@Getter@Setter
public class Money{
    private CurrencyCode moneyCurrency;
    private BigDecimal amount;

    public void setAmount(double amount) {
        this.amount = new BigDecimal(amount);
    }

    public void setAmount(int amount) {
        this.amount = new BigDecimal(amount);
    }

    public Money(BigDecimal amount, CurrencyCode moneyCurrency) {
        this.amount = amount;
        this.moneyCurrency = moneyCurrency;
    }

}
