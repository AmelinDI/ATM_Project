package ru.ATM_Project;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
public class CardInfo {
    private final String cardNumber;
    private final LocalDate expDate;
    private final int cardPIN;
    @Setter
    private Money balans;

    public CardInfo(String cardNumber, LocalDate expDate, int cardPIN, Money balans) {
        this.cardNumber = cardNumber;
        this.expDate = expDate;
        this.cardPIN = cardPIN;
        this.balans = balans;
    }


}
