package ru.ATM_Project;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Predicate;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ATM {
    private final ArrayList<CardInfo> cardList = new ArrayList<>();
    private final ArrayList<String> cardUsage = new ArrayList<>();

    public ATM() {
        // Заполняем базу из объектов CardInfo(cardNumber, Calendar, cardPIN, Money):
        cardList.add(new CardInfo("1234567812345601",  LocalDate.of(2022, 1, 1), 1111, new Money(new BigDecimal(100), CurrencyCode.RUR)));
        cardList.add(new CardInfo("1234567812345602",  LocalDate.of(2021, 6, 1), 2222, new Money(new BigDecimal(200), CurrencyCode.EUR)));
        cardList.add(new CardInfo("1234567812345603",  LocalDate.of(2021, 10, 1), 3333, new Money(new BigDecimal(300), CurrencyCode.USD)));
    }

    public Money getCardBalance(String txtCard, String txtExpDate, int cardPIN){
        LocalDate expDate;
        int year = 1;
        int month = 1;

        // Проверяем на повтор использования карты и логируем, если повтор

        Predicate<String> isRepeatUsage = cardUsage::contains;
        if (isRepeatUsage.test(txtCard)) {
            log.info(txtCard);
        }
        cardUsage.add(txtCard);

        try {
            year = Integer.parseInt(txtExpDate.substring(3,5)) + 2000;
            month = Integer.parseInt(txtExpDate.substring(0,2));
        }catch (NumberFormatException nfe) {
            log.debug("Ошибка парсинга даты. Используем дату по умолчанию", nfe);
        }finally {
            expDate = LocalDate.of(year, month,1);
        }


        return cardList.stream()
                .filter(c -> (c.getCardNumber().equals(txtCard) && c.getExpDate().equals(expDate) && c.getCardPIN() == cardPIN))
                .findFirst()
                .map(CardInfo::getBalans)
                .orElse(new Money(new BigDecimal(-1), CurrencyCode.NO_VALUE));
    }

}
