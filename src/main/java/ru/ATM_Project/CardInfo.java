package ru.ATM_Project;

import lombok.Getter;
import lombok.Setter;

import java.util.GregorianCalendar;

public class CardInfo {
    @Getter
    private final String cardNumber;
    @Getter
    private final GregorianCalendar expDate;
    @Getter
    private final int cardPIN;
    @Getter@Setter
    private Money balans;

    public CardInfo(String cardNumber, GregorianCalendar expDate, int cardPIN, Money balans) {
        this.cardNumber = cardNumber;
        this.expDate = expDate;
        this.cardPIN = cardPIN;
        this.balans = balans;
    }


}
