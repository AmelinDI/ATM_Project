package ru.ATM_Project.serverDB;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@Slf4j
@SpringBootApplication
public class MainDB {
    public static void main(String[] args) {
        SpringApplication.run(MainDB.class, args);
        log.info("Main_serverDB отработал");
    }
}
