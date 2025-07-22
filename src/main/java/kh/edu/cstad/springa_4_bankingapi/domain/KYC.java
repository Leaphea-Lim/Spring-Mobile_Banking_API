package kh.edu.cstad.springa_4_bankingapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity

//kyc = know ur customer
public class KYC {
    @Id
//    @GeneratedValue(strategy = GenerationType.UUID) //id-> uuid
    private Integer id;

    @Column(unique = true, nullable = false, length = 9)
    private String nationalCardId;

    @Column(nullable = false)
    private Boolean isVerified;

    @Column(nullable = false)
    private Boolean isDeleted;

    @OneToOne(optional = false)
    @MapsId
    @JoinColumn(name = "cust_id")
    private Customer customer;

}
