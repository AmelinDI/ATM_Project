package ru.ATM_Project;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ATM {
    private final ArrayList<CardInfo> cardList = new ArrayList<>();

    public ATM() {
        // Заполняем базу из объектов CardInfo(cardNumber, Calendar, cardPIN, Money):
        cardList.add(new CardInfo("1234567812345601",  new GregorianCalendar(2022, Calendar.JANUARY, 1), 1111, new Money(100d, "RUB")));
        cardList.add(new CardInfo("1234567812345602",  new GregorianCalendar(2021, Calendar.JUNE, 1), 2222, new Money(200d, "RUB")));
        cardList.add(new CardInfo("1234567812345603",  new GregorianCalendar(2011, Calendar.OCTOBER, 1), 3333, new Money(300d, "RUB")));
    }

    public Money getCardBalance(String txtCard, String txtExpDate, int cardPIN){
        GregorianCalendar expDate;
        int year = 1;
        int month = Calendar.JANUARY;

        try {
            year = Integer.parseInt(txtExpDate.substring(3,5)) + 2000;
            month = Integer.parseInt(txtExpDate.substring(0,2)) - 1;
        }catch (NumberFormatException nfe) {
            log.debug("Error occurred", nfe);
//todo SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
        }finally {
            expDate = new GregorianCalendar(year, month,1);
        }


        return cardList.stream()
                .filter(c -> (c.getCardNumber().equals(txtCard) && c.getExpDate().equals(expDate) && c.getCardPIN() == cardPIN))
                .findFirst()
                .map(CardInfo::getBalans)
                .orElse(new Money(0d, "___"));
    }

}
