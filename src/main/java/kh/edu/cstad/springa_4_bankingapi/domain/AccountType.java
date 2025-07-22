package kh.edu.cstad.springa_4_bankingapi.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "account_types")
public class AccountType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto implement on primary key
    private Integer id;

//    @Column(nullable = false, unique = true)
    private String type;

    @OneToMany(mappedBy = "accountType")
    private List<Account> accounts;

}
