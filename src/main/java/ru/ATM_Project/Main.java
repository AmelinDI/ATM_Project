package ru.ATM_Project;

public class Main {
    public static void main(String[] args) {
        Card myCard = new Card(1234_5678_1234_5678L, "12/21");
        ATM sberATM = new ATM();

        System.out.println(sberATM.getCardBalance(myCard,1234));;
    }
}
