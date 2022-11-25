package com.banking.account.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.pl.PESEL;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = "accounts")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 64)
    private String firstName;
    @Column(nullable = false, length = 64)
    private String secondName;
    @PESEL
    @Column(unique = true, length = 11, nullable = false)
    private Long pesel;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnoreProperties("user")
    private List<AccountEntity> accounts;
}
