package ru.ATM_Project;

public interface Device {
    Money getCardBalance(String txtCard, String txtExpDate, int cardPIN);
    Money topUpPhoneBalance(String txtCard, String txtExpDate, int cardPIN, long mobileNumber, Money amount);
}
