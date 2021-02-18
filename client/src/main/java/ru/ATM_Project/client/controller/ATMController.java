package ru.ATM_Project.client.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import ru.ATM_Project.client.entity.CreditCard;
import ru.ATM_Project.client.entity.PhoneAccount;
import ru.ATM_Project.client.repository.CreditCardRepository;
import ru.ATM_Project.client.repository.PhoneAccountRepository;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Controller
//@RestController
@AllArgsConstructor
public class ATMController {

    private final PhoneAccountRepository phoneAccountRepository;
    private final CreditCardRepository creditCardRepository;

    @GetMapping("/test_rest")
    public String testREST() throws URISyntaxException, JSONException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 1);
        jsonObject.put("name", "John");
        HttpEntity<String> request = new HttpEntity<String>(jsonObject.toString(), headers);

        ResponseEntity<String> response = restTemplate.postForEntity(new URI("http://127.0.0.1:8081/test_responce"), request, String.class);

        System.out.println(response);
        return "Ответ от сервера: " + response.toString();
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
