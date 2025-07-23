package kh.edu.cstad.springa_4_bankingapi.domain;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "transaction_types")

public class TransactionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String type; //-> payment, transfer
    private Boolean isDeleted;

    @OneToMany(mappedBy = "transactionType")
    private List<Transaction> transactions;

}
