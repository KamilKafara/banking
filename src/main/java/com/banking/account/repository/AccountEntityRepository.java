package com.banking.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountEntityRepository extends JpaRepository<AccountEntity, Long> {
    @Query("select a from AccountEntity a where a.user.pesel = ?1")
    Optional<AccountEntity> findAccountByUserPesel(@NonNull Long pesel);

    @Query("select a from AccountEntity a where a.user.id = ?1")
    Optional<AccountEntity> findAccountByUserId(@NonNull Long userId);

}
