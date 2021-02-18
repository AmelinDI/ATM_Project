package ru.ATM_Project.serverDB.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ATM_Project.common.dto.PhoneAccountDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController
@AllArgsConstructor
public class DBController {

    @PostMapping("/test_responce_list")
    public List<PhoneAccountDTO> testATMList() {
        List<PhoneAccountDTO> phoneAccountDTOList = new ArrayList<>();
        phoneAccountDTOList.add(new PhoneAccountDTO(1L, 9008007060L, new BigDecimal(100)));
        phoneAccountDTOList.add(new PhoneAccountDTO(2L, 9008007070L, new BigDecimal(100)));
        phoneAccountDTOList.add(new PhoneAccountDTO(2L, 9008007060L, new BigDecimal(100)));
        return phoneAccountDTOList;
    }

    @PostMapping("/test_responce")
    public PhoneAccountDTO testATM() {
        return new PhoneAccountDTO(1L, 9008007060L, new BigDecimal(100));
    }
}
