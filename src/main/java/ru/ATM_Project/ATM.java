package ru.ATM_Project;

import java.math.BigDecimal;

public class ATM {

    public BigDecimal getCardBalance(Card card, int pin){
    if ((card.getCardNumber() == 1234_5678_1234_5678L) && (card.getExpDate() == "12/21") && (pin == 1234)) {
            return BigDecimal.valueOf(10.99);
        }
    else
        {
            return BigDecimal.valueOf(0);
        }

    }
}
