package ru.ATM_Project;

public class Card {
    private long cardNumber;
    private String expDate;

    public Card(long cardNumber, String expDate) {
        this.cardNumber = cardNumber;
        this.expDate = expDate;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public String getExpDate() {
        return expDate;
    }
}
