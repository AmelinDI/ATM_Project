package ru.ATM_Project.serverDB.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ATM_Project.serverDB.dto.PhoneAccountDTO;

import java.math.BigDecimal;

@Slf4j
@RestController
@AllArgsConstructor
public class DBController {

    @GetMapping("/test_responce")
    public PhoneAccountDTO testATM() {
        return new Response(1L, 9998887766L, new BigDecimal(100));
    }
}
