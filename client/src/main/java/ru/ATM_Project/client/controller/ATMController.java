package ru.ATM_Project.client.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import ru.ATM_Project.serverDB.TopUpResult;
import ru.ATM_Project.serverDB.entity.CreditCard;
import ru.ATM_Project.serverDB.entity.PhoneAccount;

import java.math.BigDecimal;

@Slf4j
@Controller
public class ATMController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Главная страница");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "О проекте ATM_project");
        return "about";
    }

    @GetMapping("/phones")
    public String phones(Model model) {
        RestTemplate restTemplate = new RestTemplate();

        PhoneAccount[] phoneAccounts = restTemplate.getForObject("http://127.0.0.1:8081/phones_db", PhoneAccount[].class);
        model.addAttribute("phoneAccounts", phoneAccounts);
        return "phones";
    }

    @GetMapping("/phone/add")
    public String phoneAdd(Model model) {
        return "phone-add";
    }

    @PostMapping("/phone/add")
    public String phonePostAdd(@RequestParam Long phoneNumber, @RequestParam BigDecimal balance) {
        PhoneAccount phoneAccount = new PhoneAccount();
        phoneAccount.setPhoneNumber(phoneNumber);
        phoneAccount.setBalance(balance);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject("http://127.0.0.1:8081/phone/add_db", phoneAccount, void.class);

        return "redirect:/phones";
    }

    @GetMapping("/phone/{id}")
    public String phoneDetails(@PathVariable("id") Long id, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        PhoneAccount phoneAccount = restTemplate.getForObject("http://127.0.0.1:8081/phone/" + id, PhoneAccount.class);

        if (phoneAccount == null) {
            return "redirect:/phones";
        }

        model.addAttribute("phoneAccount", phoneAccount);
        return "phone-details";
    }

    @PostMapping("/phone/{id}/delete")
    public String phonePostDelete(@PathVariable("id") Long id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject("http://127.0.0.1:8081/phone/" + id + "/delete", void.class);

        return "redirect:/phones";
    }

    @GetMapping("/phone/{id}/edit")
    public String phoneEdit(@PathVariable("id") Long id, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        PhoneAccount phoneAccount = restTemplate.getForObject("http://127.0.0.1:8081/phone/" + id, PhoneAccount.class);

        if (phoneAccount == null) {
            return "redirect:/phones";
        }

        model.addAttribute("phoneAccount", phoneAccount);
        return "phone-edit";
    }

    @PostMapping("/phone/{id}/edit")
    public String phonePostUpdate(@PathVariable("id") Long id, @RequestParam Long phoneNumber, @RequestParam BigDecimal balance) {
        PhoneAccount phoneAccount = new PhoneAccount();
        phoneAccount.setPhoneNumber(phoneNumber);
        phoneAccount.setBalance(balance);
        phoneAccount.setId(id);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject("http://127.0.0.1:8081/phone/add_db", phoneAccount, void.class);

        return "redirect:/phones";
    }

    @GetMapping("/phone/{id}/topup")
    public String topUpPhone(@PathVariable("id") Long id, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        PhoneAccount phoneAccount = restTemplate.getForObject("http://127.0.0.1:8081/phone/" + id, PhoneAccount.class);
        CreditCard[] creditCards = restTemplate.getForObject("http://127.0.0.1:8081/cards_db", CreditCard[].class);

        if (phoneAccount == null) {
            return "redirect:/phones";
        }
        model.addAttribute("phoneAccount", phoneAccount);
        model.addAttribute("creditCards", creditCards);
        return "phone-topup";
    }

    @PostMapping("/phone/{id}/topup")
    public String topUpPhone(@PathVariable("id") Long phoneId, @RequestParam("cardID") Long cardID, @RequestParam("amount") BigDecimal amount) {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> parametersMap = new LinkedMultiValueMap<>();
        parametersMap.add("phoneID", phoneId.toString());
        parametersMap.add("cardID", cardID.toString());
        parametersMap.add("amount", amount.toString());
        TopUpResult result = restTemplate.postForObject("http://127.0.0.1:8081/phone/topup", parametersMap, TopUpResult.class);
        log.info("Phone top up result: " + result);

        return "redirect:/phones";
    }

    @GetMapping("/cards")
    public String cards(Model model) {
        RestTemplate restTemplate = new RestTemplate();

        CreditCard[] creditCards = restTemplate.getForObject("http://127.0.0.1:8081/cards_db", CreditCard[].class);

        model.addAttribute("creditCards", creditCards);
        return "cards";
    }

}
