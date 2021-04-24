package ru.ATM_Project.serverDB.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.ATM_Project.serverDB.TopUpResult;
import ru.ATM_Project.serverDB.repository.CreditCardRepository;
import ru.ATM_Project.serverDB.repository.PhoneAccountRepository;

import java.math.BigDecimal;

@SpringBootTest
class DBControllerTest {
    private DBController dbController;

    @MockBean
    private CreditCardRepository creditCardRepository;

    @MockBean
    private PhoneAccountRepository phoneAccountRepository;

    @Test
    @Disabled("not finished yet")
    void phoneTopUp() {
        dbController = new DBController(phoneAccountRepository, creditCardRepository);
        TopUpResult resultTopUp = dbController.phoneTopUp(1L, 1L, new BigDecimal(10));
        Assertions.assertEquals(TopUpResult.OK, resultTopUp);
    }
}