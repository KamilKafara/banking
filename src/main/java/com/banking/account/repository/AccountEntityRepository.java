package com.banking.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountEntityRepository extends JpaRepository<AccountEntity, Long> {
    String FULL_JOIN_QUERY = "SELECT a,u " +
                             "FROM UserEntity u FULL JOIN AccountEntity a " +
                             "ON u.id = a.user.id ";


    @Query(FULL_JOIN_QUERY + "WHERE u.pesel = ?1")
    Optional<AccountEntity> findAccountByUserPesel(@NonNull String pesel);

    @Query(FULL_JOIN_QUERY + "WHERE u.id = ?1")
    Optional<AccountEntity> findAccountByUserId(@NonNull Long userId);

}
