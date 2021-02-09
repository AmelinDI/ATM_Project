package ru.ATM_Project.client.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.ATM_Project.client.entity.CreditCard;
import ru.ATM_Project.client.entity.PhoneAccount;
import ru.ATM_Project.client.messages.Request;
import ru.ATM_Project.client.messages.Response;
import ru.ATM_Project.client.repository.CreditCardRepository;
import ru.ATM_Project.client.repository.PhoneAccountRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Controller
@RestController
@AllArgsConstructor
public class ATMController {

    private final PhoneAccountRepository phoneAccountRepository;
    private final CreditCardRepository creditCardRepository;

    @GetMapping("/test_rest")
    public String testREST() {

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Request> request = new HttpEntity<>(new Request(1, "{\"clientId\":1,\"accountId\":0,\"pin\":123}"));

        ResponseEntity<Response> response = restTemplate.postForEntity("http://127.0.0.1:9090/test_responce", request, Response.class);

        return response.toString();
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Главная страница");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "страница About");
        return "about";
    }

    @GetMapping("/phones")
    public String phoneMain(Model model) {

        Iterable<PhoneAccount> phoneAccounts = phoneAccountRepository.findAll();
        model.addAttribute("phoneAccounts", phoneAccounts);

        return "phones";
    }

    @GetMapping("/phone/add")
    public String phoneAdd(Model model) {
        return "phone-add";
    }


    @PostMapping("/phone/add")
    public String phonePostAdd(@RequestParam Long phoneNumber, @RequestParam BigDecimal balance) {
        PhoneAccount phoneAccount = new PhoneAccount(phoneNumber, balance);
        phoneAccountRepository.save(phoneAccount);
        return "redirect:/phones";
    }

    @GetMapping("/phone/{id}")
    public String phoneDetails(@PathVariable("id") Long id, Model model) {
        if (!phoneAccountRepository.existsById(id)) {
            return "redirect:/phones";
        }
        Optional<PhoneAccount> phoneAccountOptional = phoneAccountRepository.findById(id);
        ArrayList<PhoneAccount> phoneAccounts = new ArrayList<>();
        phoneAccountOptional.ifPresent(phoneAccounts::add);
        model.addAttribute("phoneAccounts", phoneAccounts);
        return "phone-details";
    }

    @GetMapping("/phone/{id}/edit")
    public String phoneEdit(@PathVariable("id") Long id, Model model) {
        if (!phoneAccountRepository.existsById(id)) {
            return "redirect:/phones";
        }
        Optional<PhoneAccount> phoneAccountOptional = phoneAccountRepository.findById(id);
        ArrayList<PhoneAccount> phoneAccounts = new ArrayList<>();
        phoneAccountOptional.ifPresent(phoneAccounts::add);
        model.addAttribute("phoneAccounts", phoneAccounts);
        return "phone-edit";
    }

    @PostMapping("/phone/{id}/edit")
    public String phonePostUpdate(@PathVariable("id") Long id, @RequestParam Long phoneNumber, @RequestParam BigDecimal balance) {
        PhoneAccount phoneAccount = phoneAccountRepository.findById(id).orElse(new PhoneAccount(phoneNumber, balance));
        phoneAccount.setPhoneNumber(phoneNumber);
        phoneAccount.setBalance(balance);
        phoneAccountRepository.save(phoneAccount);
        return "redirect:/phones";
    }

    @PostMapping("/phone/{id}/delete")
    public String phonePostDelete(@PathVariable("id") Long id) {
        PhoneAccount phoneAccount = phoneAccountRepository.findById(id).orElse(null);
        if (phoneAccount != null) {
            phoneAccountRepository.delete(phoneAccount);
        }
        return "redirect:/phones";
    }


    @GetMapping("/cards")
    public String cardMain(Model model) {

        Iterable<CreditCard> creditCards = creditCardRepository.findAll();
        model.addAttribute("creditCards", creditCards);

        return "cards";
    }
}
