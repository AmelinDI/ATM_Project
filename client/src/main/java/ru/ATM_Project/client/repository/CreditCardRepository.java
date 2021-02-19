package ru.ATM_Project.client.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ATM_Project.client.entity.CreditCard;


@Repository
public interface CreditCardRepository extends CrudRepository<CreditCard, Long> {
}
