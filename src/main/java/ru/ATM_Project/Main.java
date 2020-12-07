package ru.ATM_Project;


public class Main {
    public static void main(String[] args) {
        ATM sberATM = new ATM();

        Money cardInfo = sberATM.getCardBalance("1234567812345602","06/21", 2222);

        System.out.println(cardInfo + " " + cardInfo.getMoneyCurrency());
    }

}
