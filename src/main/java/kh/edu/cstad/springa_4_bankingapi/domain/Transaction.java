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
@Table(name = "transactions")
public class Transaction   {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private BigDecimal amount;
    private String remark;
    private Boolean isDeleted;

    @ManyToOne(optional = false)
//    @JoinColumn(name = "transaction_type_id")
    private TransactionType transactionType;

    @ManyToOne(optional = false)
//    @JoinColumn(name = "sender_id")
    private Account sender;

    @ManyToOne(optional = false)
//    @JoinColumn(name = "receiver_id")
    private Account receiver;


}
