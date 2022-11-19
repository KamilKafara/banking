package com.banking.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountEntityRepository extends JpaRepository<AccountEntity, Long> {
    AccountEntity findAccountById(Long id);

//    AccountEntity findAccountByPesel(Long pesel);
}
