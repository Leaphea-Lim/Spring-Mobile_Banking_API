package kh.edu.cstad.springa_4_bankingapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "accounts")

public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 32)
    private String accountNumber;

    @Column(nullable = false, length = 50)
    private String accountName;

//    @Column(nullable = false, length = 50) -> custom column
//    private String accountType ;

    @Column(nullable = false, length = 50)
    private String accountCurrency;

    @Column(nullable = false)
    private BigDecimal balance;


    @Column(nullable = false)
    private BigDecimal overLimit;


    @Column(nullable = false)
    private Boolean isHide;

    @Column(nullable = false)
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(nullable = false, name = "cust_id", referencedColumnName = "id")
    private Customer customer;

    @ManyToOne
    private AccountType accountType;


}
