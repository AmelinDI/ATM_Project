package ru.ATM_Project.serverDB.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.ATM_Project.serverDB.entity.CreditCard;
import ru.ATM_Project.serverDB.entity.PhoneAccount;
import ru.ATM_Project.serverDB.repository.CreditCardRepository;
import ru.ATM_Project.serverDB.repository.PhoneAccountRepository;

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

}
