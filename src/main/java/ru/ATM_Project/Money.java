package ru.ATM_Project;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Getter@Setter
public class Money{
    @NotNull
    private CurrencyCode moneyCurrency;
    @Min(0)
    private BigDecimal amount;

    public void setAmount(double amount) {
        this.amount = new BigDecimal(amount);
    }

    public Money(BigDecimal amount, CurrencyCode moneyCurrency) {
        this.amount = amount;
        this.moneyCurrency = moneyCurrency;
    }

}
