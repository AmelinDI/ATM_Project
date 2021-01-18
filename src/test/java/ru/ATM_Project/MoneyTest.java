package ru.ATM_Project;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


import java.math.BigDecimal;

@Slf4j
class MoneyTest {

    Money money = new Money(new BigDecimal(100), CurrencyCode.RUR);

    @BeforeEach
    void setUp() {
        money.setAmount(200);
        log.info("SetUp 200");
    }

    @Test
    void getAmount() {
        assertEquals(200, money.getAmount().doubleValue());
        log.info("assertAmount 100");
    }


    @AfterEach
    void tearDown() {
        log.info("JUnit tests of class Money finished!");
    }
}