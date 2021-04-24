package ru.ATM_Project.serverDB.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.ATM_Project.serverDB.AppContextProvider;
import ru.ATM_Project.serverDB.TopUpResult;
import ru.ATM_Project.serverDB.entity.CreditCard;
import ru.ATM_Project.serverDB.entity.PhoneAccount;
import ru.ATM_Project.serverDB.exception.NotEnoughMoneyException;
import ru.ATM_Project.serverDB.exception.PhoneNotDepositedException;
import ru.ATM_Project.serverDB.repository.CreditCardRepository;
import ru.ATM_Project.serverDB.repository.PhoneAccountRepository;

import java.math.BigDecimal;
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
    public TopUpResult phoneTopUp(@RequestParam("phoneID") Long phoneID, @RequestParam("cardID") Long cardID, @RequestParam("amount") BigDecimal amount) {

        try {
            phoneTopUpOperation(phoneID, cardID, amount);
        } catch (NotEnoughMoneyException e) {
            return TopUpResult.NOT_ENOUGH_MONEY;
        } catch (PhoneNotDepositedException e) {
            return TopUpResult.PHONE_NOT_DEPOSITED;
        } catch (Exception e) {
            return TopUpResult.OTHER_ERROR;
        }

        return TopUpResult.OK;
    }


    @Transactional
    private boolean phoneTopUpOperation(Long phoneID, Long cardID, BigDecimal amount) throws Exception {
        boolean moneyCharged;
        boolean phoneDeposited;

        moneyCharged = creditCardRepository
                .findById(cardID)
                .map(c -> {
                    if (c.getBalance().compareTo(amount) < 0) return false;
                    c.setBalance(c.getBalance().subtract(amount));
                    creditCardRepository.save(c);
                    return true;
                })
                .orElse(false);

        if (!moneyCharged) throw new NotEnoughMoneyException();

        phoneDeposited = phoneAccountRepository
                .findById(phoneID) //
                .map(p -> {
                    p.setBalance(p.getBalance().add(amount));
                    phoneAccountRepository.save(p);
                    return true;
                })
                .orElse(false);

        if (!phoneDeposited) throw new PhoneNotDepositedException();

        return true;
    }


    @GetMapping("/test_other")
    public void testOther() {
        ApplicationContext applicationContext = AppContextProvider.getApplicationContext();
        System.out.println(applicationContext.getApplicationName());
    }

}
