package ru.ATM_Project.serverDB.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.ATM_Project.serverDB.entity.CreditCard;
import ru.ATM_Project.serverDB.entity.PhoneAccount;
import ru.ATM_Project.serverDB.repository.CreditCardRepository;
import ru.ATM_Project.serverDB.repository.PhoneAccountRepository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;


@Slf4j
@RestController
@AllArgsConstructor
public class DBController {
    private final PhoneAccountRepository phoneAccountRepository;
    private final CreditCardRepository creditCardRepository;

    @GetMapping("/phones_db")
    public Iterable<PhoneAccount> phonesDB() {
        return phoneAccountRepository.findAll();
    }


    @PostMapping("/phone/add_db")
    public void phoneAddDB(@RequestBody PhoneAccount phoneAccount) {
        phoneAccountRepository.save(phoneAccount);
    }


    @GetMapping("/phone/{id}")
    public Optional<PhoneAccount> phoneDetails(@PathVariable("id") Long id) {
        return phoneAccountRepository.findById(id);
    }

    @GetMapping("/phone/{id}/delete")
    public void phoneDelete(@PathVariable("id") Long id) {
        phoneAccountRepository.findById(id).ifPresent(phoneAccountRepository::delete);
    }

    @GetMapping("/cards_db")
    public Iterable<CreditCard> cardsDB() {
        return creditCardRepository.findAll();
    }


    @PostMapping("/phone/topup")
    public boolean phoneTopUp(@RequestParam("phoneID") Long phoneID, @RequestParam("cardID") Long cardID, @RequestParam("amount") BigDecimal amount) {
        boolean moneyCharged = creditCardRepository
                .findById(cardID)
                .map(c -> {
                    if (c.getBalance().compareTo(amount) < 0) return false;
                    c.setBalance(c.getBalance().subtract(amount));
                    creditCardRepository.save(c);
                    return true;
                })
                .orElse(false);

        if (moneyCharged) {
            phoneAccountRepository
                    .findById(phoneID)
                    .ifPresent(p -> {
                        p.setBalance(p.getBalance().add(amount));
                        phoneAccountRepository.save(p);
                    });
        }
        return moneyCharged;
    }


    // Тестовый метод. Не используется во взаимодействии с модулем "client"
    @GetMapping("/test_jdbc_api")
    @Transactional
    public void test() {
        Connection conn;
        PreparedStatement preparedSt;

        try {
            conn = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "password");
            preparedSt = conn.prepareStatement("INSERT INTO CREDIT_CARDS (CARD_NUMBER, EXP_DATE, CARDPIN, BALANCE) VALUES (?, ?, ?, ?)");
            preparedSt.setString(1, "1234567812345604");
            preparedSt.setString(2, "2022-01-04");
            preparedSt.setInt(3, 4444);
            preparedSt.setBigDecimal(4, BigDecimal.valueOf(400));
            preparedSt.executeUpdate();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
