package ru.ATM_Project.serverDB.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ATM_Project.serverDB.entity.CreditCard;


@Repository
public interface CreditCardRepository extends CrudRepository<CreditCard, Long> {
}
