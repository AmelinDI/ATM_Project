package ru.ATM_Project.serverDB.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ATM_Project.serverDB.entity.PhoneAccount;


@Repository
public interface PhoneAccountRepository extends CrudRepository<PhoneAccount, Long> {
}
