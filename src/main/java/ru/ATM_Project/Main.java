package ru.ATM_Project;

import java.math.BigDecimal;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


@Slf4j
public class Main {

    public static void main(String[] args) {
        log.debug("Hello this is a debug message");
        log.info("Hello this is an info message");

        ATM someATM = new ATM();

        Money cardBalance = someATM.getCardBalance("1234567812345602","06/21", 2222);
        System.out.println(cardBalance.getAmount() + " " + cardBalance.getMoneyCurrency());

        cardBalance = someATM.getCardBalance("1234567812345603","10/21", 3333);
        System.out.println(cardBalance.getAmount() + " " + cardBalance.getMoneyCurrency());

        Money mobileBalance = someATM.topUpPhoneBalance("1234567812345602","06/21", 2222,79009009020L, new Money(new BigDecimal(50), CurrencyCode.RUR));
        System.out.println("Баланс телефона стал = " + mobileBalance.getAmount());


        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        cardBalance.setMoneyCurrency(null);
        cardBalance.setAmount(-1);
        Set<ConstraintViolation<Money>> violations = validator.validate(cardBalance);
        violations.forEach(System.out::println);
    }

}
