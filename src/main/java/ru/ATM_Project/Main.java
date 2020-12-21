package ru.ATM_Project;


import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        ATM sberATM = new ATM();
        TestClass myTestClass = new TestClass();

        myTestClass.printString("Test String");

        Money cardBalance = sberATM.getCardBalance("1234567812345602","06/21", 2222);
        System.out.println(cardBalance.getAmount() + " " + cardBalance.getMoneyCurrency());

        cardBalance = sberATM.getCardBalance("1234567812345603","10/21", 3333);
        System.out.println(cardBalance.getAmount() + " " + cardBalance.getMoneyCurrency());
    }

}
