package ru.ATM_Project;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Predicate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ATM implements Device{
    private final ArrayList<CardInfo> cardList = new ArrayList<>();
    private final ArrayList<MobileInfo> mobileList = new ArrayList<>();
    private final ArrayList<String> cardUsage = new ArrayList<>();

    public ATM() {
        // Заполняем базу из объектов CardInfo(cardNumber, Calendar, cardPIN, Money):

        cardList.add(new CardInfo("1234567812345601",  LocalDate.of(2022, 1, 1), 1111, new Money(new BigDecimal(100), CurrencyCode.RUR)));
        cardList.add(new CardInfo("1234567812345602",  LocalDate.of(2021, 6, 1), 2222, new Money(new BigDecimal(200), CurrencyCode.RUR)));
        cardList.add(new CardInfo("1234567812345603",  LocalDate.of(2021, 10, 1), 3333, new Money(new BigDecimal(300), CurrencyCode.RUR)));

        mobileList.add(new MobileInfo(79009009010L, new Money(new BigDecimal(10), CurrencyCode.RUR)));
        mobileList.add(new MobileInfo(79009009020L, new Money(new BigDecimal(20), CurrencyCode.RUR)));
        mobileList.add(new MobileInfo(79009009030L, new Money(new BigDecimal(30), CurrencyCode.RUR)));
    }

    @Override
    public Money getCardBalance(String txtCard, String txtExpDate, int cardPIN){
        LocalDate expDate = getExpDate(txtExpDate);

        // Проверяем на повтор использования карты и логируем, если повтор
        Predicate<String> isRepeatUsage = cardUsage::contains;
        if (isRepeatUsage.test(txtCard)) {
            log.info("Повторный запрос по карте " + txtCard);
        }
        cardUsage.add(txtCard);


        return cardList.stream()
                .filter(c -> (c.getCardNumber().equals(txtCard) && c.getExpDate().equals(expDate) && c.getCardPIN() == cardPIN))
                .findFirst()
                .map(CardInfo::getBalans)
                .orElse(new Money(new BigDecimal(-1), CurrencyCode.NO_VALUE));
    }


    @Override
    public Money topUpPhoneBalance(String txtCard, String txtExpDate, int cardPIN, long mobileNumber, Money amount) {
        LocalDate expDate = getExpDate(txtExpDate);
        CardInfo cardInfo;
        MobileInfo mobileInfo;


        try {
            cardInfo = getCardInfo(txtCard, expDate, cardPIN);
            mobileInfo = getMobileInfo(mobileNumber);
        } catch (NoCardException|NoMobileException noCardMobileException) {
            log.info(noCardMobileException.getMessage());
            return new Money(new BigDecimal(0), CurrencyCode.RUR);
        }

        cardList.forEach(card -> log.info(card.getCardNumber() + " баланс карты до " + card.getBalans().getAmount()));
        mobileList.forEach(mobile -> log.info(mobile.getMobileNumber() + " баланс мобильного телефона до " + mobile.getBalans().getAmount()));

        cardInfo.setBalans(new Money(cardInfo.getBalans().getAmount().subtract((amount.getAmount())), CurrencyCode.RUR));
        mobileInfo.setBalans(new Money(mobileInfo.getBalans().getAmount().add(amount.getAmount()), CurrencyCode.RUR));

        cardList.forEach(card -> log.info(card.getCardNumber() + " баланс карты после " + card.getBalans().getAmount()));
        mobileList.forEach(mobile -> log.info(mobile.getMobileNumber() + " баланс мобильного телефона после " + mobile.getBalans().getAmount()));
        return  mobileInfo.getBalans();
    }


    private LocalDate getExpDate(String txtExpDate){
        int year;
        int month;

        try {
            year = Integer.parseInt(txtExpDate.substring(3,5)) + 2000;
            month = Integer.parseInt(txtExpDate.substring(0,2));
        }catch (NumberFormatException nfe) {
            year = 1;
            month = 1;
            log.info("Ошибка парсинга даты. Используем параметры даты по умолчанию", nfe);
        }

        return LocalDate.of(year, month,1);
    }


    private CardInfo getCardInfo(String txtCard, LocalDate expDate, int cardPIN) throws NoCardException{

        return cardList.stream()
                .filter(c -> (c.getCardNumber().equals(txtCard) && c.getExpDate().equals(expDate) && c.getCardPIN() == cardPIN))
                .findFirst()
                .orElseThrow(()->new NoCardException("Карта не найдена или неверный ПИН"));
    }

    private MobileInfo getMobileInfo(Long mobileNumber) throws NoMobileException{

        return mobileList.stream()
                .filter(c -> (c.getMobileNumber() == mobileNumber))
                .findFirst()
                .orElseThrow(()->new NoMobileException("Мобильный номер не найден"));
    }


}
