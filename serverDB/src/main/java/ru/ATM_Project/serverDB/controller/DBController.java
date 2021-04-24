package ru.ATM_Project.serverDB.controller;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Slf4j
@RestController
//@AllArgsConstructor
public class DBController {
    private final PhoneAccountRepository phoneAccountRepository;
    private final CreditCardRepository creditCardRepository;
    ApplicationContext applicationContext = AppContextProvider.getApplicationContext();


    public DBController(PhoneAccountRepository phoneAccountRepository, CreditCardRepository creditCardRepository) {
        this.phoneAccountRepository = phoneAccountRepository;
        this.creditCardRepository = creditCardRepository;
    }

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


//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();


/*        boolean moneyCharged = creditCardRepository
                .findById(cardID)
                .map(c -> {
                    if (c.getBalance().compareTo(amount) < 0) return false;
                    c.setBalance(c.getBalance().subtract(amount));
                    creditCardRepository.save(c);
                    return true;
                })
                .orElse(false);

        if (moneyCharged) {
            phoneAccountRepository
                    .findById(phoneID)
                    .ifPresent(p -> {
                        p.setBalance(p.getBalance().add(amount));
                        phoneAccountRepository.save(p);
                    });
        }*/

//        transaction.commit();
//        session.close();


    private SessionFactory getCurrentSessionFromJPA() {
        // JPA and Hibernate SessionFactory example
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("jpa-tutorial");
        EntityManager entityManager = emf.createEntityManager();
        // Get the Hibernate Session from the EntityManager in JPA
        Session session = entityManager.unwrap(org.hibernate.Session.class);
        SessionFactory factory = session.getSessionFactory();
        return factory;
    }


    // Тестовый метод.
    @GetMapping("/test_hibernate")
    public void testHibernate() {
//        SessionFactory sessionFactory = (SessionFactory) applicationContext.getBean("sessionFactory");
        SessionFactory sessionFactory = getCurrentSessionFromJPA();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();


        Query query = session.createQuery("FROM Developer");
        List developers = query.list();

        developers.forEach(System.out::println);

        transaction.commit();
        session.close();
    }


    // Тестовый метод.
    @GetMapping("/test_jdbc_api")
    @Transactional
    public void test() {
        Connection conn;
        PreparedStatement preparedSt;

        try {
            conn = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "password");
            preparedSt = conn.prepareStatement("INSERT INTO CREDIT_CARDS (CARD_NUMBER, EXP_DATE, CARDPIN, BALANCE) VALUES (?, ?, ?, ?)");
            preparedSt.setString(1, "1234567812345604");
            preparedSt.setString(2, "2022-01-04");
            preparedSt.setInt(3, 4444);
            preparedSt.setBigDecimal(4, BigDecimal.valueOf(400));
            preparedSt.executeUpdate();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    // Тест всего остального
    @GetMapping("/test_other")
    public void testOther() {
        System.out.println(applicationContext.getApplicationName());
    }


}
