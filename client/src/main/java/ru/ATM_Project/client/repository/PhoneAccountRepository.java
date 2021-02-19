package ru.ATM_Project.client.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ATM_Project.client.entity.PhoneAccount;

@Repository
public interface PhoneAccountRepository extends CrudRepository<PhoneAccount, Long> {
}
