package com.banking.account.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.validator.constraints.pl.PESEL;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@EqualsAndHashCode(exclude = "accounts")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 64)
    private String firstName;
    @Column(nullable = false, length = 64)
    private String secondName;
    @PESEL
    @Column(unique = true, length = 11)
    private Long pesel;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JsonIgnoreProperties("user")
    private List<AccountEntity> accounts;
}
